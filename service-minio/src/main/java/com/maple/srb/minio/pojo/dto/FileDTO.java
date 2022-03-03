package com.maple.srb.minio.pojo.dto;
import lombok.Data;

import lombok.Builder;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Data
@Builder
@ToString
public class FileDTO {
    /**
     * 桶名称
     */
    private String bucketName;
    /**
     * 对象名称
     */
    private String objectName;
    /**
     * 重命名的文件名称
     */
    private String rename;
    /**
     * 外链
     */
    private String url;

    /**
     * 文件原始名称
     */
    private String originalFilename;

    /**
     * 加密密钥
     */
    private String encryptKey;

    @Tolerate
    public FileDTO() {

    }
}
