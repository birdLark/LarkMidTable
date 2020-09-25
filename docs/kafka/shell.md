# 1.启动脚本：

```
#!/bin/bash
for host in mini4 mini5 mini6
do
    ssh $host "source /etc/profile;/home/hadoop/data/kafka/bin/kafka-server-start.sh /home/hadoop/data/kafka/config/server.properties  >/dev/null 2>&1 &"
    echo "$host kafka is running"
done
```



# 2.停止脚本：

```
#! /bin/sh
for host in mini4 mini5 mini6
do
        ssh $host "source /etc/profile;/home/hadoop/data/kafka/bin/kafka-server-stop.sh"
        echo "$host kafka is stopping"
done`
```

```
vi  kafka-server-stop.sh 

PIDS=$(jps -lm | grep -i 'kafka.Kafka' | awk '{print $1}')
```

可以添加首页QQ群获取脚本。