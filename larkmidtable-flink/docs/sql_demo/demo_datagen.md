# 1. datagen简介
在flink 1.11中，内置提供了一个DataGen 连接器，主要是用于生成一些随机数，用于在没有数据源的时候，进行流任务的测试以及性能测试等。
- DataGen 连接器允许按数据生成规则进行读取。
- DataGen 连接器可以使用计算列语法。 这使您可以灵活地生成记录。
- DataGen 连接器是内置的。

[具体的使用方法可以先看下官网的概述](https://ci.apache.org/projects/flink/flink-docs-release-1.11/zh/dev/table/connectors/datagen.html)

使用时注意如下:
- 目前随机生成只支持基本数据类型：数字类型（TINYINT、SMALLINT、int、bigint、FLOAT、double）、字符串类型（VARCHAR、char），
  以及boolean类型,不支持复杂类型: Array，Map，Row。 请用计算列构造这些类型
- 目前有两种数据生成器，一种是随机生成器（默认),这个是无界的，另一个是序列生成器，是有界的。
- 字段中只要有一个是按序列生成的，也就是有界的，程序就会在序列结束的时候退出。如果所有字段都是随机生成的，则程序最终不会结束。
- 计算列是一个使用 “column_name AS computed_column_expression” 语法生成的虚拟列。
  它由使用同一表中其他列的非查询表达式生成，并且不会在表中进行物理存储。例如，一个计算列可以使用 cost AS price * quantity 进行定义，
  这个表达式可以包含物理列、常量、函数或变量的任意组合，但这个表达式不能存在任何子查询。
- 在 Flink 中计算列一般用于为 CREATE TABLE 语句定义 时间属性。 处理时间属性 可以简单地通过使用了系统函数 PROCTIME() 的 proc AS PROCTIME() 语句进行定义

#2. 使用示例
## 2.1. 在flink sql-client中使用
### 进入客户端
```shell script
bin/sql-client.sh embedded
或
bin/sql-client.sh embedded -l 依赖的jar包路径
```
### flink SQL测试
```sql
CREATE TABLE datagen (
 f_sequence INT,
 f_random INT,
 f_random_str STRING,
 ts AS localtimestamp,
 WATERMARK FOR ts AS ts
) WITH (
 'connector' = 'datagen',
 -- optional options --
 'rows-per-second'='5',
 'fields.f_sequence.kind'='sequence',
 'fields.f_sequence.start'='1',
 'fields.f_sequence.end'='1000',
 'fields.f_random.min'='1',
 'fields.f_random.max'='1000',
 'fields.f_random_str.length'='10'
);

select * from datagen;
```
## 2.2 参数解释
DDL的with属性中，除了connector是必填之外，其他都是可选的。
rows-per-second 每秒生成的数据条数
f_sequence字段的生成策略是按序列生成，并且指定了起始值，所以该程序将会在到达序列的结束值之后退出
f_random 字段是按照随机生成，并指定随机生成的范围
f_random_str是一个字符串类型，属性中指定了随机生成字符串的长度是10
ts列是一个计算列，返回当前的时间.
