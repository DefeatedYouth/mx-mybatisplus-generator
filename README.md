## 项目简介

一个基于 [MyBatis-Plus](https://github.com/baomidou/mybatis-plus) 的简单好用代码生成工具。

## 功能说明

- Controller.java 控制器生成，包含基本的增删改查接口，适用于后台项目
- Service 服务接口
- ServiceImpl.java 对应```Service```服务实现
- Model 实体对象,与表结构对应
- Mapper.java 对应XML的接口
- Mapper.xml 对应表```Mapper```的```XML```文件
- Query.java 默认分页查询对象，继承Model,对于时间类型的字段，会默认生成对应的```XXXBegin```和```XXXEnd```属性，方便用来做范围查询，不污染model

## 快速开始

- 修改代码生成器启动类```CodeGeneratorBootstrap```相关参数：
  - 文件创建者 ```author```
  - 需要生成的多张表 ```tables```
  - controller模块所在main目录(mac/windows) ```controllerProjectMainPath```
  - service模块所在main目录(mac/windows) ```serviceProjectMainPath```
  - dao模块所在main目录(mac/windows) ```repoProjectMainPath```
  - 目标controller所在的模块名(portal/admin/sio) ```controllerModuleName```
  - 是否是mac操作系统(true/false),涉及路径分隔符 ```mac```
  
  - 项目根包名(不用修改) ```rootPackageName```
  - 目标service所在的模块名(不用修改) ```serviceModuleName```
  - 目标dao所在的模块名(不用修改) ```repoModuleName```
    
- 要生成代码的项目中增加相关配置:
  - 添加```maven```依赖
  ```xml
       <dependency>
            <groupId>com.biyiapp.common</groupId>
            <artifactId>biyi-common</artifactId>
            <version>0.0.1-SNAPSHOT</version>
      </dependency>
      
      <dependency>
             <groupId>com.baomidou</groupId>
             <artifactId>mybatis-plus-boot-starter</artifactId>
             <version>${mybatisplus.version}</version>
       </dependency>
  
       <properties>
          <mybatisplus.version>3.4.0</mybatisplus.version>
       </properties>
    ```

  - 添加```MybatisPlusConfig```配置文件
  ```java
    @Configuration
    @MapperScan("**要扫描的Mapper.java所在的包名,或者在springBoot启动类中配置**")
    public class MybatisPlusConfig {
        /**
         * @Desc 自定义分页拦截器
         */
        @Bean
        public PaginationInterceptor paginationInterceptor() {
        	PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    
        	// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false,特殊业务场景下设置为true
            // paginationInterceptor.setOverflow(false);
    
            // 设置最大单页限制数量，默认最大500 条，-1 不受限制,超过则此值为默认值，不报错
             paginationInterceptor.setLimit(100);
            // 开启 count 的 join 优化,只针对部分 left join
            paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
            return paginationInterceptor;
        }
    
    }
  ```
  - 在```properties或yml```配置文件中增加MyBatis-Plus的相关配置
  ```properties
    mybatis-plus.mapper-locations=classpath:/mapper/*.xml
    mybatis-plus.global-config.db-config.id-type=auto
    mybatis-plus.global-config.banner=false
    mybatis-plus.global-config.db-config.logic-delete-value=1
    mybatis-plus.global-config.db-config.logic-not-delete-value=0
    mybatis-plus.configuration.map-underscore-to-camel-case=true
    mybatis-plus.configuration.cache-enabled=false
  ```
  
  ```yml
  mybatis-plus:
    mapper-locations: classpath:/mappers/*.xml
    global-config:
      db-config:
        id-type: auto
        banner: false
        logic-delete-value: 1
        logic-not-delete-value: 0
    configuration:
      cache-enabled: false
  #    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
      map-underscore-to-camel-case: true
      default-fetch-size: 100
      default-statement-timeout: 30
    ```
  
- 运行```ly-generator```项目的```CodeGeneratorBootstrap```启动类即可生成

- common项目中添加base类:
  - BaseController
  - BaseService
  - BaseServiceImpl
  - 添加```maven```依赖
```xml
      <dependency>
             <groupId>com.baomidou</groupId>
             <artifactId>mybatis-plus-boot-starter</artifactId>
             <version>${mybatisplus.version}</version>
       </dependency>
  
       <properties>
          <mybatisplus.version>3.4.0</mybatisplus.version>
       </properties>
```

## 补充说明

- 生成器代码说明
  - CodeGeneratorBootstrap.java 代码生成启动入口
  - GeneratorService.java 代码生成实现，可根据需要修改jdbc等配置
  - 默认生成的文件有7个,且默认已存在该文件不替换(可重复启动,无需关心),如果只需生成部分文件,可反注释启动类中的.outPutXXX(false)
  - 自定义代码生成样式修改```本项目\resources\templates\XXX.vm```
- 注意项说明
  - 数据库所有表设计均需要自增```id``` , 逻辑删除```is_delete```字段
  - 默认更新/查询均过滤逻辑删除，即过滤掉is_delete=1，```如果业务需要手动自定义xml的sql```
  - xml中默认生成一个默认的分页查询，用来解决分页查询中带时间范围的查询条件，需要额外做以下几步：
    1. 生成```Query.java```查询对象
- 其他说明:
  - 推荐生成时对应各文件的包名&目录
    - Entity(Domain)    ```包名.dal.model```
    - Mapper.xml        ```resources/mappers/*.xml```
    - Mapper.java       ```包名.dal.dao```
    - Service           ```包名.service```
    - ServiceImpl 生成   ```包名.service.impl```
    - Controller 生成    ```包名.controller```
    - Query.java 生成    ```包名.dal.query```
  - mybatis升级到mybatis-plus应注意版本：
    - 如ly项目mybatis版本3.5.4,升级后的mybatis-plus版本3.4.0，内置依赖mybatis版本3.5.5,最大化兼容，保证不会出问题
  - mybatis-plus官方使用说明文档：[MyBatis-Plus使用文档](https://mybatis.plus/guide/#%E7%89%B9%E6%80%A7)
  - mybatis-plus使用参考案例：
    - ly项目分支``` v-mybatisPlus-wj-1222```
    - demo类``` MybatisPlusTestController```