# 功能列表【已经实现】

| 模块名称 | 功能名称     | 功能描述                                                     |
| :------- | ------------ | ------------------------------------------------------------ |
| 数据报表 | 数据报表     | 实时展现数据可视化大屏                                       |
| 基础建设 | 数据源管理   | 配置全局数据源：mysql、oracle、hana、postgresql、sqlserver、hive、hbase、mongodb、es |
|          | 资源管理     | 管理资源地址并可访问：ES、Kafka、ZK、Flink、Spark、Storm、HDFS、HBASE... |
|          | 用户管理     | 用户的增删改查操作                                           |
| 数据集成 | 项目管理     | 项目的增删改查操作                                           |
|          | 任务模块     | 任务模块配置任务的定时调度                                   |
|          | 任务构建     | 创建任务并添加任务模板的调度                                 |
| 数据开发 | 开发环境配置 | 开发环境的增删改查操作，配置部署地址、ES、Kafka、ZK、Flink、Spark、Storm |
|          | 开发任务列表 | 展示经过JAR和SQL添加的任务                                   |
| 数据服务 | API接口列表  | 展示用户用户添加的API接口列表                                |
|          | 权限管理列表 | 展示用户的分组和对应的访问的Token信息                        |
|          | JSON格式化   | 可以将用户的JSON进行格式化的工具                             |

# 数据集成

| **数据集成工具** | **Features** | **The database type** | **Read type** | **Write type** |
| ---------------- | :----------- | --------------------- | ------------- | -------------- |
| **Flinkx**       | Batch        | Mysql                 | enable        | enable         |
|                  |              | Postgresql            | enable        | enable         |
| **Datax**        | Batch        |                       |               |                |
|                  |              |                       |               |                |
| **Seatunnel**    | Batch        |                       |               |                |
|                  |              |                       |               |                |

