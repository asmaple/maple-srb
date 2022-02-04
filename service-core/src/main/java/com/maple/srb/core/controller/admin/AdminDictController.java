package com.maple.srb.core.controller.admin;


import com.maple.common.exception.BusinessException;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@Api(tags = "数据字典管理")
@RestController
@RequestMapping("/admin/core/dict")
@Slf4j
@CrossOrigin
public class AdminDictController {

    @Resource
    DictService dictService;

    @ApiOperation("Excel数据的批量导入")
    @PostMapping("/import")
    public R batchImport(
            @ApiParam(value = "Excel数据字典文件", required = true)
            @RequestParam("file") MultipartFile file){

        try {
            InputStream inputStream = file.getInputStream();
            dictService.importData(inputStream);

            return R.ok().message("数据字典数据批量导入成功");

        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }
}


