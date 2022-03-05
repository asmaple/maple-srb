package com.maple.srb.core.controller.admin;


import com.maple.common.result.R;
import com.maple.srb.core.service.FileInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 文件信息 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@RestController
@RequestMapping("/admin/core/fileInfo")
public class AdminFileInfoController {

//
    @Resource
    private FileInfoService fileInfoService;

    @ApiOperation("新增文件")
    @PostMapping("/insertFile")
    public R insertFile(@RequestParam String bucketName, @RequestParam String objectName, @RequestParam String fileRename, @RequestParam String fileUrl, @RequestParam String originalFilename, @RequestParam String fileType, @RequestParam Long fileSize, @RequestParam(required = false) String encryptKey){
       return fileInfoService.insertFile(bucketName, objectName, fileRename, fileUrl, originalFilename, fileType, fileSize, encryptKey);
    }
}

