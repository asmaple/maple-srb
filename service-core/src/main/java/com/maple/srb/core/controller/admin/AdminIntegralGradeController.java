package com.maple.srb.core.controller.admin;


import com.maple.srb.core.mapper.IntegralGradeMapper;
import com.maple.srb.core.pojo.entity.IntegralGrade;
import com.maple.srb.core.service.IntegralGradeService;
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
@CrossOrigin  // 跨域
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;


    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/test")
    public List<IntegralGrade> listAll(){
        return integralGradeService.list();
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeById(@PathVariable Long id){
        return integralGradeService.removeById(id);
    }
}

