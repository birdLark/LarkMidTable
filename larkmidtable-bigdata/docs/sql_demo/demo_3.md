## demo3 kafka和mysql维表实时关联写入mysql 参考


source kafka json 数据格式  

topic  flink_test_1  {"day_time": "20201011","id": 8,"amnount":211}

dim    test_dim

```sql
CREATE TABLE `test_dim` (
  `id` bigint(11) NOT NULL,
  `coupon_amnount` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of test_dim
-- ----------------------------
BEGIN;
INSERT INTO `test_dim` VALUES (1, 1);
INSERT INTO `test_dim` VALUES (3, 1);
INSERT INTO `test_dim` VALUES (8, 1);
COMMIT;

```


sink mysql 创建语句

```sql

CREATE TABLE `sync_test_3` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `day_time` varchar(64) DEFAULT NULL,
  `total_gmv` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx` (`day_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

```

配置语句

```sql

create table flink_test_3 ( 
  id BIGINT,
  day_time VARCHAR,
  amnount BIGINT,
  proctime AS PROCTIME ()
)
 with ( 
    'connector' = 'kafka',
    'topic' = 'flink_test_1',
    'properties.bootstrap.servers' = '172.25.20.76:9092', 
    'properties.group.id' = 'flink_gp_test3',
    'scan.startup.mode' = 'earliest-offset',
    'format' = 'json',
    'json.fail-on-missing-field' = 'false',
    'json.ignore-parse-errors' = 'true',
    'properties.zookeeper.connect' = '172.25.20.76:2181/kafka'
  );


create table flink_test_3_dim ( 
  id BIGINT,
  coupon_amnount BIGINT
)
 with ( 
   'connector' = 'jdbc',
   'url' = 'jdbc:mysql://172.25.21.10:3306/flink_web?characterEncoding=UTF-8',
   'table-name' = 'test_dim',
   'username' = 'videoweb',
   'password' = 'suntek',
   'lookup.max-retries' = '3',
   'lookup.cache.max-rows' = 1000
 );


CREATE TABLE sync_test_3 (
                   day_time string,
                   total_gmv bigint,
                   PRIMARY KEY (day_time) NOT ENFORCED
 ) WITH (
   'connector' = 'jdbc',
   'url' = 'jdbc:mysql://172.25.21.10:3306/flink_web?characterEncoding=UTF-8',
   'table-name' = 'sync_test_3',
   'username' = 'videoweb',
   'password' = 'suntek'
 );


INSERT INTO sync_test_3 
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
      flink_test_3 as a 
      LEFT JOIN flink_test_3_dim  FOR SYSTEM_TIME AS OF  a.proctime  as b
     ON b.id = a.id
  ) 
GROUP BY day_time;


``` 


