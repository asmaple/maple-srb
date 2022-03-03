package com.maple.srb.minio.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties implements InitializingBean {

//    enable: true  # 必须配置 true
//    enableHttps: false # 是否使用https

    private Boolean enable;

    private Boolean enableHttps;



    /**
     * minio地址+端口号
     */
    private String endpoint;

    /**
     * minio用户名
     */
    private String accessKey;

    /**
     * minio密码
     */
    private String secretKey;

    /**
     * 文件桶的名称
     */
    private String bucketName;

    public static Boolean ENABLE;
    public static Boolean ENABLE_HTTPS;
    public static String ENDPOINT;
    public static String ACCESS_KEY;
    public static String SECRET_KEY;
    public static String BUCKET_NAME;

    //当私有成员被赋值后，此方法自动被调用，从而初始化常量
    @Override
    public void afterPropertiesSet() throws Exception {
        ENABLE = enable;
        ENABLE_HTTPS = enableHttps;
        ENDPOINT = endpoint;
        ACCESS_KEY = accessKey;
        SECRET_KEY = secretKey;
        BUCKET_NAME = bucketName;
    }
}