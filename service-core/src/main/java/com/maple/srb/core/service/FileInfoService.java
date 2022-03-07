package com.maple.srb.core.service;

import com.maple.common.result.R;
import com.maple.srb.core.pojo.entity.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ggq
 * @since 2022-03-05
 */
public interface FileInfoService extends IService<FileInfo> {

    R insertFile(String bucketName, String objectName, String fileRename, String fileUrl, String originalFilename, String fileType, Long fileSize, String encryptKey, String moduleName);
}
