1.Main方法获取Reader,114行

```
BaseDataReader dataReader = DataReaderFactory.getDataReader(config, env);
```

2.刨析DataReaderFactory的getDataReader方法

```java
public static BaseDataReader getDataReader(DataTransferConfig config, StreamExecutionEnvironment env) {
        try {
            String pluginName =config.getJob().getContent().get(0).getReader().getName();
            String pluginClassName = PluginUtil.getPluginClassName(pluginName);
            Set<URL> urlList = PluginUtil.getJarFileDirPath(pluginName, 																			config.getPluginRoot());
            return ClassLoaderManager.newInstance(urlList, cl -> {
                Class<?> clazz = cl.loadClass(pluginClassName);
                Constructor constructor = 
                    clazz.getConstructor(DataTransferConfig.class, 					                                              StreamExecutionEnvironment.class);
                return (BaseDataReader)constructor.newInstance(config, env);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
```

pluginName：streamReader

pluginClassName：com.dtstack.flinkx.stream.reader.StreamReader

urlList：