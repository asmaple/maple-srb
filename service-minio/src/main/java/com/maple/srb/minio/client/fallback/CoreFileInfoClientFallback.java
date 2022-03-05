package com.maple.srb.minio.client.fallback;

import com.maple.common.result.R;
import com.maple.srb.minio.client.CoreFileInfoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoreFileInfoClientFallback implements CoreFileInfoClient {

    @Override
    public R insertFile(String bucketName, String objectName, String fileRename, String fileUrl, String originalFilename, String fileType, Long fileSize, String encryptKey) {
        log.error("新增文件远程调用成功");
        return R.error();
    }
}
