package com.maple.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.result.R;
import com.maple.common.util.MD5;
import com.maple.srb.core.mapper.FileInfoMapper;
import com.maple.srb.core.pojo.entity.FileInfo;
import com.maple.srb.core.pojo.entity.UserInfo;
import com.maple.srb.core.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-03-05
 */
@Service
@Slf4j
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

    @Transactional(rollbackFor = Exception.class) // 事务，出错回退
    @Override
    public R insertFile(String bucketName, String objectName, String fileRename, String fileUrl, String originalFilename, String fileType, Long fileSize, String encryptKey,String moduleName) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setBucketName(bucketName);
        fileInfo.setObjectName(objectName);
        fileInfo.setFileRename(fileRename);
        fileInfo.setFileUrl(fileUrl);
        fileInfo.setOriginalFilename(originalFilename);
        fileInfo.setFileType(fileType);
        fileInfo.setFileSize(fileSize);
        fileInfo.setEncryptKey(encryptKey);
        fileInfo.setModuleName(moduleName);

        log.error("=insertFile====>>>>" + bucketName);
        log.error("=insertFile====>>>>" + objectName);
        log.error("=insertFile====>>>>" + fileRename);
        log.error("=insertFile====>>>>" + fileUrl);
        log.error("=insertFile====>>>>" + originalFilename);
        log.error("=insertFile====>>>>" + fileType);
        log.error("=insertFile====>>>>" + fileSize);
        log.error("=insertFile====>>>>" + encryptKey);
        log.error("=insertFile====>>>>" + moduleName);
        int count = baseMapper.insert(fileInfo);
        R insertFileResult = R.error();
        if(count > 0){
            insertFileResult = R.ok();
        }
        return insertFileResult;
    }
}
