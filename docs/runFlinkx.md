# Flinkx如何在idea中运行？

## 1.下载zip包

[Flinkx链接](https://github.com/DTStack/flinkx)![Flinkx](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809165608631-252785534.png)

## 2.解压jar包

普通的解压软件解压即可

## 3.解压的bin目录

 window环境下双击解压后的flinkx-1.8_release\bin\install_jars.bat

 linux/mac环境下执行 sh install_jars.sh

## 4.编译插件

使用cmd命令进入到 flinkx-1.8_release目录，使用maven命令进行编译

```
mvn clean package -Dmaven.test.skip=true
```

![flinkx的编译结果](https://img2020.cnblogs.com/blog/622382/202008/622382-20200809182513956-1929594896.png)

## 5.使用idea工具导入项目工程

![如图所示](https://img2020.cnblogs.com/blog/622382/202008/622382-20200815183506309-70824110.png)

## 6.创建job目录，并配置launcher

![创建job并配置launcer](https://img2020.cnblogs.com/blog/622382/202008/622382-20200815183811451-27087232.png)

### 6.1 Program arguments配置如下:

-job "E:\tmplate\idea\flinkx-1.8_release\job\stream.json" -flinkconf "E:\tmplate\idea\flinkx-1.8_release\flinkconf" -pluginRoot "E:\tmplate\idea\flinkx-1.8_release\plugins"

### 6.2 如下配置即可stream.json

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

## 7.运行程序

执行flinkx-launcher模块中的Launcher类即可运行