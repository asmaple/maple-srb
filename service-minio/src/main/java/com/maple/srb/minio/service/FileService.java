package com.maple.srb.minio.service;

import com.maple.srb.minio.pojo.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    FileDTO uploadFile(MultipartFile multipartFile, String bucketName);

    boolean downloadFile(HttpServletResponse httpServletRespons,String bucketName,String fileName);

    String getFileURL(String bucketName,String fileName);

    boolean removeBucket(String bucketName);

    boolean removeFile(String bucketName,String fileName);

    boolean removeFiles(String bucketName, String keys);
}
