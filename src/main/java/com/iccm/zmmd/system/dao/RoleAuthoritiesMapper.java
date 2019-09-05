package com.iccm.zmmd.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iccm.zmmd.system.model.RoleAuthorities;

public interface RoleAuthoritiesMapper extends BaseMapper<RoleAuthorities> {

    int deleteTrash();
}
