# 一：Spring的概述：

## 1.Spring是一个**IOC**(DI)和**AOP**容器框架

IOC:开发人员不需要知道容器是如何创建资源对象的，只需要提供接收资源的方式即可

DI:DI 是对IOC思想的具体实现，有两种实现，xml方式和注解的方式

## 2.Spring的优良特性

**依赖注入**：DI——Dependency Injection，反转控制(IOC)最经典的实现。

**面向切面编程**：Aspect Oriented Programming——AOP

**一站式**：在IOC和AOP的基础上可以整合各种企业应用的开源框架和优秀的第三方   类库（实际上Spring 自身也提供了表述层的SpringMVC和持久层的Spring JDBC）。

## 3.HelloWord实现

### 3.1.创建pom的maven项目

```
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>4.0.0.RELEASE</version>
    </dependency>
</dependencies>
```

### 3.2.在resources下面创建applicationContext.xml文件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id ="person" class="Person">
        <property name="name" value="zhangsan"></property>
    </bean>
</beans>
```

### 3.3.创建HelloSpring和Person类

HelloSpring类：

```
public class HelloSpring {

   public static void main(String[] args) {
      //1.创建IOC容器对象
      ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
      //2.从IOC容器中获取HelloWorld对象
      Person person = (Person) ioc.getBean("person");
      //3.调用HelloWorld中的sayHello方法
      person.play();
   }
}
```

Person类：

```
public class Person {
   private  String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void play(){
      System.out.println("study spring!!!");
   }
}
```

执行运行HelloSpring类即可！获取代码可加入首页的群，获取spring的例子代码！

## 4.创建datasource实现

4.1pom文件引入

```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.16</version>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.34</version>
</dependency>
```

4.2jdbc.properties

```
prop.userName=root
prop.password=root
prop.url=jdbc:mysql://192.168.1.204:3306/test
prop.driverClass=com.mysql.jdbc.Driver
```

4.3applicationContext.xml

```
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
    http://www.springframework.org/schema/context
    http://www.springframework.org/schema/context/spring-context-4.2.xsd">
    <bean id ="person" class="Person">
        <property name="name" value="zhangsan"></property>
    </bean>

    <!-- classpath:xxx 表示属性文件位于类路径下 -->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="username" value="root"/>
        <property name="password" value="${prop.password}"/>
        <property name="url" value="${prop.url}"/>
        <property name="driverClassName" value="${prop.driverClass}"/>
    </bean>
</beans>
```

4.测试类

```
@Test
public void test2() throws SQLException {
   //1.创建IOC容器对象
   ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
   DataSource dataSource = ioc.getBean(DataSource.class);
   Connection connection = dataSource.getConnection();
   System.out.println(connection);
}
```