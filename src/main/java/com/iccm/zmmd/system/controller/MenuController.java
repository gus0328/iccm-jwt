package com.iccm.zmmd.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.iccm.zmmd.common.BaseController;
import com.iccm.zmmd.common.JsonResult;
import com.iccm.zmmd.common.annotation.Log;
import com.iccm.zmmd.common.enums.BusinessType;
import com.iccm.zmmd.system.model.Menu;
import com.iccm.zmmd.system.model.PostModel;
import com.iccm.zmmd.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单Controller
 * 
 * @author gxj
 * @date 2019-09-07
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController
{

    @Autowired
    private IMenuService menuService;

    /**
     * 查询菜单列表
     */
    @PostMapping("/list")
    @ResponseBody
    public JSONArray list(@RequestBody Menu menu)
    {
        return menuService.selectMenuList(menu);
    }

//    /**
//     * 导出菜单列表
//     */
//    @PostMapping("/export")
//    @ResponseBody
//    public JsonResult export(@RequestBody Menu menu)
//    {
//        List<Menu> list = menuService.selectMenuList(menu);
//        ExcelUtil<Menu> util = new ExcelUtil<Menu>(Menu.class);
//        return util.exportExcel(list, "menu");
//    }

    /**
     * 新增保存菜单
     */
    @Log(title = "菜单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public JsonResult addSave(@RequestBody Menu menu)
    {
        menuService.insertMenu(menu);
        return JsonResult.ok();
    }

    /**
     * 修改保存菜单
     */
    @Log(title = "菜单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public JsonResult editSave(@RequestBody Menu menu)
    {
        menuService.updateMenu(menu);
        return JsonResult.ok();
    }

    /**
     * 删除菜单
     */
    @Log(title = "菜单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public JsonResult remove(@RequestBody PostModel postModel)
    {
        menuService.deleteMenuById(Long.parseLong(postModel.getId()));
        return JsonResult.ok();
    }

    @GetMapping("/treeSelect")
    @ResponseBody
    public JSONArray treeSelect(){
        return menuService.queryMenuSelect();
    }
}
