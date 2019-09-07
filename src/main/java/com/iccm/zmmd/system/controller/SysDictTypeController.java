package com.iccm.zmmd.system.controller;

import com.iccm.zmmd.common.BaseController;
import com.iccm.zmmd.common.ExcelUtil;
import com.iccm.zmmd.common.JsonResult;
import com.iccm.zmmd.common.UserConstants;
import com.iccm.zmmd.common.annotation.Log;
import com.iccm.zmmd.common.enums.BusinessType;
import com.iccm.zmmd.common.page.TableDataInfo;
import com.iccm.zmmd.system.model.PostModel;
import com.iccm.zmmd.system.model.SysDictType;
import com.iccm.zmmd.system.model.Ztree;
import com.iccm.zmmd.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 * 
 * @author gxj
 */
@Controller
@RequestMapping("/system/dict")
public class SysDictTypeController extends BaseController
{

    @Autowired
    private ISysDictTypeService dictTypeService;

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestBody SysDictType dictType)
    {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public JsonResult export(@RequestBody SysDictType dictType)
    {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
        return util.exportExcel(list, "字典类型");
    }

    /**
     * 新增保存字典类型
     */
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public JsonResult addSave(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return JsonResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
//        dict.setCreateBy(ShiroUtils.getLoginName());
        dictTypeService.insertDictType(dict);
        return JsonResult.ok();
    }

    /**
     * 修改保存字典类型
     */
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public JsonResult editSave(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return JsonResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
//        dict.setUpdateBy(ShiroUtils.getLoginName());
        dictTypeService.updateDictType(dict);
        return JsonResult.ok();
    }

    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public JsonResult remove(String ids)
    {
        try
        {
            dictTypeService.deleteDictTypeByIds(ids);
            return JsonResult.ok();
        }
        catch (Exception e)
        {
            return JsonResult.error(e.getMessage());
        }
    }

    @PostMapping("/removeById")
    @ResponseBody
    public JsonResult removeById(@RequestBody PostModel postModel){
        dictTypeService.deleteDictTypeById(Long.parseLong(postModel.getId()));
        return JsonResult.ok();
    }

    /**
     * 校验字典类型
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public String checkDictTypeUnique(SysDictType dictType)
    {
        return dictTypeService.checkDictTypeUnique(dictType);
    }

    /**
     * 加载字典列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = dictTypeService.selectDictTree(new SysDictType());
        return ztrees;
    }
}
