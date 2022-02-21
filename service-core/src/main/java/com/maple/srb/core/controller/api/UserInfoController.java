package com.maple.srb.core.controller.api;


import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.common.util.RegexValidateUtils;
import com.maple.srb.base.util.JwtUtils;
import com.maple.srb.core.pojo.vo.LoginVO;
import com.maple.srb.core.pojo.vo.RegisterVO;
import com.maple.srb.core.pojo.vo.UserInfoVO;
import com.maple.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@Api(tags = "会员接口")
@RestController
@RequestMapping("/api/core/userInfo")
@Slf4j
@CrossOrigin
public class UserInfoController {

    @Resource
//    private RedisTemplate<String, String> redisTemplate; // 使用泛型的 这种不行
    private RedisTemplate redisTemplate;

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("会员注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVO registerVO){

        String mobile = registerVO.getMobile();
        String password = registerVO.getPassword();
        String code = registerVO.getCode();

        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);
        Assert.notEmpty(code, ResponseEnum.CODE_NULL_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        //校验验证码是否正确
        String codeGen = (String)redisTemplate.opsForValue().get("srb:sms:code:" + mobile);
//        String codeGen = redisTemplate.opsForValue().get("srb:sms:code:" + mobile);
        Assert.equals(code, codeGen, ResponseEnum.CODE_ERROR);

        //注册
        boolean result = userInfoService.register(registerVO);
        if(result) {
            return R.ok().message("注册成功");
        } else {
            return R.error().message("注册失败, 请联系管理员!");
        }
    }


    @ApiOperation("会员登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO, HttpServletRequest request) {  //HttpServletRequest 自动获取（前端无须传值） 请求对象，获取到当前发访问的IP地址
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();

        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        String ip = request.getRemoteAddr();
        // 包装用户信息对象 返回
        UserInfoVO userInfoVO = userInfoService.login(loginVO, ip);
        if(userInfoVO != null) {
            return R.ok().data("userInfo", userInfoVO);
        } else  {
            return R.setResult(ResponseEnum.LOGIN_ERROR);
        }
    }

    @ApiOperation("校验令牌")
    @GetMapping("/checkToken")
    public R checkToken(HttpServletRequest request) {

        String token = request.getHeader("token");
        boolean result = JwtUtils.checkToken(token);
        if(result){
            return R.ok();
        }else{
            return R.setResult(ResponseEnum.LOGIN_AUTH_ERROR);
        }
    }

}

