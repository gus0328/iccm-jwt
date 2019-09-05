package com.iccm.zmmd.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iccm.zmmd.system.dao.UserMapper;
import com.iccm.zmmd.system.model.User;
import com.iccm.zmmd.system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByUsername(String username) {
        return baseMapper.getByUsername(username);
    }

}
