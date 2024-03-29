package com.iccm.zmmd.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iccm.zmmd.system.dao.AuthoritiesMapper;
import com.iccm.zmmd.system.model.Authorities;
import com.iccm.zmmd.system.service.AuthoritiesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthoritiesServiceImpl extends ServiceImpl<AuthoritiesMapper, Authorities> implements AuthoritiesService {

    @Override
    public List<String> listByUserId(Integer userId) {
        return baseMapper.listByUserId(userId);
    }

    @Override
    public List<String> listByRoleIds(List<Integer> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return new ArrayList<>();
        }
        return baseMapper.listByRoleId(roleIds);
    }

    @Override
    public List<String> listByRoleId(Integer roleId) {
        List<Integer> roleIds = new ArrayList<>();
        if (roleId != null) {
            roleIds.add(roleId);
        }
        return listByRoleIds(roleIds);
    }

}
