# 1.编译打包

1.直接从Git上面获得源代码，在项目的根目录下执行如下命令

```
mvn clean install 
```

2.执行成功后将会在工程的build目录下生成安装包

```
build/flinkx-web-{VERSION}.tar.gz
```

# 2.解压部署

1.解压安装包

```
tar -zxvf flinkx-web-2.1.2.tar.gz 
```

2.解压部署包

```
cd flinkx-web-2.1.2/bin/
sh install.sh 
```



## 3.安装配置

### 3.1创建数据库

#### 执行bin/db下面的flinkx_web.sql文件(数据库版本5.1.0版本)

### 3.2修改项目配置

### 1.修改flinkx_admin下conf/application.yml文件

```
#数据源
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/flinkx_web?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.jdbc.Driver
```

将localhost修改为数据库的地址

### 2.修改flinkx-executor下conf/application.yml文件

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

**flinkx-executor的日志的路径:**

 /home/hadoop/data/flinkx-web-2.1.2/packages/flinkx-executor

**flinkx的job目录的路径:**

jsonpath: /home/hadoop/flinkx-1.8_release/job/

**flinkx的启动脚本的路径:**

 pypath: /home/hadoop/flinkx-1.8_release/start.sh





**其中start.sh脚本和flinkx-1.8_release压缩包，可以添加群获得**

# 4.启动程序

```
cd flinkx-web-2.1.2/bin/
sh start-all.sh 
```



# 5.访问首页

http://host:8080/index.html