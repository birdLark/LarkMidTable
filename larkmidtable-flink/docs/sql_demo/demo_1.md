## demo1 单流kafka写入mysqld  参考

配置参考: 
    [jdbc](https://ci.apache.org/projects/flink/flink-docs-release-1.12/dev/table/connectors/jdbc.html)
    [kafka](https://ci.apache.org/projects/flink/flink-docs-release-1.12/dev/table/connectors/kafka.html)

触发方式: 针对每条触发一次

source kafka json 数据格式  
    topic: flink_test
    msg: {"day_time": "20201009","id": 7,"amnount":20}

sink mysql 创建语句

```sql
CREATE TABLE sync_test_1 (
  `day_time` varchar(64) NOT NULL,
  `total_gmv` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`day_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

```

配置语句

```sql

create table flink_test_1 ( 
  id BIGINT,
  day_time VARCHAR,
  amnount BIGINT,
  proctime AS PROCTIME ()
)
 with ( 
  'connector' = 'kafka',
  'topic' = 'flink_test',
  'properties.bootstrap.servers' = '172.25.20.76:9092', 
  'properties.group.id' = 'flink_gp_test1',
  'scan.startup.mode' = 'earliest-offset',
  'format' = 'json',
  'json.fail-on-missing-field' = 'false',
  'json.ignore-parse-errors' = 'true',
  'properties.zookeeper.connect' = '172.25.20.76:2181/kafka'
 );

CREATE TABLE sync_test_1 (
                   day_time string,
                   total_gmv bigint,
                   PRIMARY KEY (day_time) NOT ENFORCED
 ) WITH (
   'connector' = 'jdbc',
   'url' = 'jdbc:mysql://172.25.21.10:3306/flink_web?characterEncoding=UTF-8',
   'table-name' = 'sync_test_1',
   'username' = 'videoweb',
   'password' = 'suntek'
 );

INSERT INTO sync_test_1 
SELECT day_time,SUM(amnount) AS total_gmv
FROM flink_test_1
GROUP BY day_time;

``` 

