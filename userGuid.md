# FlinkX Web用户手册

### 系统要求

* java版本: Java 8（jdk版本建议1.8.201以上）

* 数据库版本: Mysql5.7

* 运行环境: MacOS, Windows,Linux

  

## 1.Flinkx的安装

[Flinkx的安装](https://github.com/wxgzgl/LarkMidTable/tree/master/docs/InstallFlinkx.md)



## 2.Flinkx-web的Linux安装

### 2.1 创建数据库

执行bin/db下面的flinkx_web.sql文件(数据库版本5.1.0版本)

### 2.2 linux环境安装

[linux环境安装](https://github.com/wxgzgl/Lark/tree/master/docs/linuxInstall.md)



## 3 window环境安装

### 3.1 创建数据库

执行bin/db下面的flinkx_web.sql文件(数据库版本5.1.0版本) 

### 3.2 修改项目配置

1.修改flinkx_admin下resources/application.yml文件

```
#数据源
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/flinkx_web?serverTimezone=Asia/Shanghai&useLegacyDatetimeCode=false&useSSL=false&nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.jdbc.Driver
```

修改mysql的 username 和password 和localhost 

### 3.4 Flinkx-web的启动

​    1.运行flinkx_admin下 FlinkXAdminApplication

​    2.启动成功后打开页面（默认管理员用户名：admin 密码：123456） http://localhost:8080/index.html#/dashboard

![启动成功](https://img2020.cnblogs.com/blog/622382/202008/622382-20200813002251461-1896158188.png)









