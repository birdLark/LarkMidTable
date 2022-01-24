## demo4 滚动窗口


source kafka json 数据格式  

topic  flink_test_4  

{"username":"zhp","click_url":"https://www.infoq.cn/","ts":"2021-01-05 11:12:12"}
{"username":"zhp","click_url":"https://www.infoq.cn/video/BYSSg4hGR5oZmUFsL8Kb","ts":"2020-01-05 11:12:15"}
{"username":"zhp","click_url":"https://www.infoq.cn/talks","ts":"2020-01-05 11:12:18"}
{"username":"zhp","click_url":"https://www.infoq.cn/","ts":"2021-01-05 11:12:55"}
{"username":"zhp","click_url":"https://www.infoq.cn/","ts":"2021-01-05 11:13:25"}
{"username":"zhp","click_url":"https://www.infoq.cn/talks","ts":"2021-01-05 11:13:25"}
{"username":"zhp","click_url":"https://www.infoq.cn/talks","ts":"2021-01-05 11:13:26"}

sink mysql 创建语句

```sql

CREATE TABLE `sync_test_tumble_output` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `window_start` datetime DEFAULT NULL,
  `window_end` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `clicks` bigint(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

```

配置语句

```sql
-- -- 开启 mini-batch 指定是否启用小批量优化 （相关配置说明 https://ci.apache.org/projects/flink/flink-docs-release-1.12/zh/dev/table/config.html）
SET table.exec.mini-batch.enabled=true;
-- -- mini-batch的时间间隔，即作业需要额外忍受的延迟
SET table.exec.mini-batch.allow-latency=60s;
-- -- 一个 mini-batch 中允许最多缓存的数据
SET table.exec.mini-batch.size=5;

create table user_clicks ( 
  username varchar,
  click_url varchar,
  ts timestamp,
--   ts BIGINT,
--   ts2 AS TO_TIMESTAMP(FROM_UNIXTIME(ts / 1000, 'yyyy-MM-dd HH:mm:ss')),
  WATERMARK FOR ts AS ts - INTERVAL '20' SECOND 

)
with ( 
    'connector' = 'kafka',
    'topic' = 'flink_test_4',
    'properties.bootstrap.servers' = '172.25.20.76:9092', 
    'properties.group.id' = 'flink_gp_test4',
    'scan.startup.mode' = 'earliest-offset',
    'format' = 'json',
    'json.fail-on-missing-field' = 'false',
    'json.ignore-parse-errors' = 'true',
    'properties.zookeeper.connect' = '172.25.20.76:2181/kafka'
  );


CREATE TABLE sync_test_tumble_output (
				 window_start TIMESTAMP(3),
				 window_end TIMESTAMP(3),
				 username VARCHAR,
				 clicks BIGINT
 ) WITH (
    'connector' = 'jdbc',
    'url' = 'jdbc:mysql://172.25.21.10:3306/flink_web?characterEncoding=UTF-8',
    'table-name' = 'sync_test_tumble_output',
    'username' = 'videoweb',
    'password' = 'suntek'
  );
 
 
 INSERT INTO sync_test_tumble_output
 SELECT
 TUMBLE_START(ts, INTERVAL '60' SECOND) as window_start,
 TUMBLE_END(ts, INTERVAL '60' SECOND) as window_end,
 username,
 COUNT(click_url)
 FROM user_clicks
 GROUP BY TUMBLE(ts, INTERVAL '60' SECOND), username; 
``` 


