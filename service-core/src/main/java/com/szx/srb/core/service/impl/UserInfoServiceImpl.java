package com.szx.srb.core.service.impl;

import com.szx.srb.core.pojo.entity.UserInfo;
import com.szx.srb.core.mapper.UserInfoMapper;
import com.szx.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author szx
 * @since 2021-06-25
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
