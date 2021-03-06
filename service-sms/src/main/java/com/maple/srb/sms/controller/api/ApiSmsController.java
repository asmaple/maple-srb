package com.maple.srb.sms.controller.api;

import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.common.util.RandomUtils;
import com.maple.common.util.RegexValidateUtils;
import com.maple.srb.sms.client.CoreUserInfoClient;
import com.maple.srb.sms.service.SmsService;
import com.maple.srb.sms.util.SmsProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Api(tags = "短信管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/api/sms")
@Slf4j
public class ApiSmsController {

    @Resource
    private SmsService smsService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private CoreUserInfoClient coreUserInfoClient;

    @ApiOperation(value = "获取验证码")
    @GetMapping("/send/{mobile}")
    public R send(
            @ApiParam(value = "手机号", required = true)
            @PathVariable String mobile){

        //校验手机号吗不能为空
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        //是否是合法的手机号码
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        //判断手机号是否已经注册
        boolean result = coreUserInfoClient.checkMobile(mobile);
        log.info("result = " + result);
        Assert.isTrue(!result, ResponseEnum.MOBILE_EXIST_ERROR);

        String code = RandomUtils.getFourBitRandom();
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        // todo 由于真正的发送短信无法使用 暂时注释
//        smsService.send(mobile, SmsProperties.TEMPLATE_CODE, map);

        //将验证码存入redis   1分钟后过期
        redisTemplate.opsForValue().set("srb:sms:code:" + mobile, code, 1, TimeUnit.MINUTES);

        return R.ok().message("短信发送成功");
    }
}
