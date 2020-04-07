# springboot学习



## 准备

### 开发环境

1. eclipse+sts 

   1. 在线安装sts插件help->marketplace
   2. 官网下载压缩包help->install new software

2. spring-tool-suite

   官网下载最新sts4，jar包，双击后解压直接可以使用集成ide

3. idea

   社区版不支持web，收费，破解版不好下载

4. jdk 配置系统环境变量

5. maven

   1. 官网下载，配置系统环境变量

   2. 修改settings.xml

   3. 国内仓库，试了很多个，华为的可用

   4. project名右键maven，update project…

      ``` xml
      <mirror>
          <id>huaweicloud</id>
          <mirrorOf>*</mirrorOf>
          <url>https://mirrors.huaweicloud.com/repository/maven/</url>
      </mirror>
      <!--后面两个好像没用，自测maven install有效
      <mirror>
          <id>mirrorId</id>
          <mirrorOf>central</mirrorOf>
          <name>Human Readable Name for this Mirror.</name>
          <url>http://central.maven.org/maven2/</url>
      </mirror>
      
      <mirror>
          <id>nexus-aliyun</id>
          <mirrorOf>*</mirrorOf>
          <name>Nexus aliyun</name><url>http://maven.aliyun.com/nexus/content/groups/public</url>
      </mirror>
      -->
      ```

6. VMware虚拟工作站安装

7. Ubuntu虚拟主机安装

   1. ssh 安装server版本Ubuntu过程中可安装

   2. smb 安装server版本Ubuntu过程中可安装

   3. mysql 执行命令安装

      ``` shell
      sudo apt-get install mysql-server mysql-client
      ```

8. mysql-gui-tools MySQL可视化工具

9. MobaXterm 远程终端工具

### 数据环境

1. mysql 开启远程访问

   ``` shell
   #vim /etc/mysql/mysql.conf.d/mysqld.cnf
   ```

   ``` shell
   #注释掉绑定lo
   #bind-address           = 127.0.0.1
   ```

   ``` mysql
   mysql -u root -p
   use mysql；
   GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'passwd'；
   flush privileges;
   ```

2. 通过gui-tool或者mysql命令行创建schema

   ``` mysql
   create database spring;
   show databases;
   ```

3. 创建table

   ``` mysql
   use spring;
   CREATE TABLE tb_user (
       id int(32) PRIMARY KEY AUTO_INCREMENT,
       username varchar(32),
       address varchar(256)
   );
   show tables;
   ```

4. 插入测试数据

   ``` mysql
   INSERT INTO tb_user VALUES ('1', 'a', 'aaa');
   INSERT INTO tb_user VALUES ('2', 'b', 'bbb');
   INSERT INTO tb_user VALUES ('3', 'c', 'ccc');
   select * from tb_user;
   ```

基本环境和初步测试数据已准备完毕。

------



## 开始

### 创建springboot工程

1. sts插件创建springboot starter project 直接创建项目 或者使用官网

   [spring initializr]: https://start.spring.io/

2. 检查项目根目录pom.xml文件中是否有依赖问题，注意springboot-starter-parent是否通过maven下载完成

3. 生成项目时可选依赖

   1. spring-web

      > spring MVC框架

      

   2. mybatis

      > 流行的数据库持久化框架，相对于hibernate更灵活，动态SQL

      

   3. lombok

      > 一款java工具，消除java的冗长代码
      >
      > @Data
      >
      > @Getter/@Setter
      >
      > @Builder
      >
      > @NonNull
      >
      > 缺点：**不建议使用**，会破坏代码封装性，jdk升级带来的兼容性问题，团队开发中一个人使用则所有人都得用，一定需要ide，EqualsAndHashCode默认不对比继承父类的属性

      

   4. junit

      > UT 单元测试
      >
      > @Test
      >
      > 1.测试方法上必须使用@Test
      >
      > 2.测试方法必须使用 public void进行修饰
      >
      > 3.新建一个源代码目录来存放测试代码
      >
      > 4.测试类的包应该和被测试类的包一样
      >
      > 5.测试单元中的每个方法一定要能够独立测试，其方法不能有任何依赖

      

   5. druid

      > alibaba数据库连接池
      
      

4. 依赖pom

   ``` xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
       <dependency>
           <groupId>org.mybatis.spring.boot</groupId>
           <artifactId>mybatis-spring-boot-starter</artifactId>
           <version>2.1.2</version>
       </dependency>
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
       </dependency>
       <dependency>
           <groupId>com.alibaba</groupId>
           <artifactId>druid</artifactId>
           <version>1.1.0</version>
       </dependency>
       <!--
       <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
           <optional>true</optional>
       </dependency>
   	-->
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-test</artifactId>
           <scope>test</scope>
           <exclusions>
               <exclusion>
                   <groupId>org.junit.vintage</groupId>
                   <artifactId>junit-vintage-engine</artifactId>
               </exclusion>
           </exclusions>
       </dependency>
   </dependencies>
   <!--maven编译配置-->
   <build>
       <plugins>
           <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
           </plugin>
           <plugin>
               <groupId>org.apache.maven.plugins</groupId>
               <artifactId>maven-compiler-plugin</artifactId>
           </plugin>
       </plugins>
   </build>
   ```
   
5. springboot启动类

   ``` java
   @ComponentScan(basePackages="com.demo.springboot")
   @SpringBootApplication
   public class SpringTestApplication {
   	public static void main(String[] args) {
   		SpringApplication.run(SpringTestApplication.class, args);
   	}
   }
   ```

   

### 集成mybatis

1. application.properties，当前使用yml方式较多

   ``` properties
   #配置mysql数据源
   spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
   spring.datasource.url=jdbc:mysql://192.168.71.10:3306/spring?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
   spring.datasource.username=root
   spring.datasource.password=root
   spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
   
   mybatis.type-aliases-package=com.demo.springboot.entity
   mybatis.mapper-locations=classpath:resources/mapping/*.xml
   ```

   

2. application.yml

   ``` yaml
   #配置mysql数据源
   spring: 
     datasource:
     	#比默认连接池需要多配置一个type
       type: com.alibaba.druid.pool.DruidDataSource
       url: jdbc:mysql://192.168.71.10:3306/spring?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
       username: root
       password: root
       driverClassName: com.mysql.cj.jdbc.Driver
     
   mybatis: 
     type-aliases-package: com.demo.springboot.entity
     #采用注解方式可以不配置mapper-locations，自测有效
     #mapper-locations: classpath:resources/mapping/*.xml
     
   logging:
     level:
       root: info
       com.demo.springboot: debug
       #spring boot2.0之后开启api映射打印
       org.springframework.web: trace
   ```

   

3. configuration类

   1. pom添加依赖，可使用ide 建议自动加入

   ``` xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-configuration-processor</artifactId>
       <optional>true</optional>
   </dependency>
   ```

   2. 根包下面新建一个包config，包下新建一个配置类DruidConfiguration配置数据源，往spring容器中注入datasource

      ``` java
      @Configuration
      public class DruidConfiguration {
      	@Value("${spring.datasource.url}")
      	private String url;
      	@Value("${spring.datasource.username}")
      	private String username;
      	@Value("${spring.datasource.password}")
      	private String password;
      	@Value("${spring.datasource.driverClassName}")
      	private String driverClassName;
      	
      	@Bean
      	@Primary
      	public DataSource dataSource() {
      		DruidDataSource dataSource = new DruidDataSource();
      		dataSource.setUrl(url);
      		dataSource.setUsername(username);
      		dataSource.setPassword(password);
      		dataSource.setDriverClassName(driverClassName);
      		return dataSource;
      	}
      }
      ```

      

   3. config包下新建MybatisConfiguration配置类，往spring容器中注入sqlSessionFactory

      ``` java
      @MapperScan("com.demo.springboot.mapper")
      public class MybatisConfiguration {
      	
      	@Value("${mybatis.type-aliases-package}")
      	private String typeAliasesPackage;
      	
          //采用注解方式可以不指定mapper-locations，自测有效
      	//@Value("${mybatis.mapper-locations}")
      	//private String mapperLocations;
      	
      	@Autowired
      	private DataSource dataSource;
      	
      	@Bean
      	public SqlSessionFactory sqlSessionFactory() throws Exception {
      		
      		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      		
      		sqlSessionFactoryBean.setDataSource(dataSource);
      	sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
              //sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
      		
      		return sqlSessionFactoryBean.getObject();
      	}
   }
      ```
      

------

## 编码

### Entity 对象实体层

	1. 项目根包下创建一个包：entity
 	2. entity包下新建一个类：User.java

``` java
public class User {
	private int id;
	private String username;
	private String address;
    //省略set get equals toString等自动生成代码
}
```

> ​	未使用jpa，如果使用@Entity注解会在数据库中生成相应表



### Mapper数据持久层

 1. 项目根包下创建一个包：mapper

 2. mapper包下新建一个接口：UserMapper.java

    ``` java
    @Mapper
    public interface UserMapper {
    	@Select("select * from tb_user where id=#{id}")
    	@Results(id="UserMap",value= {
    			@Result(column="id",property="id"),
    			@Result(column="username",property="username"),
    			@Result(column="address",property="address")
    	})
    	User getUser(User user);
    	
    	@Select("select * from tb_user")
    	@ResultMap(value="UserMap")
    	List<User> getUserList();
    	
    	@Insert("insert into tb_user(username,address) values(#{username},#{address})")
    	int addUser(User user);
    	
    	@Delete("delete from tb_user where id=#{id}")
    	int delUser(User user);
    	
    	@Update("update tb_user set username=#{username},address=#{address} where id=#{id}")
    	int updateUser(User user);
    }
    ```

    > 使用注解方式不是很好处理动态sql的问题，可以使用@SelectProvider注解，编写sql组成类，但是是以map方式传参，需要使用map.get()方法判断

    

 3. 增删查改对应@Insert @Delete @select @Update注解

 4. #{}, ${}两种方式使用不同

    > - #{}表示一个占位符号，通过#{}可以实现preparedStatement向占位符中设置值，自动进行java类型和jdbc类型转换。#{}可以有效防止sql注入。 #{}可以接收简单类型值或pojo属性值。 如果parameterType传输单个简单类型值，#{}括号中可以是value或其它名称。
    > - ${}表示拼接sql串，通过${}可以将parameterType 传入的内容拼接在sql中且不进行jdbc类型转换， ${}可以接收简单类型值或pojo属性值，如果parameterType传输单个简单类型值，${}括号中只能是value。

    

### Service 业务逻辑层

1. 项目根包下创建一个包：service

2. service包下新建一个类：UserService.java

   > 这里业务简单，并没有使用接口实现的方式

   

   ``` java
   @Service
   public class UserService {
   	@Autowired
   	private UserMapper userMapper;
   	public User getUser(User user) {
   		return userMapper.getUser(user);
   	}
   	public List<User> getUserList(){
   		return userMapper.getUserList();
   	}
   	@Transactional
   	public int addUser(User user) {
   		return userMapper.addUser(user);
   	}
   	@Transactional
   	public int delUser(User user) {
   		return userMapper.delUser(user);
   	}
   	@Transactional
   	public int updateUser(User user) {
   		return userMapper.updateUser(user);
   	}
   }
   ```

3. @Service表明这是一个业务类，@Autowired注入要调用的mapper依赖

   > @Autowired按类型，@Resource按名称

   

4. @Transactional开启事务



### Controller 控制表示层

1. 项目根包下创建一个包：controller

2. controller包下新建一个类：UserController

   ``` java
   @RestController
   public class UserController {
   
   	@Autowired
   	private UserService userService;
   	
   	@GetMapping("/user/{id}")
   	public User getUser(@PathVariable int id){
   		User user = new User();
   		user.setId(id);
   		return userService.getUser(user);
   	}
   	
   	@GetMapping("/userAll")
   	public List<User> getUserList(){
   		return userService.getUserList();
   	}
   	
   	@PostMapping("/user")
   	public int addUser(
   			@RequestParam(value="username", required=true) String username,
   			@RequestParam(value="address", required=true) String address) {
   		User user = new User();
   		user.setUsername(username);
   		user.setAddress(address);
   		return userService.addUser(user);
   	}
   	
   	@DeleteMapping("/user/{id}")
   	public int delUser(@PathVariable int id) {
   		User user = new User();
   		user.setId(id);
   		return userService.delUser(user);
   	}
   	
   	@PutMapping("/user/{id}")
   	public int updateUser(
   			@PathVariable int id,
   			@RequestParam(value="username", required=true) String username,
   			@RequestParam(value="address", required=true) String address) {
   		User user = new User();
   		user.setId(id);
   		user.setUsername(username);
   		user.setAddress(address);
   		return userService.updateUser(user);
   	}
   ```

3. @RestController是一个复合注解，主要包含@Controller和@ResponseBody（表示返回数据到http reponse的body中，主要json）

4. @Autowired注入依赖的service层对象实例

5. @GetMapping @PostMapping @DeleteMapping @PutMapping都为复合注解，对应RESTful的增删查改

6. 映射地址中{id}传参变量使用@PathVariable注解修饰变量接收

7. @RequestParam也对应传参，注解内部提供多项参数（ value，name，required，defaultValue）

   ------

   

## 编译&测试

1. Run As…springboot app
2. Console中可以看到是否启动成功，默认使用内嵌的tomcat 应用服务器
3. WebApplicationContext初始化成功可以看到HandlerMapping映射的资源路由地址
4. 浏览器或者postman工具测试url，控制台打印DispatcherServlet分发的HandlerAdapter（实际包括HandlerMethod 和HandlerInterceptor，这里并没有配置拦截器），已经mybatis mapper接口的sql语句
5. 检查返回的json是否符合预期

------

## 打包

1. Run As…maven clean…maven install

   eclipse集成maven，使用maven install生成target，即pom文件中定义的packaging格式，默认是jar。

2. 生成的jar包可直接控制台java -jar xxx.jar运行

### 错误记录

1. 错误描述：在执行maven install的时候有时会出现 The requested profile “pom.xml” could not be activated because it does not exist 的错误
   解决办法：在项目中单击右键 --属性 --maven,将此处清空保存即可

2. 错误描述：No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?

   解决办法：build path替换运行环境jre为jdk

3. 错误描述：maven install中test失败，Unable to find a @SpringBootConfiguration, you need to use @ContextConfiguration or @SpringBootTest(…

   解决办法：@SpringBootTest(classes = Application.class)

   ​			或者@RunWith(SpringRunner.class)

