package com.maple.srb.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件信息
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FileInfo对象", description="文件信息")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "桶名")
    private String bucketName;

    @ApiModelProperty(value = "文件名（带前缀-文件夹）")
    private String objectName;

    @ApiModelProperty(value = "rename")
    private String rename;

    @ApiModelProperty(value = "文件地址")
    private String url;

    @ApiModelProperty(value = "原始名称")
    private String originalFilename;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "秘钥key")
    private String encryptKey;

    @ApiModelProperty(value = "文件长度")
    private Long fileSize;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标记（0:不可用 1:可用）")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

}
