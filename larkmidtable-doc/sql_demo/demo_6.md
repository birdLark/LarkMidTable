# 参考
    https://github.com/ververica/flink-cdc-connectors/wiki/%E4%B8%AD%E6%96%87%E6%95%99%E7%A8%8B
    [基于 Flink SQL CDC的实时数据同步方案](http://www.dreamwu.com/post-1594.html)

# 1. mysql配置
## 1.1. 主从同步配置、数据准备
    1、修改配置
        [mysqld]
        # 前面还有其他配置
        # 添加的部分
        server-id = 12345
        log-bin = mysql-bin
        # 必须为ROW
        binlog_format = ROW
        # 必须为FULL，MySQL-5.7后才有该参数
        binlog_row_image  = FULL
        expire_logs_days  = 15
    
    2、验证
    SHOW VARIABLES LIKE '%binlog%';
    	
    3、设置权限
        -- 设置拥有同步权限的用户
        CREATE USER 'flinkuser' IDENTIFIED BY 'flinkpassword';
        -- 赋予同步相关权限
        GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'flinkuser';
        
        创建用户并赋予权限成功后，使用该用户登录MySQL，可以使用以下命令查看主从同步相关信息
        SHOW MASTER STATUS
        SHOW SLAVE STATUS
        SHOW BINARY LOGS

# 2. CDC Streaming ETL
模拟电商公司的订单表和物流表，需要对订单数据进行统计分析，对于不同的信息需要进行关联后续形成订单的大宽表后，交给下游的业务方使用 ES 做数据分析，
这个案例演示了如何只依赖 Flink 不依赖其他组件，借助 Flink 强大的计算能力实时把 Binlog 的数据流关联一次并同步至 ES
## 2.1 实时根据binlog将商品表、订单表、物流表合成宽表写到ES中
## 2.1.1. 数据库建表SQL
```sql
CREATE TABLE products (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(512)
);
ALTER TABLE products AUTO_INCREMENT = 101;

INSERT INTO products
VALUES (default,"scooter","Small 2-wheel scooter"),
       (default,"car battery","12V car battery"),
       (default,"12-pack drill bits","12-pack of drill bits with sizes ranging from #40 to #3"),
       (default,"hammer","12oz carpenter's hammer"),
       (default,"hammer","14oz carpenter's hammer"),
       (default,"hammer","16oz carpenter's hammer"),
       (default,"rocks","box of assorted rocks"),
       (default,"jacket","water resistent black wind breaker"),
       (default,"spare tire","24 inch spare tire");

CREATE TABLE orders (
  order_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  order_date DATETIME NOT NULL,
  customer_name VARCHAR(255) NOT NULL,
  price DECIMAL(10, 5) NOT NULL,
  product_id INTEGER NOT NULL,
  order_status BOOLEAN NOT NULL -- 是否下单
) AUTO_INCREMENT = 10001;

INSERT INTO orders
VALUES (default, '2020-07-30 10:08:22', 'Jark', 50.50, 102, false),
       (default, '2020-07-30 10:11:09', 'Sally', 15.00, 105, false),
       (default, '2020-07-30 12:00:30', 'Edward', 25.25, 106, false);

CREATE TABLE shipments (
  shipment_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  order_id INTEGER NOT NULL,
  origin VARCHAR(255) NOT NULL,
  destination VARCHAR(255) NOT NULL,
  is_arrived BOOLEAN NOT NULL
) AUTO_INCREMENT = 1001;;

INSERT INTO shipments
VALUES (default,10001,'Beijing','Shanghai',false),
       (default,10002,'Hangzhou','Shanghai',false),
       (default,10003,'Shanghai','Hangzhou',false);
```

## 2.1.2. flink配置
```sql
CREATE TABLE products (
  id INT,
  name STRING,
  description STRING
) WITH (
  'connector' = 'mysql-cdc',
  'hostname' = '172.25.21.29',
  'port' = '3306',
  'username' = 'flinkuser',
  'password' = 'flinkpassword',
  'database-name' = 'db_inventory_cdc',
  'table-name' = 'products'
);

CREATE TABLE orders (
  order_id INT,
  order_date TIMESTAMP(0),
  customer_name STRING,
  price DECIMAL(10, 5),
  product_id INT,
  order_status BOOLEAN
) WITH (
  'connector' = 'mysql-cdc',
  'hostname' = '172.25.21.29',
  'port' = '3306',
  'username' = 'flinkuser',
  'password' = 'flinkpassword',
  'database-name' = 'db_inventory_cdc',
  'table-name' = 'orders'
);

CREATE TABLE shipments (
  shipment_id INT,
  order_id INT,
  origin STRING,
  destination STRING,
  is_arrived BOOLEAN
) WITH (
  'connector' = 'mysql-cdc',
  'hostname' = '172.25.21.29',
  'port' = '3306',
  'username' = 'flinkuser',
  'password' = 'flinkpassword',
  'database-name' = 'db_inventory_cdc',
  'table-name' = 'shipments'
);

CREATE TABLE enriched_orders (
  order_id INT,
  order_date TIMESTAMP(0),
  customer_name STRING,
  price DECIMAL(10, 5),
  product_id INT,
  order_status BOOLEAN,
  product_name STRING,
  product_description STRING,
  shipment_id INT,
  origin STRING,
  destination STRING,
  is_arrived BOOLEAN,
  PRIMARY KEY (order_id) NOT ENFORCED
) WITH (
    'connector' = 'elasticsearch-7',
    'hosts' = 'http://172.25.23.15:9401',
    'index' = 'enriched_orders'
);

INSERT INTO enriched_orders
SELECT o.*, p.name, p.description, s.shipment_id, s.origin, s.destination, s.is_arrived
FROM orders AS o
LEFT JOIN products AS p ON o.product_id = p.id
LEFT JOIN shipments AS s ON o.order_id = s.order_id;
```

## 2.1.3. 验证SQL
```sql
--增加记录
INSERT INTO orders VALUES (default, '2020-07-30 15:22:00', 'Jark', 29.71, 104, false);
INSERT INTO shipments VALUES (default,10004,'Shanghai','Beijing',false);
--更新记录
UPDATE orders SET order_status = true WHERE order_id = 10004;
UPDATE shipments SET is_arrived = true WHERE shipment_id = 4;
--删除记录
DELETE FROM orders WHERE order_id = 10004;
```

## 2.1.4. 手工启动脚本
注意:相应的jar包需要到https://maven.aliyun.com/mvn/search下载，特别是cdc相关的jar
```shell script
/data1/flink/flink/bin/flink run -d -p 2 
-C file:///data1/flink/flink-streaming-platform-web/jars/flink-connector-jdbc_2.11-1.12.0.jar  
-C file:///data1/flink/flink-streaming-platform-web/jars/flink-sql-connector-kafka_2.11-1.12.0.jar  
-C file:///data1/flink/flink-streaming-platform-web/jars/flink-sql-connector-elasticsearch7_2.11-1.12.0.jar 
-c  com.flink.streaming.core.JobApplication /data1/flink/flink-streaming-platform-web/lib/flink-streaming-core_flink_1.12.0-1.2.0.RELEASE.jar 
-sql /data1/flink/flink-streaming-platform-web/sql/job_sql_8.sql
```

## 2.2. 按天统计销售额
### 2.2.1. flink 配置
```sql
set table.exec.source.cdc-events-duplicate = true;
CREATE TABLE orders (
  order_id INT,
  order_date TIMESTAMP(0),
  customer_name STRING,
  price DECIMAL(10, 5),
  product_id INT,
  order_status BOOLEAN,
  PRIMARY KEY (day_str) NOT ENFORCED,
) WITH (
  'connector' = 'mysql-cdc',
  'hostname' = '172.25.21.29',
  'port' = '3306',
  'username' = 'flinkuser',
  'password' = 'flinkpassword',
  'database-name' = 'db_inventory_cdc',
  'table-name' = 'orders'
);

CREATE TABLE kafka_gmv (
  day_str STRING,
  gmv DECIMAL(10, 5),
  PRIMARY KEY (day_str) NOT ENFORCED
) WITH (
    'connector' = 'kafka',
    'topic' = 'flink_test_6',
    'scan.startup.mode' = 'earliest-offset',
    'properties.group.id' = 'flink_gp_test6',
    'properties.bootstrap.servers' = '172.25.20.76:9092',
    'format' = 'debezium-json',
    'debezium-json.ignore-parse-errors' = 'true',
    'debezium-json.timestamp-format.standard' = 'SQL',
    'debezium-json.map-null-key.mode' = 'DROP'
);

INSERT INTO kafka_gmv
SELECT DATE_FORMAT(order_date, 'yyyy-MM-dd') as day_str, SUM(price) as gmv
FROM orders
WHERE order_status = true
GROUP BY DATE_FORMAT(order_date, 'yyyy-MM-dd');

-- 读取 Kafka 的 changelog 数据，观察 materialize 后的结果
 CREATE TABLE print_table (
   day_str STRING,
   gmv DECIMAL(10, 5),
   PRIMARY KEY (day_str) NOT ENFORCED
 ) WITH (
  'connector' = 'print'
 );
  
insert into print_table SELECT * FROM kafka_gmv;
```
## 2.2.2. 验证SQL
```sql
UPDATE orders SET order_status = true WHERE order_id = 10001;
UPDATE orders SET order_status = true WHERE order_id = 10002;
UPDATE orders SET order_status = true WHERE order_id = 10003;

INSERT INTO orders
VALUES (default, '2020-07-30 17:33:00', 'Timo', 50.00, 104, true);

UPDATE orders SET price = 40.00 WHERE order_id = 10005;

DELETE FROM orders WHERE order_id = 10005;
```

## 2.2.2. 手工启动脚本
注意: 相应的jar包需要到https://maven.aliyun.com/mvn/search下载，特别是cdc相关的jar
不能将kafka 表中的format配置为changelog-json,否则会因为flink1.12.0版本中，JsonRowDataSerializationSchema中的方法
JsonRowDataSerializationSchema(RowType rowType, TimestampFormat timestampFormat)被修改了,所以报：
java.lang.NoSuchMethodError: org.apache.flink.formats.json.JsonRowDataSerializationSchema.<init>(Lorg/apache/flink/table/types/logical/RowType;Lorg/apache/flink/formats/json/TimestampFormat;)V
方法不存在 

```shell script
 /data1/flink/flink/bin/flink run -d -p 2 
-C file:///data1/flink/flink-streaming-platform-web/jars/flink-connector-jdbc_2.11-1.12.0.jar 
-C file:///data1/flink/flink-streaming-platform-web/jars/flink-sql-connector-kafka_2.11-1.12.0.jar 
-c com.flink.streaming.core.JobApplication /data1/flink/flink-streaming-platform-web/lib/flink-streaming-core_flink_1.12.0-1.2.0.RELEASE.jar 
-sql /data1/flink/flink-streaming-platform-web/sql/job_sql_8.sql
```
