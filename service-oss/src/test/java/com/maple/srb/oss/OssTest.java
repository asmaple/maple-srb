package com.maple.srb.oss;

import com.maple.srb.oss.util.OssProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OssTest {

    @Test
    public void testProperties(){

        System.out.println(OssProperties.ENDPOINT);
        System.out.println(OssProperties.KEY_ID);
        System.out.println(OssProperties.KEY_SECRET);
        System.out.println(OssProperties.BUCKET_NAME);
    }
}
