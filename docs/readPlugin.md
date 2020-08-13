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

