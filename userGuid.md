# FlinkX Web用户手册

### 系统要求

* java版本: Java 8（jdk版本建议1.8.201以上）

* 数据库版本: Mysql5.7

* 运行环境: MacOS, Windows,Linux

  

## 1.Flinkx的安装

   * ### 1.下载源码包

     ![Flinkx下载](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809165608631-252785534.png)

     [Flinkx下载地址](https://github.com/DTStack/flinkx)

   * ### 2.解压和安装

     2.1 用解压软件进行解压即可

     2.2  window环境下双击解压后的flinkx-1.8_release\bin\install_jars.bat

     ​        linux/mac环境下执行 sh install_jars.sh

     本地私服中安装db2jcc，ojdbc8，gbase，dm7的jdbc驱动的JAR包

     

   * ### 3.编译插件

     使用cmd命令进入到 flinkx-1.8_release目录，使用maven命令进行编译
     
     ```
     mvn clean package -Dmaven.test.skip=true
     ```
     
     ![flinkx的编译结果](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809182513956-1929594896.png)
     
   * ### 4.配置文件

      4.1 flinkx-1.8_release\flinkconf目录下flink-conf.yaml里添加如下:
          
     ```
     rest.bind-port: 8888
     ```
     
     4.2 flinkx-1.8_release\下面创建job目录
     
     4.3 在 flinkx-1.8_release\job目录下面添加stream.json
     
     ```
     {
       "job" : {
         "content" : [ {
           "reader": {
             "name": "streamreader",
             "parameter": {
               "column": [
                 {
                   "name": "id",
                   "type": "int"
                 },
                 {
                   "name": "name",
                   "type": "string"
                 }
               ]
             }
           },
           "writer" : {
             "parameter" : {
               "print": false
             },
             "name" : "streamwriter"
           }
         } ],
         "setting" : {
           "restore" : {
             "isRestore" : false,
             "isStream" : false
           },
           "errorLimit" : {
           },
           "speed" : {
             "bytes" : 0,
             "channel" : 1,
             "rebalance" : true
           }
         }
       }
     }
     ```
     
     4.4将图中所选择的文件上传到Linux服务器
     
     ![上传的文件](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809192245416-1530610044.png)
     
     4.5编写启动脚本start.sh文件
     
     ```
     sh ./bin/flinkx
     
     -job './job/stream.json'
     
     -flinkconf './flinkconf/' 
     
     -pluginRoot './plugins/'
     ```
     
     ## 5.执行任务：
     
     ```
     [root@mini4 hadoop]# tar -zxvf flinkx-1.8_release.tar.gz 
     
     [root@mini4 flinkx-1.8_release]# sh start.sh 
     ```

[flinkx-1.8_release.tar.gz 下载]() 可以加QQ群获得

## 2.Flinkx-web的安装

[linux环境安装](https://github.com/wxgzgl/Lark/tree/master/docs/linuxInstall.md)

### 2.1 创建数据库

#### 执行bin/db下面的flinkx_web.sql文件(数据库版本5.1.0版本)

### 2.2 修改项目配置

### 1.修改flinkx_admin下resources/application.yml文件

```
#数据源
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/flinkx_web?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.jdbc.Driver
```

## 3.Flinkx-web的启动

### 1.本地idea开发环境

- 1.运行flinkx_admin下 DataXAdminApplication

## 4、启动成功

1.启动成功后打开页面（默认管理员用户名：admin 密码：123456） http://localhost:8080/index.html#/dashboard

![启动成功](https://img2020.cnblogs.com/blog/622382/202008/622382-20200813002251461-1896158188.png)







