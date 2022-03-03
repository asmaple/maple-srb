package com.maple.srb.minio.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.maple.srb.minio.service.FileService;
import com.maple.srb.minio.util.MinioUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private MinioUtil minioUtil;

    @Override
    public Map<String, Object> initMultiPartUpload(String path, String filename, Integer partCount, String contentType) {
        path = path.replaceAll("/+", "/");
        if (path.indexOf("/") == 0) {
            path = path.substring(1);
        }
        String filePath = path + "/" + filename;

        Map<String, Object> result;
        // TODO::单文件上传可拆分，这里只做演示，可直接上传完成
        if (partCount == 1) {
            String uploadObjectUrl = minioUtil.getUploadObjectUrl(filePath);
            result = ImmutableMap.of("uploadUrls", ImmutableList.of(uploadObjectUrl));
        } else {
            result = minioUtil.initMultiPartUpload(filePath, partCount, contentType);
        }

        return result;
    }

    @Override
    public boolean mergeMultipartUpload(String objectName, String uploadId) {
        return minioUtil.mergeMultipartUpload(objectName, uploadId);
    }
}
