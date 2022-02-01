package com.maple.srb.core.service.impl;

import com.maple.srb.core.pojo.entity.UserInfo;
import com.maple.srb.core.mapper.UserInfoMapper;
import com.maple.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
