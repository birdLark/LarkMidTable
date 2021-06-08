# LarkMidTable插件开发宝典

本文面向LarkMidTable插件开发人员，尝试尽可能全面地阐述开发一个LarkMidTable插件所经过的历程，力求消除开发者的困惑，让插件开发变得简单。

## 一、开发之前

>  路走对了，就不怕远。✓
>  路走远了，就不管对不对。✕

当你打开这篇文档，想必已经不用在此解释什么是`LarkMidTable`了。那下一个问题便是：

###  `LarkMidTable`为什么要使用插件机制？

从设计之初，`LarkMidTable`就把异构数据源同步作为自身的使命，为了应对不同数据源的差异、同时提供一致的同步原语和扩展能力，`LarkMidTable`自然而然地采用了`框架` + `插件` 的模式：

- 插件只需关心数据的读取或者写入本身。
- 而同步的共性问题，比如：类型转换、性能、统计，则交由框架来处理。

作为插件开发人员，则需要关注两个问题：

1. 数据源本身的读写数据正确性。
2. 如何与框架沟通、合理正确地使用框架。

###  开工前需要想明白的问题

就插件本身而言，希望在您动手coding之前，能够回答我们列举的这些问题，不然路走远了发现没走对，就尴尬了。

## 二、插件视角看框架

### 逻辑执行模型

插件开发者不用关心太多，基本只需要关注特定系统读和写，以及自己的代码在逻辑上是怎样被执行的，哪一个方法是在什么时候被调用的。在此之前，需要明确以下概念：

- `Job`: `Job`是LarkMidTable用以描述从一个源头到一个目的端的同步作业，是LarkMidTable数据同步的最小业务单元。比如：从一张mysql的表同步到odps的一个表的特定分区。
- `Task`: `Task`是为最大化而把`Job`拆分得到的最小执行单元。比如：读一张有1024个分表的mysql分库分表的`Job`，拆分成1024个读`Task`，用若干个并发执行。
- `TaskGroup`:  描述的是一组`Task`集合。在同一个`TaskGroupContainer`执行下的`Task`集合称之为`TaskGroup`
- `JobContainer`:  `Job`执行器，负责`Job`全局拆分、调度、前置语句和后置语句等工作的工作单元。类似Yarn中的JobTracker
- `TaskGroupContainer`: `TaskGroup`执行器，负责执行一组`Task`的工作单元，类似Yarn中的TaskTracker。

简而言之， **`Job`拆分成`Task`，在分别在框架提供的容器中执行，插件只需要实现`Job`和`Task`两部分逻辑**。

### 物理执行模型



### 编程接口

那么，`Job`和`Task`的逻辑应是怎么对应到具体的代码中的？

首先，插件的入口类必须扩展`Reader`或`Writer`抽象类，并且实现分别实现`Job`和`Task`两个内部抽象类，`Job`和`Task`的实现必须是 **内部类** 的形式，原因见 **加载原理** 一节。以Reader为例：

```java
public class SomeReader extends Reader {
    public static class Job extends Reader.Job {

        @Override
        public void init() {
        }
		
	@Override
	public void prepare() {
        }

        @Override
        public List<Configuration> split(int adviceNumber) {
            return null;
        }

        @Override
        public void post() {
        }

        @Override
        public void destroy() {
        }

    }

    public static class Task extends Reader.Task {

        @Override
        public void init() {
        }
		
	@Override
	public void prepare() {
        }

        @Override
        public void startRead(RecordSender recordSender) {
        }

        @Override
        public void post() {
        }

        @Override
        public void destroy() {
        }
    }
}
```

`Job`接口功能如下：
- `init`: Job对象初始化工作，此时可以通过`super.getPluginJobConf()`获取与本插件相关的配置。读插件获得配置中`reader`部分，写插件获得`writer`部分。
- `prepare`: 全局准备工作，比如odpswriter清空目标表。
- `split`: 拆分`Task`。参数`adviceNumber`框架建议的拆分数，一般是运行时所配置的并发度。值返回的是`Task`的配置列表。
- `post`: 全局的后置工作，比如mysqlwriter同步完影子表后的rename操作。
- `destroy`: Job对象自身的销毁工作。

`Task`接口功能如下：
- `init`：Task对象的初始化。此时可以通过`super.getPluginJobConf()`获取与本`Task`相关的配置。这里的配置是`Job`的`split`方法返回的配置列表中的其中一个。
- `prepare`：局部的准备工作。
- `startRead`: 从数据源读数据，写入到`RecordSender`中。`RecordSender`会把数据写入连接Reader和Writer的缓存队列。
- `startWrite`：从`RecordReceiver`中读取数据，写入目标数据源。`RecordReceiver`中的数据来自Reader和Writer之间的缓存队列。
- `post`: 局部的后置工作。
- `destroy`: Task象自身的销毁工作。

需要注意的是：
- `Job`和`Task`之间一定不能有共享变量，因为分布式运行时不能保证共享变量会被正确初始化。两者之间只能通过配置文件进行依赖。
- `prepare`和`post`在`Job`和`Task`中都存在，插件需要根据实际情况确定在什么地方执行操作。


### 插件定义

### 打包发布


### 配置文件


#### 如何设计配置参数

> 配置文件的设计是插件开发的第一步！

任务配置中`reader`和`writer`下`parameter`部分是插件的配置参数，插件的配置参数应当遵循以下原则：

- 驼峰命名：所有配置项采用驼峰命名法，首字母小写，单词首字母大写。
- 正交原则：配置项必须正交，功能没有重复，没有潜规则。
- 富类型：合理使用json的类型，减少无谓的处理逻辑，减少出错的可能。
    - 使用正确的数据类型。比如，bool类型的值使用`true`/`false`，而非`"yes"`/`"true"`/`0`等。
    - 合理使用集合类型，比如，用数组替代有分隔符的字符串。
- 类似通用：遵守同一类型的插件的习惯，比如关系型数据库的`connection`参数都是如下结构：

    ```json
    {
      "connection": [
        {
          "table": [
            "table_1",
            "table_2"
          ],
          "jdbcUrl": [
            "jdbc:mysql://127.0.0.1:3306/database_1",
            "jdbc:mysql://127.0.0.2:3306/database_1_slave"
          ]
        },
        {
          "table": [
            "table_3",
            "table_4"
          ],
          "jdbcUrl": [
            "jdbc:mysql://127.0.0.3:3306/database_2",
            "jdbc:mysql://127.0.0.4:3306/database_2_slave"
          ]
        }
      ]
    }
    ``` 
- ...

#### 如何使用`Configuration`类

为了简化对json的操作，`LarkMidTable`提供了简单的DSL配合`Configuration`类使用。

`Configuration`提供了常见的`get`, `带类型get`，`带默认值get`，`set`等读写配置项的操作，以及`clone`, `toJSON`等方法。配置项读写操作都需要传入一个`path`做为参数，这个`path`就是`LarkMidTable`定义的DSL。语法有两条：

1. 子map用`.key`表示，`path`的第一个点省略。
2. 数组元素用`[index]`表示。

比如操作如下json：

```json
{
  "a": {
    "b": {
      "c": 2
    },
    "f": [
      1,
      2,
      {
        "g": true,
        "h": false
      },
      4
    ]
  },
  "x": 4
}
```

比如调用`configuration.get(path)`方法，当path为如下值的时候得到的结果为：

- `x`：`4`
- `a.b.c`：`2`
- `a.b.c.d`：`null`
- `a.b.f[0]`：`1`
- `a.b.f[2].g`：`true`

注意，因为插件看到的配置只是整个配置的一部分。使用`Configuration`对象时，需要注意当前的根路径是什么。

更多`Configuration`的操作请参考`ConfigurationTest.java`。

### 插件数据传输


### 类型转换

为了规范源端和目的端类型转换操作，保证数据不失真，LarkMidTable支持六种内部数据类型：

- `Long`：定点数(Int、Short、Long、BigInteger等)。
- `Double`：浮点数(Float、Double、BigDecimal(无限精度)等)。
- `String`：字符串类型，底层不限长，使用通用字符集(Unicode)。
- `Date`：日期类型。
- `Bool`：布尔值。
- `Bytes`：二进制，可以存放诸如MP3等非结构化数据。


对应地，有`DateColumn`、`LongColumn`、`DoubleColumn`、`BytesColumn`、`StringColumn`和`BoolColumn`六种`Column`的实现。

### 加载原理


1. 框架扫描`plugin/reader`和`plugin/writer`目录，加载每个插件的`plugin.json`文件。
2. 以`plugin.json`文件中`name`为key，索引所有的插件配置。如果发现重名的插件，框架会异常退出。
3. 用户在插件中在`reader`/`writer`配置的`name`字段指定插件名字。框架根据插件的类型（`reader`/`writer`）和插件名称去插件的路径下扫描所有的jar，加入`classpath`。
4. 根据插件配置中定义的入口类，框架通过反射实例化对应的`Job`和`Task`对象。


## 三、Last but not Least

> 文档是工程师的良知。

每个插件都必须在`LarkMidTable`官方wiki中有一篇文档，文档需要包括但不限于以下内容：

1. **快速介绍**：介绍插件的使用场景，特点等。
2. **实现原理**：介绍插件实现的底层原理，比如`mysqlwriter`通过`insert into`和`replace into`来实现插入，`tair`插件通过tair客户端实现写入。
3. **配置说明**
	- 给出典型场景下的同步任务的json配置文件。
	- 介绍每个参数的含义、是否必选、默认值、取值范围和其他约束。
4. **类型转换**
    - 插件是如何在实际的存储类型和`LarkMidTable`的内部类型之间进行转换的。
    - 以及是否存在特殊处理。
5. **性能报告**
	6. **约束限制**：是否存在其他的使用限制条件。
7. **FAQ**：用户经常会遇到的问题。
