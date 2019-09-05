package com.iccm.zmmd.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.iccm.zmmd.system.model.User;

public interface UserService extends IService<User> {

    User getByUsername(String username);

}
