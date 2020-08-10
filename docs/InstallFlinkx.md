## Flinkx的安装

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
