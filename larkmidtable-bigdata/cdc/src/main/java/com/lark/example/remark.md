1.dataFrame 和SQL 之间区别:
1.12 1.13 可以使用;
能够监控多库，多表;
需要自定义反序列化器
-----SQL
1.13 可以使用;
FlinkSQL只能监控单库单表;
不需要定义反序列化器

2.cdc2.0版本解决的痛点问题
无锁
水平扩展
支持checkpoint

1.chunk切分(按照主键切分)
2.chunk分配(任务分配后发给SourceReader并行读取数据&checkpoint)
3.chunk读取(实现无锁读取) 简单理解就是先缓存，然后合并
    1）SourceReaderr 读取表数据之前先记录当前的 Binlog 位置信息记为低位点；
    2）SourceReader 将自身区间内的数据查询出来并放置在 buffer 中；
    3）查询完成之后记录当前的 Binlog 位置信息记为高位点；
    4）在增量部分消费从低位点到高位点的 Binlog； 
    5）根据主键，对 buffer 中的数据进行修正并输出。
4.chunk汇报( Snapshot Chunk 读取完成之后，SourceReader需要将 Snapshot Chunk 完成信息汇报给 SourceEnumerator。)
5.chunk分配(
FlinkCDC 是支持全量+增量数据同步的，在 SourceEnumerator 接收到所有的 Snapshot
Chunk 完成信息之后，还有一个消费增量数据（Binlog）的任务,
SourceReader 进行单并发读取来实现的)

3.cdc部分源码刨析
问题1： Binlog Chunk 中开始读取位置，是从那里开始进行读取的？  最小的offset值读取
`BinlogOffset minBinlogOffset = BinlogOffset.INITIAL_OFFSET;
  for (MySqlSnapshotSplit split : assignedSnapshotSplit) {
  // 查找最小的OFFSET值
  BinlogOffset binlogOffset = splitFinishedOffsets.get(split.splitId());
  if (binlogOffset.compareTo(minBinlogOffset) < 0) {
    minBinlogOffset = binlogOffset;
  }`

问题2:如何读取低位点到高位点之间的 Binlog，这样读取会不会出现数据重复？
`private boolean shouldEmit(SourceRecord sourceRecord) {
  //查看数据是否改变
  if (isDataChangeRecord(sourceRecord)) {
  TableId tableId = getTableId(sourceRecord);
  BinlogOffset position = getBinlogPosition(sourceRecord);
  //如果记录的offset值比高位点还大，那么返回true
  if (position.isAtOrBefore(maxSplitHighWatermarkMap.get(tableId))) {
  return true;
  }
  Object[] key =
  getSplitKey(
  currentBinlogSplit.getSplitKeyType(),
  sourceRecord,
  statefulTaskContext.getSchemaNameAdjuster());
  for (FinishedSnapshotSplitInfo splitInfo : finishedSplitsInfo.get(tableId)) {
  // 1.数据必须是在chunk的范围内，2.数据的offset值大于当前读取数据范围最大值
  // 例如 chuck【0，1024】 数据读取范围【300-500】 新来的数据是501，那么当前数据是满足要求
  if (RecordUtils.splitKeyRangeContains(
  key, splitInfo.getSplitStart(), splitInfo.getSplitEnd())
  && position.isAtOrBefore(splitInfo.getHighWatermark())) {
  return true;
  }
  }
  // not in the monitored splits scope, do not emit
  return false;
  }
  // always send the schema change event and signal event
  // we need record them to state of Flink
  return true;
  }`






