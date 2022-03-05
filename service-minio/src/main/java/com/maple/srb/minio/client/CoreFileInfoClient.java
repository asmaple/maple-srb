package com.maple.srb.minio.client;

import com.maple.common.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service-core") // 服务名
public interface CoreFileInfoClient {

    @PostMapping("/admin/core/fileInfo/insertFile")
    R insertFile(@RequestParam String bucketName, @RequestParam String objectName, @RequestParam String fileRename, @RequestParam String fileUrl, @RequestParam String originalFilename, @RequestParam String fileType, @RequestParam Long fileSize, @RequestParam(required = false) String encryptKey);
}
