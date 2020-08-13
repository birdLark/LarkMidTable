### 1.读插件

```
BaseRichInputFormat  extends    RichInputFormat  implements InputFormat
```

### 2.BaseRichInputFormat的抽象方法：

```
执行方法的顺序：
1.创建数据分片，只会调用1次，140行   :createInputSplitsInternal(int i)
2.打开数据库链接，只会调用1次，97行  :openInternal(InputSplit inputSplit)
3.父类InputFormat判断是否结束	  :reachedEnd()
4.读取下条数据301行 			   :nextRecordInternal(Row row)
5.关闭数据库链接358行			  :closeInternal()
```

### 3.StreamInputForma类的刨析

含有的3个参数说明：

**sliceRecordCount**:每个通道生成的数据条数

```
public void openInternal(InputSplit inputSplit) throws IOException {
    
    if(CollectionUtils.isNotEmpty(sliceRecordCount) && sliceRecordCount.size() >     	
                                  inputSplit.getSplitNumber()){
       
        channelRecordNum = sliceRecordCount.get(inputSplit.getSplitNumber());
    }
    // The record number of channel:[0] is [100]
    LOG.info("The record number of channel:[{}] is [{}]", inputSplit.getSplitNumber(), channelRecordNum);
}
```



