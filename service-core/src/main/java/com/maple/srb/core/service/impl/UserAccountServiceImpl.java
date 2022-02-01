package com.maple.srb.core.service.impl;

import com.maple.srb.core.pojo.entity.UserAccount;
import com.maple.srb.core.mapper.UserAccountMapper;
import com.maple.srb.core.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
