package com.maple.srb.core.controller.admin;


import com.maple.common.result.R;
import com.maple.srb.core.pojo.entity.IntegralGrade;
import com.maple.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
//@CrossOrigin  // 跨域
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;

    @ApiOperation(value = "获取hello world")
    @GetMapping("/hello")
    public R hello() {
        return R.ok().data("content", "hello world");
    }

    @GetMapping("/list")
    public R listAll() {
        List<IntegralGrade> list = integralGradeService.list();
        return R.ok().data("list", list);
    }

    @ApiOperation(value = "根据id删除数据记录", notes = "逻辑删除")
    @DeleteMapping("/remove/{id}")
    public R removeById(
            @ApiParam(value = "要删除的id", example = "1", required = true)
            @PathVariable Long id) {
        boolean result = integralGradeService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }

    @ApiOperation(value = "新增积分等级")
    @PostMapping("/save")
    public R save(
            @ApiParam(value = "积分等级对象", required = true)
            @RequestBody IntegralGrade integralGrade) {

        boolean result = integralGradeService.save(integralGrade);
        if (result) {
            return R.ok().message("添加成功");
        } else {
            return R.error().message("添加失败");
        }
    }

    @ApiOperation(value = "根据id查询积分等级")
    @GetMapping("/get/{id}")
    public R getById(
            @ApiParam(value = "积分id", example = "1", required = true)
            @PathVariable Long id) {

        IntegralGrade integralGrade = integralGradeService.getById(id);
        if (integralGrade != null) {
            return R.ok().data("record", integralGrade);
        } else {
            return R.error().message("数据获取失败");
        }
    }

    @ApiOperation("更新积分等级")
    @PutMapping("/update")
    public R updateById(
            @ApiParam(value = "积分等级对象", required = true)
            @RequestBody IntegralGrade integralGrade) {

        boolean result = integralGradeService.updateById(integralGrade);

        if (result) {
            return R.ok().message("更新成功");
        } else {
            return R.error().message("更新失败");
        }
    }
}

