package com.maple.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.srb.core.pojo.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.srb.core.pojo.query.UserInfoQuery;
import com.maple.srb.core.pojo.vo.LoginVO;
import com.maple.srb.core.pojo.vo.RegisterVO;
import com.maple.srb.core.pojo.vo.UserInfoVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author ggq
 * @since 2022-02-01
 */
public interface UserInfoService extends IService<UserInfo> {

    boolean register(RegisterVO registerVO);

    UserInfoVO login(LoginVO loginVO, String ip);

    IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery);

    boolean lock(Long id, Integer status);

    boolean checkMobile(String mobile);
}
