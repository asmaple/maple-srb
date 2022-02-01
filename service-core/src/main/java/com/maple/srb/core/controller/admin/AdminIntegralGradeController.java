package com.maple.srb.core.controller.admin;


import com.maple.srb.core.mapper.IntegralGradeMapper;
import com.maple.srb.core.pojo.entity.IntegralGrade;
import com.maple.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@Api(tags = "积分等级管理")
@CrossOrigin  // 跨域
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;

    @ApiOperation(value = "获取hello world")
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/test")
    public List<IntegralGrade> listAll(){
        return integralGradeService.list();
    }

    @ApiOperation(value = "根据id删除数据记录", notes = "逻辑删除")
    @DeleteMapping("/remove/{id}")
    public boolean removeById(
            @ApiParam(value = "要删除的id", example = "1", required = true)
            @PathVariable Long id){
        return integralGradeService.removeById(id);
    }
}

