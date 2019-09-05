package com.iccm.zmmd.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iccm.zmmd.system.service.RoleService;
import com.iccm.zmmd.system.dao.RoleMapper;
import com.iccm.zmmd.system.model.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
