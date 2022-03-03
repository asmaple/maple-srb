package com.maple.srb.minio.config;

import com.maple.srb.minio.util.CustomMinioClient;
import com.maple.srb.minio.util.MinioProperties;
import com.maple.srb.minio.util.OKHttpClientBuilder;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;


/**
 * 获取配置文件信息
 */
@Configuration
@ConditionalOnExpression("${minio.enable:false}")
public class MinIoClientConfig {

    @Value("${minio.enable}")
    private Boolean enable;
    @Value("${minio.enableHttps}")
    private Boolean enableHttps;
    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {

        MinioClient.Builder builder = MinioClient.builder();
        if (enableHttps){
            builder.httpClient(OKHttpClientBuilder.buildOKHttpClient().build());
        }
        return builder.endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Bean
    public CustomMinioClient customMinioClient() {
        return new CustomMinioClient(minioClient());
    }
}