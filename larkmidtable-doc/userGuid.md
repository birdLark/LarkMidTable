# linux部署文档

### 系统要求

* java版本: Java 8

* 数据库版本: Mysql5.7

* 运行环境: MacOS, Windows,Linux

  

## 1.Flinkx的安装

[Flinkx的安装](https://github.com/DTStack/flinkx/blob/master/docs/quickstart.md)

**flinkx-1.12-SNAPSHOT压缩包，关注公众号【LarkMidTable】发送 【flinkx】**  

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
build/web-{VERSION}.tar.gz
```

### 2.3解压部署

1.解压安装包

```
tar -zxvf web-2.1.2.tar.gz 
```

2.解压部署包

```
cd web-2.1.2/bin/
sh install.sh 
```

### 2.4安装配置

#### 修改项目配置

1.修改路径web-2.1.2/packages/admin/conf/ 下面的application.yml文件

```
#数据源
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/web?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.jdbc.Driver
```

将localhost修改为数据库的地址

2.修改web-2.1.2/packages/executor/conf 下面的application.yml文件

```
# web port
server:
#  port: ${server.port}
  port: 8081

# log config
logging:
  config: classpath:logback.xml
#  path: ${data.path}/applogs/executor/jobhandler
  path: /home/hadoop/data/flinkx-web-2.1.2/packages/flinkx-executor

flinkx:
  job:
    admin:
      ## flinkx admin address list, such as "http://address" or "http://address01,http://address02"
      addresses: http://localhost:8080
#      addresses: http://127.0.0.1:${flinkx.admin.port}
    executor:
      appname: flinkx-executor
      ip:
      port: 9999
#      port: ${executor.port:9999}
      ### job log path
      logpath: /home/hadoop/data/flinkx-web-2.1.2/packages/flinkx-executor/
#      logpath: ${data.path}/applogs/executor/jobhandler
      ### job log retention days
      logretentiondays: 30
    ### job, access token
    accessToken:

  executor:
    jsonpath: /home/hadoop/flinkx-1.8_release/job/
#    jsonpath: ${json.path}

  pypath: /home/hadoop/flinkx-1.8_release/start.sh
#  pypath: ${python.path}
  
```

**executor的日志的路径:**

 logpath: /home/hadoop/data/web-2.1.2/packages/executor

**flinkx的job目录的路径:**

jsonpath:  /home/hadoop/flinkx-1.8_release/job/

**flinkx的启动脚本的路径:**

 pypath:  /home/hadoop/flinkx-1.8_release/start.sh



**其中start.sh脚本和flinkx-1.8_release压缩包，可以添加群获得**

### 2.5启动程序

```
cd web-2.1.2/bin/
sh start-all.sh 
```

验证是否启动成功

```
[hadoop@mini4 bin]$ jps
24931 AdminApplication
26216 Jps
26185 ExecutorApplication
```

### 2.6访问首页

​    启动成功后打开页面（默认管理员用户名：admin 密码：123456） http://{ip}:8080/index.html#/dashboard

**![](https://img2022.cnblogs.com/blog/622382/202201/622382-20220118221928716-62704218.png)**





