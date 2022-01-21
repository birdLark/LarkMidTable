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
    flinkxHome: /home/hadoop/flinkx/start.sh
    jsonPath: /home/hadoop/flinkx/job/
    logHome: /home/hadoop/flinkx/nohup.out
```

1.将localhost修改为数据库的地址

2.将flinkxHome，jsonPath，logHome 配置的/home/hadoop/flinkx 换成flinkx所在目录



### 2.5启动程序

```
[hadoop@mini4 bin]$ cd web-2.1.2/bin/
[hadoop@mini4 bin]$ sh admin.sh start
```

验证是否启动成功

```
[hadoop@mini4 bin]$ jps
24931 AdminApplication
```

### 2.6访问首页

​    启动成功后打开页面（默认管理员用户名：admin 密码：123456） http://{ip}:8080/index.html#/dashboard

**![](https://img2022.cnblogs.com/blog/622382/202201/622382-20220118221928716-62704218.png)**





