### 一、 下载  nacos-server-2.0.4.zip
1，解压 nacos-server-2.0.4.zip
2，进入到D:\tools\nacos-server-2.0.4\nacos\bin 目录下
3，启动：startup.cmd -m standalone   // 单服务启动模式，
4，访问：http://localhost:8848/nacos
5，用户名密码：nacos/nacos
6，关闭：shutdown.cmd
### 二、 服务发现
```
1、引入依赖
service-base模块中配置Nacos客户端依赖

<!--服务发现-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>


2、添加服务配置信息
在需要注册到注册中心的微服务放入配置文件中添加配置
#spring:
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848 # nacos服务地址
```


