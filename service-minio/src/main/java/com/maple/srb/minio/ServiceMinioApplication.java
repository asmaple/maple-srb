package com.maple.srb.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.maple.srb","com.maple.common"})
@EnableDiscoveryClient
public class ServiceMinioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceMinioApplication.class,args);
    }
}
