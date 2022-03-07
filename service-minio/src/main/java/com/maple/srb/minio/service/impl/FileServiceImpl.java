package com.maple.srb.minio.service.impl;

import com.maple.common.exception.Assert;
import com.maple.common.result.ResponseEnum;
import com.maple.srb.minio.pojo.dto.FileDTO;
import com.maple.srb.minio.service.FileService;
import com.maple.srb.minio.util.MinioUtil;
import io.minio.Result;
import io.minio.messages.DeleteError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private MinioUtil minioUtil;


    @Override
    public FileDTO uploadFile(MultipartFile multipartFile, String bucketName, String moduleName) {
        Assert.notNull(multipartFile, ResponseEnum.UPLOAD_ERROR);
        Assert.notEmpty(bucketName, ResponseEnum.UPLOAD_ERROR);
        Assert.notEmpty(moduleName, ResponseEnum.UPLOAD_ERROR);
        try {
            FileDTO fileDTO = minioUtil.uploadFile(multipartFile, bucketName,moduleName);
            return fileDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean downloadFile(HttpServletResponse httpServletResponse, String bucketName, String fileName) {
        Assert.notEmpty(bucketName, ResponseEnum.DOWNLOAD_ERROR);
        Assert.notEmpty(fileName, ResponseEnum.DOWNLOAD_ERROR);
        return minioUtil.downloadFile(httpServletResponse, bucketName, fileName);
    }

    @Override
    public String getFileURL(String bucketName, String fileName) {
        Assert.notEmpty(bucketName, ResponseEnum.PARAMETER_ERROR);
        Assert.notEmpty(fileName, ResponseEnum.PARAMETER_ERROR);
        try {
            return minioUtil.getObjectURL(bucketName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeBucket(String bucketName) {
        Assert.notEmpty(bucketName, ResponseEnum.PARAMETER_ERROR);
        try {
            minioUtil.removeBucket(bucketName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeFile(String bucketName, String fileName) {
        Assert.notEmpty(bucketName, ResponseEnum.DOWNLOAD_ERROR);
        Assert.notEmpty(fileName, ResponseEnum.DOWNLOAD_ERROR);
        try {
            minioUtil.removeObject(bucketName, fileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeFiles(String bucketName, String keys) {
        Assert.notEmpty(bucketName, ResponseEnum.DOWNLOAD_ERROR);
        Assert.notEmpty(keys,ResponseEnum.DOWNLOAD_ERROR);
        try {
            String[] arrays = keys.split(",");
            List<String> keyList = Stream.of(arrays).collect(Collectors.toList());
            Iterable<Result<DeleteError>> iterable = minioUtil.removeObjects(bucketName, keyList);
            if(getIterableCount(iterable) <= 0) {
                return true;
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return false;
    }

    @Override
    public String queryBucketPolicy(String bucketName) {
        Assert.notEmpty(bucketName, ResponseEnum.DOWNLOAD_ERROR);
        try {
            return minioUtil.getBucketPolicy(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getIterableCount(Iterable<?> i){
        int count = 0;
        Iterator<?> it = i.iterator();
        while (it.hasNext()) {
            it.next();
            count++;
        }
        return count;
    }
}
