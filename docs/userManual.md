# 用户操作手册

## 1.用户登陆

### 1.1浏览器输入

http://ip:8080/index.htm

![1603003764510](https://img-blog.csdnimg.cn/2020101814555823.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)



### 1.2默认用户名和密码

用户名: admin	密码: 123456

### 1.3系统首页

![https://img2020.cnblogs.com/blog/622382/202201/622382-20220113230020487-1484915727.png](https://img2020.cnblogs.com/blog/622382/202201/622382-20220113230020487-1484915727.png)

## 2.运行报表

展示成功多少次

失败多少次

正在运行多少次

![1602826473140](https://img-blog.csdnimg.cn/20201018145629121.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)



## 3.项目管理

搜索/添加/修改/删除项目

![1602826686577](https://img-blog.csdnimg.cn/20201018145651440.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)



## 4.执行器管理

执行器使用开源项目xxl-job

### 4.1添加执行器

- AppName：执行器的唯一标识，会周期性以AppName为对象进行自动注册；


- 名称：执行器的名称，提高执行器的可读性；
- 排序：选择执行器时的排列顺序；
- 注册方式：

  - 自动注册：通过底层自动注册获取执行器地址；
  - 手动录入：手动录入执行器地址，多地址逗号分割；
- 机器地址：执行器所在机器地址。

![img](https://img2020.cnblogs.com/blog/622382/202201/622382-20220113230146093-1604491020.png)

### 4.2编辑/删除执行器

可对创建好的执行器进行编辑、删除操作

![1602826775966](https://img-blog.csdnimg.cn/20201018145756893.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)

在线机器  ip:9999

## 5.数据源管理

### 5.1添加数据源

选择数据源，目前可选mysql, oracle, postgresql, sqlserver, hive, hbase, mongodb, clickhouse。

![1602912267682](https://img-blog.csdnimg.cn/2020101814581664.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)

填写数据源名称，数据源分组，所选数据源的用户名与密码，以及jdbc url，填写完成可进行测试连接。

![1602912579372](https://img-blog.csdnimg.cn/20201018145842881.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)

### 5.2编辑/删除数据源

可对配置已好的数据源进行编辑以及删除操作。

![1602826857929](https://img-blog.csdnimg.cn/20201018145902715.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)

## 6.任务管理

### 6.1创建Flinkx任务模板

![1602912849775](https://img-blog.csdnimg.cn/20201018145928644.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)

### 6.2任务构建

共分4步，步骤1与步骤2选择第5步创建的数据源，步骤3选择需要映射的字段，步骤4点击构建会自动生成flinkx所需的JSON脚本。

![1602913237693](https://img-blog.csdnimg.cn/20201018145947668.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)

### 6.3选择模板

![1602913488363](https://img-blog.csdnimg.cn/20201018150005288.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)

### 

点击下一步创建任务，在实例管理页面可以看到已构建的任务

## 7.实例管理

![1602826881720](https://img-blog.csdnimg.cn/20201018150026838.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzEyNDczMg==,size_16,color_FFFFFF,t_70#pic_center)

## 8.日志管理

![https://img2020.cnblogs.com/blog/622382/202201/622382-20220113230801526-1641993705.png](https://img2020.cnblogs.com/blog/622382/202201/622382-20220113230801526-1641993705.png)