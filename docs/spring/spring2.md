# 1.通过注解配置bean

1) 普通组件：@Component

2) 持久化层组件：@Repository

3) 业务逻辑层组件：@Service

4) 表述层控制器组件：@Controller

## 2.代码实战

1.创建beans-annotation.xml 文件

```
<!---设置自动扫描的包-->
<context:component-scan base-package="com.larkmidtable.day02"></context:component-scan>
```

2.在需要添加注解的类上添加注解

```
@Component
public class User {
}
```

```
@Repository
public class UserDaoImpl implements UserDao{

   public void saveUser() {
      System.out.println("save user");
   }
}
```

3.进行测试

```
public class AnnotationTest {

   public static void main(String[] args) {
      ApplicationContext ioc = new ClassPathXmlApplicationContext("beans-annotation.xml");
      UserDao userDao = (UserDao)ioc.getBean("userDaoImpl");
      userDao.saveUser();
   }
}
```

## 3.代码获取

添加主页的群进行获取