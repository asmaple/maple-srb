package com.maple.srb.minio;

import com.maple.srb.minio.util.MinioProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MinIoTest {
    @Test
    public void testProperties(){

        System.out.println(MinioProperties.ENDPOINT);
        System.out.println(MinioProperties.ACCESS_KEY);
        System.out.println(MinioProperties.SECRET_KEY);
        System.out.println(MinioProperties.BUCKET_NAME);
    }
}
