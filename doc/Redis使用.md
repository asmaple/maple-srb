1, 引入redis 
```
<!-- spring boot redis缓存引入 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!-- 缓存连接池-->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>

        <!-- redis 存储 json序列化 -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
        </dependency>
<!-- end-->
```

2,配置redis   application.yml 

spring 节点下

```
  redis:
#    host: 192.168.100.100
    host: 127.0.0.1
    port: 6379
    database: 0
    password: #默认为空
    timeout: 3000ms #最大等待时间，超时则抛出异常，否则请求一直等待
    lettuce:
      pool:
        max-active: 20  #最大连接数，负值表示没有限制，默认8
        max-wait: -1    #最大阻塞等待时间，负值表示没限制，默认-1
        max-idle: 8     #最大空闲连接，默认8
        min-idle: 0     #最小空闲连接，默认0

```
3, windows 下启动redis

cmd 到 D:\tools\Redis-x64-3.2.100 目录
测试
    先 redis-cli 
    存值：set A  123
    取值：get A
