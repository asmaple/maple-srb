package com.maple.srb.minio.util;

import cn.hutool.core.io.IoUtil;
import com.maple.srb.minio.pojo.dto.FileDTO;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MinioUtil {

    public static final String SEPARATOR = "/";


    @Resource
    private CustomMinioClient customMinioClient;

    public boolean bucketExists(String bucketName) throws Exception {
        if (StringUtils.isEmpty(bucketName)) {
            return false;
        }
        boolean isExists = customMinioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (isExists) {
            return true;
        }
        try {
            customMinioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 上传文件
     */
    public FileDTO uploadFile(MultipartFile file,String bucketName) throws Exception {
        if (!bucketExists(bucketName)) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        // 上传文件路径
        String rename = RenameUtil.generateFileName(fileName);
        String objectName = RenameUtil.getFilePath(rename);
        String fileUrl = MinioProperties.ENDPOINT + SEPARATOR + bucketName + SEPARATOR + objectName;
        PutObjectArgs objectArgs = PutObjectArgs.builder().object(objectName)
                .bucket(bucketName)
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), -1).build();
        customMinioClient.putObject(objectArgs);
        return FileDTO.builder().bucketName(bucketName)
                .objectName(objectName)
                .originalFilename(fileName)
                .url(fileUrl)
                .rename(rename)
                .build();
    }

    /**
     * 从bucket获取指定对象的输入流，后续可使用输入流读取对象
     * getObject与minio server连接默认保持5分钟，
     * 每隔15s由minio server向客户端发送keep-alive check，5分钟后由客户端主动发起关闭连接
     * */
    public InputStream getObject(String bucketName, String objectName) throws Exception{
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        return customMinioClient.getObject(args);
    }

    /**
     * 获取对象的临时访问url，有效期5分钟
     * */
    public String getObjectURL(String bucketName, String objectName) throws Exception{
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .expiry(5, TimeUnit.MINUTES)
                .method(Method.GET)
                .build();
        return customMinioClient.getPresignedObjectUrl(args);
    }

    /**
     * 直接下载文件
     */
    public boolean downloadFile(HttpServletResponse httpServletResponse, String bucketName,String fileName) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName)
                .object(fileName).build();
        OutputStream outputStream = null;
        try (GetObjectResponse response = customMinioClient.getObject(objectArgs)) {
            outputStream = httpServletResponse.getOutputStream();
            StreamUtils.copy(response, outputStream);
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/force-download");// 设置强制下载不打开
            httpServletResponse.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(outputStream);
        }
        return false;
    }


    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    public Optional<Bucket> getBucket(String bucketName) throws Exception {
        return customMinioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 获取全部bucket
     * <p>
     * https://docs.minio.io/cn/java-client-api-reference.html#listBuckets
     */
    public List<Bucket> getAllBuckets() throws Exception {
        return customMinioClient.listBuckets();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    public void removeBucket(String bucketName) throws Exception {
        customMinioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    public void removeObject(String bucketName, String objectName) throws Exception {
        customMinioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 批量删除文件
     *
     * @param bucketName bucket
     * @param keys       需要删除的文件列表
     * @return
     */
    public Iterable<Result<DeleteError>> removeObjects(String bucketName, List<String> keys) {
        List<DeleteObject> objects = new LinkedList<>();
        keys.forEach(s -> {
            objects.add(new DeleteObject(s));
        });
        return customMinioClient.removeObjects(
                RemoveObjectsArgs.builder().bucket(bucketName).objects(objects).build());
    }
}
