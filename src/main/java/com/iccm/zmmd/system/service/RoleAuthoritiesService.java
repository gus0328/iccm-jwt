package com.iccm.zmmd.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.iccm.zmmd.system.model.RoleAuthorities;

/**
 * Created by Administrator on 2018-12-19 下午 4:38.
 */
public interface RoleAuthoritiesService extends IService<RoleAuthorities> {

    void deleteTrash();
}
