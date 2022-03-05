package com.maple.srb.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.maple.srb","com.maple.common"})
@EnableFeignClients // 开启 OpenFeign
@EnableDiscoveryClient
public class ServiceMinioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMinioApplication.class,args);
    }
}
