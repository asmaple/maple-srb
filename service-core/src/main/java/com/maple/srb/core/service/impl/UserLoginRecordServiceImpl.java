package com.maple.srb.core.service.impl;

import com.maple.srb.core.pojo.entity.UserLoginRecord;
import com.maple.srb.core.mapper.UserLoginRecordMapper;
import com.maple.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
