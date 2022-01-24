## demo2 双流kafka写入mysql 参考


source kafka json 数据格式  

topic  flink_test_1  {"day_time": "20201011","id": 8,"amnount":211}
topic  flink_test_2  {"id": 8,"coupon_amnount":100}

注意：针对双流中的每条记录都发触发

sink mysql 创建语句

```sql
CREATE TABLE `sync_test_2` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `day_time` varchar(64) DEFAULT NULL,
  `total_gmv` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx` (`day_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

```

配置语句

```sql
create table flink_test_2_1 ( 
  id BIGINT,
  day_time VARCHAR,
  amnount BIGINT,
  proctime AS PROCTIME ()
)
 with ( 
   'connector' = 'kafka',
   'topic' = 'flink_test_1',
   'properties.bootstrap.servers' = '172.25.20.76:9092', 
   'properties.group.id' = 'flink_gp_test2-1',
   'scan.startup.mode' = 'earliest-offset',
   'format' = 'json',
   'json.fail-on-missing-field' = 'false',
   'json.ignore-parse-errors' = 'true',
   'properties.zookeeper.connect' = '172.25.20.76:2181/kafka'
 );


create table flink_test_2_2 ( 
  id BIGINT,
  coupon_amnount BIGINT,
  proctime AS PROCTIME ()
)
 with ( 
   'connector' = 'kafka',
   'topic' = 'flink_test_2',
   'properties.bootstrap.servers' = '172.25.20.76:9092', 
   'properties.group.id' = 'flink_gp_test2-2',
   'scan.startup.mode' = 'earliest-offset',
   'format' = 'json',
   'json.fail-on-missing-field' = 'false',
   'json.ignore-parse-errors' = 'true',
   'properties.zookeeper.connect' = '172.25.20.76:2181/kafka'
 );


CREATE TABLE sync_test_2 (
                   day_time string,
                   total_gmv bigint,
                   PRIMARY KEY (day_time) NOT ENFORCED
 ) WITH (
   'connector' = 'jdbc',
   'url' = 'jdbc:mysql://172.25.21.10:3306/flink_web?characterEncoding=UTF-8',
   'table-name' = 'sync_test_2',
   'username' = 'videoweb',
   'password' = 'suntek'
 );

INSERT INTO sync_test_2 
SELECT 
  day_time, 
  SUM(amnount - coupon_amnount) AS total_gmv 
FROM 
  (
    SELECT
      a.day_time as day_time, 
      a.amnount as amnount, 
      b.coupon_amnount as coupon_amnount 
    FROM 
      flink_test_2_1 as a 
      LEFT JOIN flink_test_2_2 b on b.id = a.id
  ) 
GROUP BY 
  day_time;

``` 


