# LarkMidTable

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

中文 | [English](README_EN.md)

Lark中文名称 云雀，云代表大数据，雀代表平凡和自由。

LarkMidTable 是一站式开源的数据中台，实现元数据管理，数据仓库开发，数据质量管理，数据的可视化，实现高效赋能数据前台并提供数据服务的产品。



[![Stargazers over time](https://starchart.cc/wxgzgl/larkMidTable.svg)](#)

# **产品愿景**

1.满足许多的小企业，提供一站式的解决方案。

2.做出世界级别，能够媲美BAT大厂的产品。

3.创造价值，产生价值，让世界变得更加美好。



# 产品效果图

![页面效果图](https://img2020.cnblogs.com/blog/622382/202010/622382-20201024132815743-808911491.png)



# 产品架构图

![系统架构图](https://img2020.cnblogs.com/blog/622382/202010/622382-20201019215540747-440767668.jpg ) 



# 产品功能

| **功能**     | **功能特性**   | **数据库类型** | **读取类型** | **写入类型** |
| ------------ | :------------- | -------------- | ------------ | ------------ |
| **数据集成** | 批处理同步     | Mysql          | 支持         | 支持         |
|              |                | PostgreSql     | 支持         | 支持         |
|              |                | Mongodb        | 支持         | 支持         |
|              |                | SqlServer      | 支持         | 支持         |
|              |                | Oracle         | 支持         | 支持         |
|              |                | SAPHana        | 支持         | 支持         |
|              |                | ClickHouse     | 支持         | 支持         |
|              |                | Hive           |              | 支持         |
|              |                | Hbase          | 支持         | 支持         |
|              |                | Hdfs           | 支持         | 支持         |
|              |                | ElasticSearch  |              |              |
|              |                | Kudu           |              |              |
|              |                | Redis          |              |              |
|              | 流处理同步     | Kafka          |              |              |
|              |                | MySQL Binlog   |              |              |
|              |                | MongoDB Oplog  |              |              |
|              |                | PostgreSQL WAL |              |              |
| **任务调度** | 任务逻辑调度   |                | 支持         | 支持         |
| **数据治理** | 元数据管理     |                |              |              |
|              | 数据质量管理   |                |              |              |
|              | 数据血缘管理   |                |              |              |
| **数据开发** | 可视化任务开发 |                |              |              |
| **数据服务** | 可视化API构建  |                |              |              |



# **快速开始**

安装手册      [安装手册](https://github.com/wxgzgl/flinkx-web/blob/master/userGuid.md)

操作手册  	[操作手册](https://github.com/wxgzgl/LarkMidTable/tree/master/docs/userManual.md)

插件开发手册      [插件开发手册](https://github.com/wxgzgl/LarkMidTable/tree/master/docs/PluginDev.md)

前端代码:  [前端代码](https://github.com/wxgzgl/LarkMidTableUI)


# LarkMidTable家族

[LarkMidTable](https://github.com/wxgzgl/LarkMidTable)

**基于flink的分布式实时处理引擎**

[LarkMidTableWebUI](https://github.com/wxgzgl/LarkMidTableWebUI)

**基于flink的分布式数据分析页面**



# 技术交流

**一个人走的很快，一群人走的更远。**

**扫描下面的QQ二维码加入Lark的数据中台开源社区，并为你提供全程免费服务，你也可以与其他伙伴交流大数据技术，如果觉得项目不错，可以star关注，LarkMidTable团队将十分感谢您的关注！**

QQ群1群【678097205】已满

**微信公众号 【LarkMidTable】**

关注官方微信公众号，回复加群，会发送社区微信群二维码，扫描二维码可加入群聊！


# 特别鸣谢

本项目在[datax-web](https://github.com/WeiYe-Jing/datax-web)代码的基础上进行二次重构开发，在此特别感谢datax-web团队。

