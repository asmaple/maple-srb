package com.maple.srb.oss.service;

import java.io.InputStream;

public interface FileService {
    /**
     * 文件上传至阿里云
     */
    String upload(InputStream inputStream, String module, String fileName);

    // 删除文件
    boolean removeFile(String url);
}
