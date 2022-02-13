package com.maple.srb.oss.controller.api;

import com.maple.common.exception.BusinessException;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.srb.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Api(tags = "阿里云文件管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/api/oss/file")
@Slf4j
public class FileController {

    @Resource
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value= "文件", required = true)
            @RequestParam("file") MultipartFile file,

            @ApiParam(value = "模块", required = true)
            @RequestParam("module") String module){

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String url = fileService.upload(inputStream, module, originalFilename);

            return R.ok().message("文件上传成功").data("url", url);
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("删除oss文件")
    @DeleteMapping("/remove")
    public R remove(
            @ApiParam(value = "要删除的文件的url（阿里云oss后台URl地址全路径）", required = true)
            @RequestParam("url") String url){
        boolean result = fileService.removeFile(url);
        if(result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }
}
