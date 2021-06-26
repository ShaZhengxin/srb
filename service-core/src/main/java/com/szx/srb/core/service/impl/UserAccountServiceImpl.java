package com.szx.srb.core.service.impl;

import com.szx.srb.core.pojo.entity.UserAccount;
import com.szx.srb.core.mapper.UserAccountMapper;
import com.szx.srb.core.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author szx
 * @since 2021-06-25
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
