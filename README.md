# flinkx-web产品

FlinkX Web是在FlinkX之上开发的分布式数据同步工具，提供简单易用的 操作界面，降低用户使用FlinkX的学习成本，缩短任务配置时间，避免配置过程中出错。用户可通过页面选择数据源即可创建数据同步任务，RDBMS数据源可批量创建数据同步任务，支持实时查看数据同步进度及日志并提供终止同步功能，集成并二次开发xxl-job可根据时间、自增主键增量同步数据。  

任务"执行器"支持集群部署，支持执行器多节点路由策略选择，支持超时控制、失败重试、失败告警、任务依赖，执行器CPU.内存.负载的监控等等。后续还将提供更多的数据源支持、数据转换UDF、表结构同步、数据同步血缘等更为复杂的业务场景。



# flinkx-web产品规划

[flinkx执行引擎](  https://github.com/wxgzgl/flinkx  )

[flinkx-web后端管理]( https://github.com/wxgzgl/flinkx-web )

[flinkx-web-ui后端管理界面]( https://github.com/wxgzgl/flinkx-web/tree/master/flinkx-web-ui-master )



# flinkx-web产品流程图

![](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809112010690-444044896.png)





# 支持的数据库

|                        |  Database Type  |                      Reader                       |                     Writer                     |
| :--------------------: | :-------------: | :-----------------------------------------------: | :--------------------------------------------: |
| Batch Synchronization  |      MySQL      |     [doc](docs/offline/reader/mysqlreader.md)     |   [doc](docs/offline/writer/mysqlwriter.md)    |
|                        |     Oracle      |    [doc](docs/offline/reader/oraclereader.md)     |   [doc](docs/offline/writer/oraclewriter.md)   |
|                        |    SqlServer    |   [doc](docs/offline/reader/sqlserverreader.md)   | [doc](docs/offline/writer/sqlserverwriter.md)  |
|                        |   PostgreSQL    |  [doc](docs/offline/reader/postgresqlreader.md)   | [doc](docs/offline/writer/postgresqlwriter.md) |
|                        |       DB2       |      [doc](docs/offline/reader/db2reader.md)      |    [doc](docs/offline/writer/db2writer.md)     |
|                        |      GBase      |     [doc](docs/offline/reader/gbasereader.md)     |   [doc](docs/offline/writer/gbasewriter.md)    |
|                        |   ClickHouse    |  [doc](docs/offline/reader/clickhousereader.md)   | [doc](docs/offline/writer/clickhousewriter.md) |
|                        |     PolarDB     |    [doc](docs/offline/reader/polardbreader.md)    |  [doc](docs/offline/writer/polardbwriter.md)   |
|                        |    SAP Hana     |    [doc](docs/offline/reader/saphanareader.md)    |  [doc](docs/offline/writer/saphanawriter.md)   |
|                        |    Teradata     |   [doc](docs/offline/reader/teradatareader.md)    |  [doc](docs/offline/writer/teradatawriter.md)  |
|                        |     Phoenix     |    [doc](docs/offline/reader/phoenixreader.md)    |  [doc](docs/offline/writer/phoenixwriter.md)   |
|                        |      达梦       |      [doc](docs/offline/reader/dmreader.md)       |     [doc](docs/offline/writer/dmwriter.md)     |
|                        |    Greenplum    |   [doc](docs/offline/reader/greenplumreader.md)   | [doc](docs/offline/writer/greenplumwriter.md)  |
|                        |    Cassandra    |   [doc](docs/offline/reader/cassandrareader.md)   | [doc](docs/offline/writer/cassandrawriter.md)  |
|                        |      ODPS       |     [doc](docs/offline/reader/odpsreader.md)      |    [doc](docs/offline/writer/odpswriter.md)    |
|                        |      HBase      |     [doc](docs/offline/reader/hbasereader.md)     |   [doc](docs/offline/writer/hbasewriter.md)    |
|                        |     MongoDB     |    [doc](docs/offline/reader/mongodbreader.md)    |  [doc](docs/offline/writer/mongodbwriter.md)   |
|                        |      Kudu       |     [doc](docs/offline/reader/kudureader.md)      |    [doc](docs/offline/writer/kuduwriter.md)    |
|                        |  ElasticSearch  |      [doc](docs/offline/reader/esreader.md)       |     [doc](docs/offline/writer/eswriter.md)     |
|                        |       FTP       |      [doc](docs/offline/reader/ftpreader.md)      |    [doc](docs/offline/writer/ftpwriter.md)     |
|                        |      HDFS       |     [doc](docs/offline/reader/hdfsreader.md)      |    [doc](docs/offline/writer/hdfswriter.md)    |
|                        |   Carbondata    |  [doc](docs/offline/reader/carbondatareader.md)   | [doc](docs/offline/writer/carbondatawriter.md) |
|                        |     Stream      |    [doc](docs/offline/reader/streamreader.md)     | [doc](docs/offline/writer/carbondatawriter.md) |
|                        |      Redis      |                                                   |   [doc](docs/offline/writer/rediswriter.md)    |
|                        |      Hive       |                                                   |    [doc](docs/offline/writer/hivewriter.md)    |
| Stream Synchronization |      Kafka      |    [doc](docs/realTime/reader/kafkareader.md)     |   [doc](docs/realTime/writer/kafkawriter.md)   |
|                        |      EMQX       |     [doc](docs/realTime/reader/emqxreader.md)     |   [doc](docs/realTime/writer/emqxwriter.md)    |
|                        |     RestApi     |   [doc](docs/realTime/reader/restapireader.md)    |  [doc](docs/realTime/writer/restapiwriter.md)  |
|                        |  MySQL Binlog   |    [doc](docs/realTime/reader/binlogreader.md)    |                                                |
|                        |  MongoDB Oplog  | [doc](docs/realTime/reader/mongodboplogreader.md) |                                                |
|                        | PostgreSQL WAL  |    [doc](docs/realTime/reader/pgwalreader.md)     |                                                |
|                        | Oracle Logminer |                    Coming Soon                    |                                                |
|                        |  SqlServer CDC  |                    Coming Soon                    |                                                |

# flinkx-web产品计划

1. 8月11日完成flinkx-web的登录功能，输出2篇文章
2. 8月12日设计flinkx-web的生成JSON文件的页面，输出1篇文章
3. 8月13日实现flinkx-web的生成JSON文件的功能，输出1篇文章

4. 8月14日完善flinkx-web的部署文档，输出1篇文章
5. 8月15日-8月16日，完成flinkx-web的设计页面

# 快速开始

#### 请点击：[快速开始](https://github.com/wxgzgl/flinkx-web/blob/master/userGuid.md)

#### 文章学习：[Flinkx学习]( https://github.com/wxgzgl/flinkx-web/blob/master/docs/list.md )



# 技术交流

搜索QQ群号[**678097205**]或者扫描下面的二维码进入Flinkx Web的QQ群，目前正是开源的初期阶段，暂时就我一个人。

![钉钉群](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809130031496-886275303.png)
