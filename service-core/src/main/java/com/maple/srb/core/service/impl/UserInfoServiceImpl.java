package com.maple.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maple.common.exception.Assert;
import com.maple.common.result.ResponseEnum;
import com.maple.common.util.MD5;
import com.maple.srb.core.mapper.UserAccountMapper;
import com.maple.srb.core.pojo.entity.UserAccount;
import com.maple.srb.core.pojo.entity.UserInfo;
import com.maple.srb.core.mapper.UserInfoMapper;
import com.maple.srb.core.pojo.vo.RegisterVO;
import com.maple.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserAccountMapper userAccountMapper;

    @Transactional(rollbackFor = Exception.class) // 事务，出错回退
    @Override
    public boolean register(RegisterVO registerVO) {
        //判断用户是否已被注册
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("mobile", registerVO.getMobile());
        Integer count = baseMapper.selectCount(userInfoQueryWrapper);
        Assert.isTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);

        //插入用户信息记录：user_info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setName(registerVO.getMobile());
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        baseMapper.insert(userInfo);

        //插入用户账户记录：user_account
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        int result = userAccountMapper.insert(userAccount);
        return result > 0;
    }
}
