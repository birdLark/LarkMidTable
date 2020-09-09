# Lark产品

Lark中文名称 云雀，云代表大数据，雀代表平凡和自由。

Lark 是一站式开源的数据中台，实现元数据管理，数据仓库开发，数据质量管理，数据的可视化，实现高效赋能数据前台并提供数据服务的产品。



# Lark技术选型

| 框架用途           | 框架名称         | 主要功能                                                     | Github地址                                                   |
| ------------------ | ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 消息中间件         | Kafka            | LinkedIn用Scala语言实现，支持hadoop数据并行加载              | [Kafka](https://github.com/apache/kafka)                     |
| 数据存储           | Hive             | 基于MR的数据仓库工具                                         | [Hive](https://github.com/apache/hive)                       |
| 离线和实时计算框架 | Flink            | 基于Flink，一站式解决批流处理问题                            | [Flink](https://github.com/apache/flink)                     |
| 分析数据库         | Kylin            | 开源的、分布式的分析型数据仓库                               | [Kylin](https://github.com/apache/kylin)                     |
| 任务调度           | DolphinScheduler | 可视化DAG工作流任务调度系统                                  | [Dolphin](https://github.com/apache/incubator-dolphinscheduler) |
| 元数据管理         | Atlas            | 包括 数据分类、集中策略引擎、数据血缘、安全和生命周期管理在内的元数据治理核心能力 | [Atlas](http://atlas.apache.org/)                            |
| 集群监控           | CM+CDH           | 制作的一个Hadoop发行版，集成了Hadoop及Hive等与Hadoop关系紧密的工具 | [CDH](https://www.cloudera.com/products/open-source/apache-hadoop/key-cdh-components.html) |
| 容器               | Kubernetes       | 部署容器化的应用简单并且高效                                 | [K8S](https://github.com/kubernetes/kubernetes)              |



# Lark产品架构图

| **适用行业** | 电商领域 | **金融领域** | **通信领域** | **工业领域** | **...** |
| ------------ | -------- | ------------ | ------------ | ------------ | ------- |
| **数据中台** | 数据仓库 | 数据质量     | 元数据管理   | 实时报表     | ...     |
| **数据平台** | 离线计算 | 实时计算     | 业务算法     | 人工智能     | ...     |
| **数据采集** | 埋点日志 | 告警日志     | 物联网数据   | 点击流数据   | ...     |



# **Lark产品愿景**

非常愉快的和群里的小伙伴探讨产品的方向和远景，我们不比BAT大厂有业务支撑，而各自的中台又有各自的特点，我们希望能够做出一个数据中台出来，能够满足许多的小企业，提供一站式的解决方案，通过实践而不断的壮大自己。当别人提到数据中台就会想起Lark MidTable，业界中开源最火的数据中台是Lark MidTable，我们希望能够做出世界级别的产品，能够媲美BAT大厂的产品，我们希望因为有我们这群可爱的人，而更能够让世界变得更加美好。



# **快速开始**

请点击[快速开始](https://github.com/wxgzgl/flinkx-web/blob/master/userGuid.md)



# **Lark开发计划**

9-10月份，我们把这些大数据组件，包装成可配置的一些工具，一键打包，一键配置，部署简单，傻瓜式操作。

11-12月份，我们可以将这些页面和后端配置，弄成web页面，然后我们具体划分，什么是数据血缘，什么是元数据…这样后续我们，比如配置flink任务，只需要选择一下，生成任务，然后配置一下任务调度。



# 学习资源库

文章学习[Flinkx学习]( https://github.com/wxgzgl/flinkx-web/blob/master/docs/list.md )

数据中台 [数据中台文章](https://github.com/wxgzgl/flinkx-web/tree/master/docs/midtable/midtable.md)



# **技术交流**

搜索QQ群号[678097205]或者扫描下面的二维码进入Flinkx Web社区的QQ群，目前正是开源的初期阶段，群里达到59人

**![QQ群](https://img2020.cnblogs.com/blog/622382/202009/622382-20200907124358049-997953244.png)**
