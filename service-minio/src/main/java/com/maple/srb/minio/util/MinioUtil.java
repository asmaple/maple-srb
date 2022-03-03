package com.maple.srb.minio.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.HashMultimap;
import com.maple.srb.minio.pojo.dto.FileDTO;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MinioUtil {
    @Resource
    private MinioClient minioClient;
    @Resource
    private CustomMinioClient customMinioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 上传文件
     */
    public FileDTO uploadFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        // 上传文件路径
        String rename = RenameUtil.generateFileName(fileName);
        String objectName = RenameUtil.getFilePath(rename);
        PutObjectArgs objectArgs = PutObjectArgs.builder().object(objectName)
                .bucket(bucketName)
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), -1).build();
        minioClient.putObject(objectArgs);
        return FileDTO.builder().bucketName(bucketName).objectName(objectName).originalFilename(fileName).build();
    }

    /**
     * 单文件签名上传
     *
     * @param objectName 文件全路径名称
     * @return /
     */
    public String getUploadObjectUrl(String objectName) {
        // 上传文件时携带content-type头即可
        /*if (StrUtil.isBlank(contentType)) {
            contentType = "application/octet-stream";
        }
        HashMultimap<String, String> headers = HashMultimap.create();
        headers.put("Content-Type", contentType);*/
        try {
            return customMinioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(1, TimeUnit.DAYS)
                            //.extraHeaders(headers)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 直接下载文件
     */
    public void downloadFile(HttpServletResponse res, String fileName) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket("file")
                .object(fileName).build();
        OutputStream outputStream = null;
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            outputStream = res.getOutputStream();
            StreamUtils.copy(response, outputStream);
            res.setCharacterEncoding("utf-8");
            res.setContentType("application/force-download");// 设置强制下载不打开
            res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(outputStream);
        }
    }

    /**
     * 初始化分片上传
     *
     * @param objectName  文件全路径名称
     * @param partCount   分片数量
     * @param contentType 类型，如果类型使用默认流会导致无法预览
     * @return /
     */
    public Map<String, Object> initMultiPartUpload(String objectName, int partCount, String contentType) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (StrUtil.isBlank(contentType)) {
                contentType = "application/octet-stream";
            }
            HashMultimap<String, String> headers = HashMultimap.create();
            headers.put("Content-Type", contentType);
            String uploadId = customMinioClient.initMultiPartUpload(bucketName, null, objectName, headers, null);

            result.put("uploadId", uploadId);
            List<String> partList = new ArrayList<>();

            Map<String, String> reqParams = new HashMap<>();
            //reqParams.put("response-content-type", "application/json");
            reqParams.put("uploadId", uploadId);
            for (int i = 1; i <= partCount; i++) {
                reqParams.put("partNumber", String.valueOf(i));
                String uploadUrl = customMinioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.PUT)
                                .bucket(bucketName)
                                .object(objectName)
                                .expiry(1, TimeUnit.DAYS)
                                .extraQueryParams(reqParams)
                                .build());
                partList.add(uploadUrl);
            }
            result.put("uploadUrls", partList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    /**
     * 分片上传完后合并
     *
     * @param objectName 文件全路径名称
     * @param uploadId   返回的uploadId
     * @return /
     */
    public boolean mergeMultipartUpload(String objectName, String uploadId) {
        try {
            //TODO::目前仅做了最大1000分片
            Part[] parts = new Part[1000];
            ListPartsResponse partResult = customMinioClient.listMultipart(bucketName, null, objectName, 1000, 0, uploadId, null, null);
            int partNumber = 1;
            for (Part part : partResult.result().partList()) {
                parts[partNumber - 1] = new Part(partNumber, part.etag());
                partNumber++;
            }
            customMinioClient.mergeMultipartUpload(bucketName, null, objectName, uploadId, parts, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
