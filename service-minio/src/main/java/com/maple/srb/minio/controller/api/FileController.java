package com.maple.srb.minio.controller.api;

import com.maple.common.result.R;
import com.maple.srb.minio.client.CoreFileInfoClient;
import com.maple.srb.minio.pojo.dto.FileDTO;
import com.maple.srb.minio.service.FileService;
import com.maple.srb.minio.util.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "MinIo文件管理 file")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/api/minio/file")
@Slf4j
public class FileController {

    @Resource
    private FileService fileService;

    @Resource
    private CoreFileInfoClient coreFileInfoClient;


    /**
     * 文件上传
     * @param file
     * @param bucketName
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    @ResponseBody
    public R upload(
            @ApiParam(value= "文件", required = true)
            @RequestParam("file") MultipartFile file,

            @ApiParam(value = "桶名", required = true)
            @RequestParam("bucketName") String bucketName,

            @ApiParam(value = "模块名", required = true)
            @RequestParam("moduleName") String moduleName) {

        FileDTO fileDTO = fileService.uploadFile(file,bucketName,moduleName);
        if(fileDTO != null){
            // 桶名称
             String dtoBucketName = fileDTO.getBucketName();
            // 对象名称
            String objectName = fileDTO.getObjectName();
            // 重命名的文件名称
            String fileRename = fileDTO.getFileRename();
            // 外链
            String fileUrl = fileDTO.getFileUrl();
            //文件原始名称
            String originalFilename = fileDTO.getOriginalFilename();
            // 文件类型
            String fileType = fileDTO.getFileType();
            // 文件长度
            Long fileSize = fileDTO.getFileSize();
            // 加密密钥
            String encryptKey = fileDTO.getEncryptKey();

            String dtoModuleName = fileDTO.getModuleName();

            R insertFileResult = coreFileInfoClient.insertFile(dtoBucketName,objectName,fileRename,fileUrl,originalFilename,fileType,fileSize,encryptKey,dtoModuleName);
            if(insertFileResult.isOk()) {
                return R.ok().data("fileInfo",fileDTO).message("文件上传成功");
            }
        }
        return R.error().message("文件上传失败");
    }


    @ApiOperation("获取文件临时访问路径")
    @GetMapping("/getFileURL")
    public R getFileURL(
            @ApiParam(value = "桶名", required = true)
            @RequestParam("bucketName") String bucketName,

            @ApiParam(value = "文件名", required = true)
            @RequestParam("fileName") String fileName) {

        String fileURL = fileService.getFileURL(bucketName, fileName);
        if(!StringUtils.isEmpty(fileURL)){
            return R.ok().data("fileURL",fileURL);
        }
        return R.error();
    }

    /**
     * 下载文件
     * @param httpServletResponse
     * @param bucketName
     * @param fileName
     */
    @ApiOperation("文件下载")
    @GetMapping("/download")
    public void download(HttpServletResponse httpServletResponse,
    @ApiParam(value = "桶名", required = true)
    @RequestParam("bucketName") String bucketName,
    @ApiParam(value = "文件名", required = true)
    @RequestParam("fileName") String fileName) {
        boolean result = fileService.downloadFile(httpServletResponse, bucketName,fileName);
        log.info("=====文件下载结果=====>>>" + result);
    }


    @ApiOperation("根据桶名删除桶")
    @PostMapping("/removeBucket")
    public R removeBucket(
                         @ApiParam(value = "桶名", required = true)
                         @RequestParam("bucketName") String bucketName) {

        boolean result = fileService.removeBucket(bucketName);
        return result? R.ok(): R.error();
    }


    ///20220304/49xvu64h9kdbkzkjchbyivq188eudhr2.bmp
    @ApiOperation("根据文件名删除文件")
    @PostMapping("/removeFile")
    public R removeFile(
            @ApiParam(value = "桶名", required = true,example = "srb")
            @RequestParam("bucketName") String bucketName,
            @ApiParam(value = "文件名", required = true,example = "/20220304/49xvu64h9kdbkzkjchbyivq188eudhr2.bmp")
            @RequestParam("fileName") String fileName) {
        boolean result = fileService.removeFile(bucketName,fileName);
        return result? R.ok(): R.error();
    }


    @ApiOperation("多文件删除")
    @PostMapping("/removeFiles")
    public R removeFiles(
            @ApiParam(value = "桶名", required = true)
            @RequestParam("bucketName") String bucketName,
            @ApiParam(value = "文件名以英文逗号分割(最后一条不带逗号)", required = true)
            @RequestParam("keys") String keys) {

//        String[] strArray = {"aaa","bbb","ccc"}；
//        String str= StringUtils.join(strArry,",");
//        System.out.println(str);

        boolean result = fileService.removeFiles(bucketName,keys);
        return result? R.ok(): R.error();
    }

    @ApiOperation("查看桶策略")
    @GetMapping("/getBucketPolicy")
    public R queryBucketPolicy(
                         @ApiParam(value = "桶名", required = true)
                         @RequestParam("bucketName") String bucketName) {
        String bucketPolicy = fileService.queryBucketPolicy(bucketName);
        if(!StringUtils.isEmpty(bucketPolicy)){
            return R.ok().data("bucketPolicy",bucketPolicy);
        }
        return R.error();
    }
}
