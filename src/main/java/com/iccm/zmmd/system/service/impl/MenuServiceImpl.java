package com.iccm.zmmd.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.iccm.zmmd.system.dao.MenuMapper;
import com.iccm.zmmd.system.model.Menu;
import com.iccm.zmmd.system.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
