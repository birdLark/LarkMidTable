​																                                 **<font size=5>部署文档</font>**



[TOC]



# 1.系统要求

 window系统和Linux需要安装Python环境，并配置环境变量

* java版本: Java 8

* 数据库版本: Mysql5.7

* 运行环境: MacOS, Windows,Linux,python

  

# 2.系统依赖包下载

## 2.1获取依赖包的方式

获取**flinkx.rar**压缩包，关注公众号【LarkMidTable】发送 【flinkx】

获取**datax.rar**压缩包，关注公众号【LarkMidTable】发送 【datax】

## 2.2压缩软件解压

linux部署:

上传解压目录flinkx到linux**【注意: 修改Linux目录权限 chown -R 用户:用户 777】**

上传解压目录datax到linux**【注意: 修改Linux目录权限 chown -R 用户:用户 777】**

window部署:

解压后存放在window本地

## 2.3上传解压后依赖包



# 3.web的安装

## 3.1 创建数据库

执行larkmidtable-web/shell 下面的larkmt_web.sql文件，添加到数据库(数据库版本5.1.0版本)

## 3.2编译打包

### 3.2.1 拉取项目代码

### 3.2.2Linux 项目打包

larkmidtable-web/build.sh

sh build.sh

### 3.2.3window 项目打包

window编译:larkmidtable-web/build.cmd

双击执行文件

### 3.3.4.生成编译后的jar包

执行成功后将会在工程的larkmidtable-web/packages下生成目录

```
packages/larkmt-admin_2.1.2_1.tar.gz
```

## 3.3解压安装包

### 3.3.1Linux解压

```
tar -zxvf admin_2.1.2_1.tar.gz 
```

###  3.3.2window解压

解压 admin_2.1.2_1.tar.gz 安装包，使用winRAR解压

## 3.4安装配置

### 3.4.1.修改数据库的配置

修改文件larkmidtable-web\larkmt-admin\src\main\resources\application.yml文件

```
server:
  port: 8888

spring:
  #数据源
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/web?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8
#    password: ${DB_PASSWORD:password}
#    username: ${DB_USERNAME:username}
#    url: jdbc:mysql://${DB_HOST:127.0.0.1}:${DB_PORT:3306}/${DB_DATABASE:flinkxweb}?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.jdbc.Driver


    hikari:
      ## 最小空闲连接数量
      minimum-idle: 5
      ## 空闲连接存活最大时间，默认600000（10分钟）
      idle-timeout: 180000
      ## 连接池最大连接数，默认是10
      maximum-pool-size: 10
      ## 数据库连接超时时间,默认30秒，即30000
      connection-timeout: 30000
      connection-test-query: SELECT 1
      ##此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟
      max-lifetime: 1800000
```

将localhost修改为数据库的地址，同时修改数据库的用户名 username 和密码 password，此时即可运行项目

# 4.启动程序

## 4.1Linux部署启动的方式:

```
[hadoop@mini4 bin]$ cd admin/bin
[hadoop@mini4 bin]$ sh admin.sh start
```

## 4.2window部署启动的方式:

```
运行\admin\bin下的admin.bat文件即可
```

# 5.验证是否启动成功

Linux部署验证启动是否成功:

```
[hadoop@mini4 bin]$ jps
24931 AdminApplication
```

window部署验证启动是否成功:

```
09:10:58.619 admin [main] INFO  c.l.a.AdminApplication - Access URLs:
----------------------------------------------------------

        Local-API:              http://127.0.0.1:8080/doc.html
        External-API:   http://192.168.1.22:8080/doc.html
        web-URL:                http://127.0.0.1:8080/index.html
        ----------------------------------------------------------

09:11:01.001 admin [web, admin JobScheduleHelper#scheduleThread] INFO  c.l.a.c.t.JobScheduleHelper - >>>>>>>>> init web admin scheduler success.
```



# 6.添加任务依赖

```
#flinkx-job, access token
larkmt:
  job:
    accessToken:
    #i18n (default empty as chinese, "en" as english)
    i18n:
    ## triggerpool max size
    triggerpool:
      fast:
        max: 200
      slow:
        max: 100
      ### log retention days
    logretentiondays: 30
  executor:
    flinkxHome: E:/flinkx/flinkx.py
    flinkxjsonPath: E:/flinkx/job/
    flinkxlogHome: E:/flinkx/job-log
    dataxHome: E:/datax/datax.py
    dataxjsonPath: E:/datax/job/
    dataxlogHome: E:/datax/job-log
```

## 6.1.添加Flinkx的配置

部署的配置**【获取flinkx-1.12-SNAPSHOT压缩包，关注公众号【LarkMidTable】发送 【flinkx】】**

```
flinkxHome: E:/flinkx/flinkx.py
flinkxjsonPath: E:/flinkx/job/
flinkxlogHome: E:/flinkx/job-log
```

配置中的E:/flinkx 为包的根目录

## 6.2.添加Datax的依赖

部署的配置**【获取flinkx-1.12-SNAPSHOT压缩包，关注公众号【LarkMidTable】发送 【flinkx】】**

```
dataxHome: E:/datax/datax.py
dataxjsonPath: E:/datax/job/
dataxlogHome: E:/datax/job-log
```

配置中的E:/datax为包的根目录

# 7.访问首页

 启动成功后打开页面（默认管理员用户名：admin 密码：123456） http://{ip}:8888/index.html#/dashboard

**![](https://img2022.cnblogs.com/blog/622382/202201/622382-20220124162212117-942279447.jpg)**


