package com.iccm.zmmd.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.iccm.zmmd.common.BaseController;
import com.iccm.zmmd.common.JsonResult;
import com.iccm.zmmd.common.utils.StringUtils;
import com.iccm.zmmd.system.model.Ztree;
import com.iccm.zmmd.common.UserConstants;
import com.iccm.zmmd.system.model.SysDept;
import com.iccm.zmmd.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{

    @Autowired
    private ISysDeptService deptService;

    @PostMapping("/list")
    @ResponseBody
    public JSONArray list(@RequestBody SysDept dept)
    {
        return  deptService.selectDeptList(dept);
    }

    /**
     * 新增保存部门
     */
    @PostMapping("/add")
    @ResponseBody
    public JsonResult addSave(@Validated @RequestBody SysDept dept)
    {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return JsonResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
//        dept.setCreateBy(ShiroUtils.getLoginName());
        deptService.insertDept(dept);
        return JsonResult.ok();
    }

    /**
     * 保存
     */
    @PostMapping("/edit")
    @ResponseBody
    public JsonResult editSave(@Validated @RequestBody SysDept dept)
    {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return JsonResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        else if (dept.getParentId().equals(dept.getDeptId()))
        {
            return JsonResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
//        dept.setUpdateBy(ShiroUtils.getLoginName());
        deptService.updateDept(dept);
        return JsonResult.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public JsonResult remove(@RequestBody SysDept sysDept)
    {
        if (deptService.selectDeptCount(sysDept.getDeptId()) > 0)
        {
            return JsonResult.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(sysDept.getDeptId()))
        {
            return JsonResult.error("部门存在用户,不允许删除");
        }
        deptService.deleteDeptById(sysDept.getDeptId());
        return JsonResult.ok();
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(@RequestBody SysDept dept)
    {
        return deptService.checkDeptNameUnique(dept);
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = deptService.selectDeptTree(new SysDept());
        return ztrees;
    }

    /**
     * 查询部门下拉
     * @return
     */
    @GetMapping("/treeSelect")
    @ResponseBody
    public JSONArray treeSelect(){
        return deptService.queryDeptSelect();
    }

}
