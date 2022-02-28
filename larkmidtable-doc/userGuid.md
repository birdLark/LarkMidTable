# linux部署文档

### 系统要求

 window系统和Linux需要安装Python环境，并配置环境变量

* java版本: Java 8

* 数据库版本: Mysql5.7

* 运行环境: MacOS, Windows,Linux,python

  

## 1.系统依赖包下载

1.获取flinkx-1.12-SNAPSHOT压缩包，关注公众号【LarkMidTable】发送 【flinkx】

2.将flinkx用压缩软件解压后

linux部署:

上传解压目录flinkx到linux**【注意: 修改Linux目录权限 chown -R 用户:用户 777】**

window部署:

解压后存放在window本地

## 2.web的安装

### 2.1 创建数据库

执行bin/db下面的web.sql文件(数据库版本5.1.0版本)

### 2.2编译打包

1.直接从Git上面获得源代码，在项目的根目录下执行如下命令

```
mvn clean install 
```

2.执行成功后将会在工程的build目录下生成安装包

```
packages/admin_{VERSION}.tar.gz
```

### 2.3解压安装包

#### Linux方式部署的解压方式

解压安装包

```
tar -zxvf admin_2.1.2_1.tar.gz 
```

####  window方式部署解压方式

解压 admin_2.1.2_1.tar.gz 安装包，使用winRAR解压

### 2.4安装配置

#### 修改项目配置

1.修改路径admin/conf/ 下面的application.yml文件

```
#数据源
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/web?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.jdbc.Driver
  ....
  ....
  ....
flinkx:
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
    flinkxHome: /home/hadoop/flinkx/flinkx.py
    jsonPath: /home/hadoop/flinkx/job/
    logHome: /home/hadoop/flinkx/nohup.out
```

1.将localhost修改为数据库的地址

2.将flinkxHome，jsonPath，logHome 配置成flinkx所在目录

例如: Linux部署的配置

```
  executor:
    flinkxHome: /home/hadoop/flinkx/flinkx.py
    jsonPath: /home/hadoop/flinkx/job/
    logHome: /home/hadoop/flinkx/nohup.out
```

例如:window部署的配置

```
executor:
  flinkxHome: E:/flinkx/flinkx.py
  jsonPath: E:/flinkx/job/
  logHome: E:/flinkx/nohup.out
```

### 2.5启动程序

Linux部署启动的方式:

```
[hadoop@mini4 bin]$ cd admin/bin
[hadoop@mini4 bin]$ sh admin.sh start
```

window部署启动的方式:

```
运行\admin\bin下的admin.bat文件即可
```

## 2.6验证是否启动成功

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



### 2.7访问首页

​    启动成功后打开页面（默认管理员用户名：admin 密码：123456） http://{ip}:8080/index.html#/dashboard

**![](https://img2022.cnblogs.com/blog/622382/202201/622382-20220124162212117-942279447.jpg)**


