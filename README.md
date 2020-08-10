## 产品规划

[flinkx执行引擎]( https://github.com/DTStack/flinkx )

[flinkx-web后端管理]( https://github.com/wxgzgl/flinkx-web )

[flinkx-web-ui后端管理界面]( https://github.com/wxgzgl/flinkx-web/tree/master/flinkx-web-ui-master )



# 产品计划

1.阅读flinkx的源码，将flinkx产品封装成更容易使用的shell脚本  

2.参考Datax-web的原型，快速的构建项目的WEB的客户端  

3.将Web页面和后台的应用对应起来  

4.针对用户提出的issue，解决实际出现的问题，完善产品  



# flinkx-web

FlinkX Web是在FlinkX之上开发的分布式数据同步工具，提供简单易用的 操作界面，降低用户使用FlinkX的学习成本，缩短任务配置时间，避免配置过程中出错。用户可通过页面选择数据源即可创建数据同步任务，RDBMS数据源可批量创建数据同步任务，支持实时查看数据同步进度及日志并提供终止同步功能，集成并二次开发xxl-job可根据时间、自增主键增量同步数据。  

任务"执行器"支持集群部署，支持执行器多节点路由策略选择，支持超时控制、失败重试、失败告警、任务依赖，执行器CPU.内存.负载的监控等等。后续还将提供更多的数据源支持、数据转换UDF、表结构同步、数据同步血缘等更为复杂的业务场景。



# 系统流程图

![](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809112010690-444044896.png)

# 系统要求

* java版本: Java 8（jdk版本建议1.8.201以上）
* 数据库版本: Mysql5.7
* 运行环境: MacOS, Windows,Linux

# 快速开始

#### 请点击：[快速开始](https://github.com/wxgzgl/flinkx-web/blob/master/userGuid.md)

#### 文章学习：[Flinkx学习]( https://github.com/wxgzgl/flinkx-web/blob/master/docs/list.md )

# 技术交流

搜索QQ群号[**678097205**]或者扫描下面的二维码进入Flinkx Web的QQ群，目前正是开源的初期阶段，暂时就我一个人。

![钉钉群](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809130031496-886275303.png)
