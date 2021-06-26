package com.szx.srb.core.service.impl;

import com.szx.srb.core.pojo.entity.UserLoginRecord;
import com.szx.srb.core.mapper.UserLoginRecordMapper;
import com.szx.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author szx
 * @since 2021-06-25
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
