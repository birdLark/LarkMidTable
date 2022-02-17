## demo5 滑动窗口


source kafka json 数据格式  

topic  flink_test_5  

{"username":"zhp","click_url":"https://www.infoq.cn/","ts":"2020-01-05 11:12:12"}
{"username":"zhp","click_url":"https://www.infoq.cn/video/BYSSg4hGR5oZmUFsL8Kb","ts":"2020-01-05 11:12:15"}
{"username":"zhp","click_url":"https://www.infoq.cn/talks","ts":"2020-01-05 11:12:18"}
{"username":"zhp","click_url":"https://www.infoq.cn/","ts":"2020-01-05 11:12:55"}
{"username":"zhp","click_url":"https://www.infoq.cn/","ts":"2020-01-05 11:13:25"}
{"username":"zhp","click_url":"https://www.infoq.cn/talks","ts":"2020-01-05 11:13:25"}
{"username":"zhp","click_url":"https://www.infoq.cn/talks","ts":"2020-01-05 11:13:26"}


sink mysql 创建语句

```sql

CREATE TABLE `sync_test_hop_output` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `window_start` datetime DEFAULT NULL,
  `window_end` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `clicks` bigint(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

```

配置语句

```sql
-- -- 开启 mini-batch （相关配置说明 https://ci.apache.org/projects/flink/flink-docs-release-1.10/zh/dev/table/config.html）
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
  WATERMARK FOR ts AS ts - INTERVAL '5' SECOND 

)
with ( 
    'connector' = 'kafka',
    'topic' = 'flink_test_5',
    'properties.bootstrap.servers' = '172.25.20.76:9092', 
    'properties.group.id' = 'flink_gp_test5',
    'scan.startup.mode' = 'earliest-offset',
    'format' = 'json',
    'json.fail-on-missing-field' = 'false',
    'json.ignore-parse-errors' = 'true',
    'properties.zookeeper.connect' = '172.25.20.76:2181/kafka'
  );

CREATE TABLE sync_test_hop_output (
				  window_start TIMESTAMP(3),
				  window_end TIMESTAMP(3),
				  username VARCHAR,
				  clicks BIGINT
 ) WITH (
    'connector' = 'jdbc',
    'url' = 'jdbc:mysql://172.25.21.10:3306/flink_web?characterEncoding=UTF-8',
    'table-name' = 'sync_test_hop_output',
    'username' = 'videoweb',
    'password' = 'suntek'
  );
 
 --统计每个用户过去1分钟的单击次数，每30秒更新1次，即1分钟的窗口，30秒滑动1次
 INSERT INTO sync_test_hop_output
 SELECT
  HOP_START (ts, INTERVAL '30' SECOND, INTERVAL '1' MINUTE) as window_start,
  HOP_END (ts, INTERVAL '30' SECOND, INTERVAL '1' MINUTE) as window_end,
 username,
 COUNT(click_url)
 FROM user_clicks
 GROUP BY HOP (ts, INTERVAL '30' SECOND, INTERVAL '1' MINUTE), username; 


``` 


