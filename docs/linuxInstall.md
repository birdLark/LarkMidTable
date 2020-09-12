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

2.解压后进入flinkx-web-2.1.2/packages目录

```
 tar -zxvf flinkx-admin_2.1.2_1.tar.gz 
```

3.进入flinkx-web-2.1.2/packages/flinkx-admin/bin

```
sh flinkx-admin.sh start
```

4.访问首页

http://host:port/index.html