## 序

本文主要研究一下flink的InputFormatSourceFunction

## 实例



```dart
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        IteratorInputFormat iteratorInputFormat = new IteratorInputFormat<String>(new WordIterator());
        env
                //TypeInformation.of(new TypeHint<String>() {}
                .createInput(iteratorInputFormat,TypeExtractor.createTypeInfo(String.class))
                .setParallelism(1)
                .print();
```

- 这里使用IteratorInputFormat调用env的createInput方法来创建SourceFunction

## StreamExecutionEnvironment.createInput

flink-streaming-java_2.11-1.6.2-sources.jar!/org/apache/flink/streaming/api/environment/StreamExecutionEnvironment.java



```php
    @PublicEvolving
    public <OUT> DataStreamSource<OUT> createInput(InputFormat<OUT, ?> inputFormat, TypeInformation<OUT> typeInfo) {
        DataStreamSource<OUT> source;

        if (inputFormat instanceof FileInputFormat) {
            @SuppressWarnings("unchecked")
            FileInputFormat<OUT> format = (FileInputFormat<OUT>) inputFormat;

            source = createFileInput(format, typeInfo, "Custom File source",
                    FileProcessingMode.PROCESS_ONCE, -1);
        } else {
            source = createInput(inputFormat, typeInfo, "Custom Source");
        }
        return source;
    }

    private <OUT> DataStreamSource<OUT> createInput(InputFormat<OUT, ?> inputFormat,
                                                    TypeInformation<OUT> typeInfo,
                                                    String sourceName) {

        InputFormatSourceFunction<OUT> function = new InputFormatSourceFunction<>(inputFormat, typeInfo);
        return addSource(function, sourceName, typeInfo);
    }
```

- StreamExecutionEnvironment.createInput在inputFormat不是FileInputFormat类型的时候创建的是InputFormatSourceFunction

## InputFormatSourceFunction



```java
/**
 * A {@link SourceFunction} that reads data using an {@link InputFormat}.
 */
@Internal
public class InputFormatSourceFunction<OUT> extends RichParallelSourceFunction<OUT> {
    private static final long serialVersionUID = 1L;

    private TypeInformation<OUT> typeInfo;
    private transient TypeSerializer<OUT> serializer;

    private InputFormat<OUT, InputSplit> format;

    private transient InputSplitProvider provider;
    private transient Iterator<InputSplit> splitIterator;

    private volatile boolean isRunning = true;

    @SuppressWarnings("unchecked")
    public InputFormatSourceFunction(InputFormat<OUT, ?> format, TypeInformation<OUT> typeInfo) {
        this.format = (InputFormat<OUT, InputSplit>) format;
        this.typeInfo = typeInfo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void open(Configuration parameters) throws Exception {
        StreamingRuntimeContext context = (StreamingRuntimeContext) getRuntimeContext();

        if (format instanceof RichInputFormat) {
            ((RichInputFormat) format).setRuntimeContext(context);
        }
        format.configure(parameters);

        provider = context.getInputSplitProvider();
        serializer = typeInfo.createSerializer(getRuntimeContext().getExecutionConfig());
        splitIterator = getInputSplits();
        isRunning = splitIterator.hasNext();
    }

    @Override
    public void run(SourceContext<OUT> ctx) throws Exception {
        try {

            Counter completedSplitsCounter = getRuntimeContext().getMetricGroup().counter("numSplitsProcessed");
            if (isRunning && format instanceof RichInputFormat) {
                ((RichInputFormat) format).openInputFormat();
            }

            OUT nextElement = serializer.createInstance();
            while (isRunning) {
                format.open(splitIterator.next());

                // for each element we also check if cancel
                // was called by checking the isRunning flag

                while (isRunning && !format.reachedEnd()) {
                    nextElement = format.nextRecord(nextElement);
                    if (nextElement != null) {
                        ctx.collect(nextElement);
                    } else {
                        break;
                    }
                }
                format.close();
                completedSplitsCounter.inc();

                if (isRunning) {
                    isRunning = splitIterator.hasNext();
                }
            }
        } finally {
            format.close();
            if (format instanceof RichInputFormat) {
                ((RichInputFormat) format).closeInputFormat();
            }
            isRunning = false;
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

    @Override
    public void close() throws Exception {
        format.close();
        if (format instanceof RichInputFormat) {
            ((RichInputFormat) format).closeInputFormat();
        }
    }

    /**
     * Returns the {@code InputFormat}. This is only needed because we need to set the input
     * split assigner on the {@code StreamGraph}.
     */
    public InputFormat<OUT, InputSplit> getFormat() {
        return format;
    }

    private Iterator<InputSplit> getInputSplits() {

        return new Iterator<InputSplit>() {

            private InputSplit nextSplit;

            private boolean exhausted;

            @Override
            public boolean hasNext() {
                if (exhausted) {
                    return false;
                }

                if (nextSplit != null) {
                    return true;
                }

                final InputSplit split;
                try {
                    split = provider.getNextInputSplit(getRuntimeContext().getUserCodeClassLoader());
                } catch (InputSplitProviderException e) {
                    throw new RuntimeException("Could not retrieve next input split.", e);
                }

                if (split != null) {
                    this.nextSplit = split;
                    return true;
                } else {
                    exhausted = true;
                    return false;
                }
            }

            @Override
            public InputSplit next() {
                if (this.nextSplit == null && !hasNext()) {
                    throw new NoSuchElementException();
                }

                final InputSplit tmp = this.nextSplit;
                this.nextSplit = null;
                return tmp;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
```

- InputFormatSourceFunction是一个使用InputFormat来读取数据的SourceFunction，它继承了RichParallelSourceFunction，新增了带有2个参数的构造器，一个是InputFormat，一个是TypeInformation
- 这里有一个getInputSplits方法，它返回的是InputSplit的Iterator(`splitIterator`)，nextSplit是调用InputSplitProvider.getNextInputSplit来获取
- run方法主要是挨个调用splitIterator.next()，并用InputFormat去open该InputSplit，然后调用format.nextRecord来挨个读取该InputSplit的每个record，最后使用SourceContext的emit方法发射出去

## InputSplitProvider

flink-runtime_2.11-1.6.2-sources.jar!/org/apache/flink/runtime/jobgraph/tasks/InputSplitProvider.java



```php
/**
 * An input split provider can be successively queried to provide a series of {@link InputSplit} objects a
 * task is supposed to consume in the course of its execution.
 */
@Public
public interface InputSplitProvider {

    /**
     * Requests the next input split to be consumed by the calling task.
     *
     * @param userCodeClassLoader used to deserialize input splits
     * @return the next input split to be consumed by the calling task or <code>null</code> if the
     *         task shall not consume any further input splits.
     * @throws InputSplitProviderException if fetching the next input split fails
     */
    InputSplit getNextInputSplit(ClassLoader userCodeClassLoader) throws InputSplitProviderException;
}
```

- InputSplitProvider接口定义了getNextInputSplit方法，用于查询nextInputSplit，它有两个实现类，分别是RpcInputSplitProvider、TaskInputSplitProvider

### RpcInputSplitProvider

flink-runtime_2.11-1.6.2-sources.jar!/org/apache/flink/runtime/taskexecutor/rpc/RpcInputSplitProvider.java



```kotlin
public class RpcInputSplitProvider implements InputSplitProvider {
    private final JobMasterGateway jobMasterGateway;
    private final JobVertexID jobVertexID;
    private final ExecutionAttemptID executionAttemptID;
    private final Time timeout;

    public RpcInputSplitProvider(
            JobMasterGateway jobMasterGateway,
            JobVertexID jobVertexID,
            ExecutionAttemptID executionAttemptID,
            Time timeout) {
        this.jobMasterGateway = Preconditions.checkNotNull(jobMasterGateway);
        this.jobVertexID = Preconditions.checkNotNull(jobVertexID);
        this.executionAttemptID = Preconditions.checkNotNull(executionAttemptID);
        this.timeout = Preconditions.checkNotNull(timeout);
    }


    @Override
    public InputSplit getNextInputSplit(ClassLoader userCodeClassLoader) throws InputSplitProviderException {
        Preconditions.checkNotNull(userCodeClassLoader);

        CompletableFuture<SerializedInputSplit> futureInputSplit = jobMasterGateway.requestNextInputSplit(
            jobVertexID,
            executionAttemptID);

        try {
            SerializedInputSplit serializedInputSplit = futureInputSplit.get(timeout.getSize(), timeout.getUnit());

            if (serializedInputSplit.isEmpty()) {
                return null;
            } else {
                return InstantiationUtil.deserializeObject(serializedInputSplit.getInputSplitData(), userCodeClassLoader);
            }
        } catch (Exception e) {
            throw new InputSplitProviderException("Requesting the next input split failed.", e);
        }
    }
}
```

- RpcInputSplitProvider请求jobMasterGateway.requestNextInputSplit来获取SerializedInputSplit(`本实例的splitProvider为RpcInputSplitProvider`)

### TaskInputSplitProvider

flink-runtime_2.11-1.6.2-sources.jar!/org/apache/flink/runtime/taskmanager/TaskInputSplitProvider.java



```java
/**
 * Implementation using {@link ActorGateway} to forward the messages.
 */
public class TaskInputSplitProvider implements InputSplitProvider {

    private final ActorGateway jobManager;
    
    private final JobID jobID;
    
    private final JobVertexID vertexID;

    private final ExecutionAttemptID executionID;

    private final FiniteDuration timeout;


    public TaskInputSplitProvider(
        ActorGateway jobManager,
        JobID jobID,
        JobVertexID vertexID,
        ExecutionAttemptID executionID,
        FiniteDuration timeout) {

        this.jobManager = Preconditions.checkNotNull(jobManager);
        this.jobID = Preconditions.checkNotNull(jobID);
        this.vertexID = Preconditions.checkNotNull(vertexID);
        this.executionID = Preconditions.checkNotNull(executionID);
        this.timeout = Preconditions.checkNotNull(timeout);
    }

    @Override
    public InputSplit getNextInputSplit(ClassLoader userCodeClassLoader) throws InputSplitProviderException {
        Preconditions.checkNotNull(userCodeClassLoader);

        final Future<Object> response = jobManager.ask(
            new JobManagerMessages.RequestNextInputSplit(jobID, vertexID, executionID),
            timeout);

        final Object result;

        try {
            result = Await.result(response, timeout);
        } catch (Exception e) {
            throw new InputSplitProviderException("Did not receive next input split from JobManager.", e);
        }

        if(result instanceof JobManagerMessages.NextInputSplit){
            final JobManagerMessages.NextInputSplit nextInputSplit =
                (JobManagerMessages.NextInputSplit) result;

            byte[] serializedData = nextInputSplit.splitData();

            if(serializedData == null) {
                return null;
            } else {
                final Object deserialized;

                try {
                    deserialized = InstantiationUtil.deserializeObject(serializedData,
                        userCodeClassLoader);
                } catch (Exception e) {
                    throw new InputSplitProviderException("Could not deserialize the serialized input split.", e);
                }

                return (InputSplit) deserialized;
            }
        } else {
            throw new InputSplitProviderException("RequestNextInputSplit requires a response of type " +
                "NextInputSplit. Instead response is of type " + result.getClass() + '.');
        }

    }
}
```

- TaskInputSplitProvider请求jobManager.ask(new JobManagerMessages.RequestNextInputSplit(jobID, vertexID, executionID),timeout)来获取SerializedInputSplit

## InputSplit

flink-core-1.6.2-sources.jar!/org/apache/flink/core/io/InputSplit.java



```dart
/**
 * This interface must be implemented by all kind of input splits that can be assigned to input formats.
 * 
 * <p>Input splits are transferred in serialized form via the messages, so they need to be serializable
 * as defined by {@link java.io.Serializable}.</p>
 */
@Public
public interface InputSplit extends Serializable {
    
    /**
     * Returns the number of this input split.
     * 
     * @return the number of this input split
     */
    int getSplitNumber();
}
```

- InputSplit是所有类型的input splits必须实现的接口，它InputSplit继承了Serializable，方便进行序列化传输；getSplitNumber返回的是当前split的编号
- 它有四个实现类，其中两个实现类是直接实现该接口，分别是GenericInputSplit、LocatableInputSplit
- 另外两个分别是继承了LocatableInputSplit的FileInputSplit，以及继承了FileInputSplit的TimestampedFileInputSplit

### GenericInputSplit

flink-core-1.6.2-sources.jar!/org/apache/flink/core/io/GenericInputSplit.java



```java
/**
 * A generic input split that has only a partition number.
 */
@Public
public class GenericInputSplit implements InputSplit, java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** The number of this split. */
    private final int partitionNumber;

    /** The total number of partitions */
    private final int totalNumberOfPartitions;
    
    // --------------------------------------------------------------------------------------------

    /**
     * Creates a generic input split with the given split number.
     * 
     * @param partitionNumber The number of the split's partition.
     * @param totalNumberOfPartitions The total number of the splits (partitions).
     */
    public GenericInputSplit(int partitionNumber, int totalNumberOfPartitions) {
        this.partitionNumber = partitionNumber;
        this.totalNumberOfPartitions = totalNumberOfPartitions;
    }

    //......
    
    public String toString() {
        return "GenericSplit (" + this.partitionNumber + '/' + this.totalNumberOfPartitions + ')';
    }
}
```

- GenericInputSplit比较简单，只有两个属性，分别是partitionNumber、totalNumberOfPartitions(`本实例的InputSplit为GenericInputSplit类型`)

### LocatableInputSplit

flink-core-1.6.2-sources.jar!/org/apache/flink/core/io/LocatableInputSplit.java



```java
/**
 * A locatable input split is an input split referring to input data which is located on one or more hosts.
 */
@Public
public class LocatableInputSplit implements InputSplit, java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    private static final String[] EMPTY_ARR = new String[0];
    
    /** The number of the split. */
    private final int splitNumber;

    /** The names of the hosts storing the data this input split refers to. */
    private final String[] hostnames;

    // --------------------------------------------------------------------------------------------
    
    /**
     * Creates a new locatable input split that refers to a multiple host as its data location.
     * 
     * @param splitNumber The number of the split
     * @param hostnames The names of the hosts storing the data this input split refers to.
     */
    public LocatableInputSplit(int splitNumber, String[] hostnames) {
        this.splitNumber = splitNumber;
        this.hostnames = hostnames == null ? EMPTY_ARR : hostnames;
    }

    /**
     * Creates a new locatable input split that refers to a single host as its data location.
     *
     * @param splitNumber The number of the split.
     * @param hostname The names of the host storing the data this input split refers to.
     */
    public LocatableInputSplit(int splitNumber, String hostname) {
        this.splitNumber = splitNumber;
        this.hostnames = hostname == null ? EMPTY_ARR : new String[] { hostname };
    }

    //......
    
    @Override
    public String toString() {
        return "Locatable Split (" + splitNumber + ") at " + Arrays.toString(this.hostnames);
    }
}
```

- LocatableInputSplit是可定位的input split，它有两个属性，分别是splitNumber以及该split对应的数据所在的hostnames

## IteratorInputFormat

flink-java-1.6.2-sources.jar!/org/apache/flink/api/java/io/IteratorInputFormat.java



```java
/**
 * An input format that returns objects from an iterator.
 */
@PublicEvolving
public class IteratorInputFormat<T> extends GenericInputFormat<T> implements NonParallelInput {

    private static final long serialVersionUID = 1L;

    private Iterator<T> iterator; // input data as serializable iterator

    public IteratorInputFormat(Iterator<T> iterator) {
        if (!(iterator instanceof Serializable)) {
            throw new IllegalArgumentException("The data source iterator must be serializable.");
        }

        this.iterator = iterator;
    }

    @Override
    public boolean reachedEnd() {
        return !this.iterator.hasNext();
    }

    @Override
    public T nextRecord(T record) {
        return this.iterator.next();
    }
}
```

- IteratorInputFormat主要是对Iterator进行了包装，实现了reachedEnd、nextRecord接口；它继承了GenericInputFormat

### GenericInputFormat

flink-core-1.6.2-sources.jar!/org/apache/flink/api/common/io/GenericInputFormat.java



```java
/**
 * Generic base class for all Rich inputs that are not based on files.
 */
@Public
public abstract class GenericInputFormat<OT> extends RichInputFormat<OT, GenericInputSplit> {

    private static final long serialVersionUID = 1L;
    
    /**
     * The partition of this split.
     */
    protected int partitionNumber;

    // --------------------------------------------------------------------------------------------
    
    @Override
    public void configure(Configuration parameters) {
        //  nothing by default
    }

    @Override
    public BaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException {
        // no statistics available, by default.
        return cachedStatistics;
    }

    @Override
    public GenericInputSplit[] createInputSplits(int numSplits) throws IOException {
        if (numSplits < 1) {
            throw new IllegalArgumentException("Number of input splits has to be at least 1.");
        }

        numSplits = (this instanceof NonParallelInput) ? 1 : numSplits;
        GenericInputSplit[] splits = new GenericInputSplit[numSplits];
        for (int i = 0; i < splits.length; i++) {
            splits[i] = new GenericInputSplit(i, numSplits);
        }
        return splits;
    }
    
    @Override
    public DefaultInputSplitAssigner getInputSplitAssigner(GenericInputSplit[] splits) {
        return new DefaultInputSplitAssigner(splits);
    }

    // --------------------------------------------------------------------------------------------

    @Override
    public void open(GenericInputSplit split) throws IOException {
        this.partitionNumber = split.getSplitNumber();
    }

    @Override
    public void close() throws IOException {}
}
```

- RpcInputSplitProvider是调用JobMaster.requestNextInputSplit来获取SerializedInputSplit，而JobMaster是调用splitAssigner.getNextInputSplit(host, taskId)，这里的splitAssigner，即为DefaultInputSplitAssigner(`从vertex.getSplitAssigner()获取`)
- 而vertex.getSplitAssigner()返回的splitAssigner，是ExecutionJobVertex在构造器里头根据splitSource.getInputSplitAssigner(splitSource.createInputSplits(numTaskVertices))得来的
- 而splitSource即为这里的IteratorInputFormat，而IteratorInputFormat的createInputSplits(`根据numTaskVertices来分割`)及getInputSplitAssigner方法均为父类GenericInputFormat提供

### DefaultInputSplitAssigner

flink-core-1.6.2-sources.jar!/org/apache/flink/api/common/io/DefaultInputSplitAssigner.java



```java
/**
 * This is the default implementation of the {@link InputSplitAssigner} interface. The default input split assigner
 * simply returns all input splits of an input vertex in the order they were originally computed.
 */
@Internal
public class DefaultInputSplitAssigner implements InputSplitAssigner {

    /** The logging object used to report information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(DefaultInputSplitAssigner.class);

    /** The list of all splits */
    private final List<InputSplit> splits = new ArrayList<InputSplit>();


    public DefaultInputSplitAssigner(InputSplit[] splits) {
        Collections.addAll(this.splits, splits);
    }
    
    public DefaultInputSplitAssigner(Collection<? extends InputSplit> splits) {
        this.splits.addAll(splits);
    }
    
    
    @Override
    public InputSplit getNextInputSplit(String host, int taskId) {
        InputSplit next = null;
        
        // keep the synchronized part short
        synchronized (this.splits) {
            if (this.splits.size() > 0) {
                next = this.splits.remove(this.splits.size() - 1);
            }
        }
        
        if (LOG.isDebugEnabled()) {
            if (next == null) {
                LOG.debug("No more input splits available");
            } else {
                LOG.debug("Assigning split " + next + " to " + host);
            }
        }
        return next;
    }
}
```

- DefaultInputSplitAssigner仅仅是按顺序返回InputSplit

## 小结

- InputFormatSourceFunction是一个使用InputFormat来读取数据的SourceFunction，它继承了RichParallelSourceFunction，新增了带有2个参数的构造器，一个是InputFormat，一个是TypeInformation
- 本实例使用的IteratorInputFormat继承了GenericInputFormat，后者提供了两个重要的方法，一个是createInputSplits(`这里是根据numTaskVertices来分割`)，一个是getInputSplitAssigner(`这里创建的是DefaultInputSplitAssigner，即按顺序返回分割好的InputSplit`)
- InputFormatSourceFunction的run方法主要是挨个调用splitIterator.next()，并用InputFormat去open该InputSplit，然后调用format.nextRecord来挨个读取该InputSplit的每个record，最后使用SourceContext的emit方法发射出去

> 可以看到整个大的逻辑就是GenericInputFormat提供将input分割为InputSplit的方法，同时提供InputSplitAssigner，然后InputFormatSourceFunction就是挨个遍历分割好的属于自己(`Task`)的InputSplit(`通过InputSplitAssigner获取`)，然后通过InputFormat读取InputSplit来挨个获取这个InputSplit的每个元素，然后通过SourceContext的emit方法发射出去

## doc

- [InputFormatSourceFunction](https://ci.apache.org/projects/flink/flink-docs-release-1.6/api/java/org/apache/flink/streaming/api/functions/source/InputFormatSourceFunction.html)



作者：go4it
链接：https://www.jianshu.com/p/ef9b9c37a4b6
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。