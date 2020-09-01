# flinkx-web产品

FlinkX Web是在FlinkX之上开发的数据中台项目，提供简单易用的 操作界面，降低用户使用FlinkX的学习成本，缩短任务配置时间，避免配置过程中出错。用户可通过页面选择数据源即可创建数据同步任务，RDBMS数据源可批量创建数据同步任务，支持实时查看数据同步进度及日志并提供终止同步功能，集成并二次开发xxl-job可根据时间、自增主键增量同步数据。  

任务"执行器"支持集群部署，支持执行器多节点路由策略选择，支持超时控制、失败重试、失败告警、任务依赖，执行器CPU.内存.负载的监控等等。后续还将提供更多的数据源支持、数据转换UDF、表结构同步、数据同步血缘等更为复杂的业务场景。

# flinkx-web技术选型

| 框架用途           | 框架名称         | 主要功能                                                     | Github地址                                                   |
| ------------------ | ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 数据采集           | Flinkx           | 数据的导入导出ETL工具                                        | [flinkx](  https://github.com/wxgzgl/flinkx  )               |
| 消息中间件         | Kafka            | LinkedIn用Scala语言实现，支持hadoop数据并行加载              | [kafka](https://github.com/apache/kafka)                     |
| 数据存储           | Hive             | 基于MR的数据仓库工具                                         | [hive](https://github.com/apache/hive)                       |
| 离线和实时计算框架 | Flink            | 基于Flink，一站式解决批流处理问题                            | [flink](https://github.com/apache/flink)                     |
| 分析数据库         | Kylin            | 开源的、分布式的分析型数据仓库                               | [kylin](https://github.com/apache/kylin)                     |
| 任务调度           | DolphinScheduler | 可视化DAG工作流任务调度系统                                  | [dolphin](https://github.com/apache/incubator-dolphinscheduler) |
| 元数据管理         | Atlas            | 包括 数据分类、集中策略引擎、数据血缘、安全和生命周期管理在内的元数据治理核心能力 | [atlas](http://atlas.apache.org/)                            |
| 集群监控           | CM+CDH           | 制作的一个Hadoop发行版，集成了Hadoop及Hive等与Hadoop关系紧密的工具 | [cdh](https://www.cloudera.com/products/open-source/apache-hadoop/key-cdh-components.html) |

# flinkx-web产品流程图

![](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809112010690-444044896.png)





# flinkx-web产品愿景

非常愉快的和群里的小伙伴探讨产品的方向和远景，我们不比BAT大厂有业务支撑，而各自的中台又有各自的特点，我们希望能够做出一个数据中台出来，能够满足许多的小企业，提供一站式的解决方案，通过实践而不断的壮大自己。当别人提到数据中台就会想起flinkx-web，业界中开源最火的数据中台是flinkx-web，我们希望能够做出世界级别的产品，能够媲美BAT大厂的产品，我们希望因为有我们这群可爱的人，而更能够让世界变得更加美好。

数据中台:

数据源和技术元数据是数据中台的基石

数据采集，数据处理，任务调度，数据血缘，数据模型，业务元数据，数据质量，数据服务，数据可视化。

目前总结如下后续有待补充



# 快速开始

#### 请点击：[快速开始](https://github.com/wxgzgl/flinkx-web/blob/master/userGuid.md)

#### 文章学习：[Flinkx学习]( https://github.com/wxgzgl/flinkx-web/blob/master/docs/list.md )

#### 数据中台文章: [数据中台文章]()

# 技术交流

搜索QQ群号[**678097205**]或者扫描下面的二维码进入Flinkx Web社区的QQ群，目前正是开源的初期阶段，群里达到29人

![钉钉群](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809130031496-886275303.png)
