# 1.启动脚本：

```
#!/bin/bash
for host in mini4 mini5 mini6
do
    ssh $host "source /etc/profile;/home/hadoop/data/zookeeper-3.4.5/bin/zkServer.sh start"
    echo "$host zk is running"
 done
```

# 2.停止脚本：

```
#!/bin/bash

for host in mini4 mini5 mini6
do
    ssh $host "source /etc/profile;/home/hadoop/data/zookeeper-3.4.5/bin/zkServer.sh stop"
    echo "$host zk is stopping"
done
```

# 3.查看状态脚本：

```
#!/bin/bash

for host in mini4 mini5 mini6
do
    ssh $host "source /etc/profile;/home/hadoop/data/zookeeper-3.4.5/bin/zkServer.sh status"
    echo "$host zk is stopping"
done
```



可以添加首页QQ群获取脚本。