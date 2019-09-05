package com.iccm.zmmd.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iccm.zmmd.system.model.User;

public interface UserMapper extends BaseMapper<User> {

    User getByUsername(String username);
}
