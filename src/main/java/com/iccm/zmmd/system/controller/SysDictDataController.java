package com.iccm.zmmd.system.controller;

import com.iccm.zmmd.common.BaseController;
import com.iccm.zmmd.common.ExcelUtil;
import com.iccm.zmmd.common.JsonResult;
import com.iccm.zmmd.common.annotation.Log;
import com.iccm.zmmd.common.enums.BusinessType;
import com.iccm.zmmd.common.page.TableDataInfo;
import com.iccm.zmmd.system.model.PostModel;
import com.iccm.zmmd.system.model.SysDictData;
import com.iccm.zmmd.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

;

/**
 * 数据字典信息
 * 
 * @author gxj
 */
@Controller
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController
{

    @Autowired
    private ISysDictDataService dictDataService;

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDictData dictData)
    {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @PostMapping("/list1")
    @ResponseBody
    public List<SysDictData> list2(@RequestBody PostModel postModel){
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(postModel.getId());
        return dictDataService.selectDictDataList(sysDictData);
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public JsonResult export(SysDictData dictData)
    {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        return util.exportExcel(list, "字典数据");
    }

    /**
     * 新增保存字典类型
     */
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public JsonResult addSave(@Validated @RequestBody SysDictData dict)
    {
//        dict.setCreateBy(ShiroUtils.getLoginName());
        dictDataService.insertDictData(dict);
        return JsonResult.ok();
    }

    /**
     * 修改保存字典类型
     */
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public JsonResult editSave(@Validated @RequestBody SysDictData dict)
    {
//        dict.setUpdateBy(ShiroUtils.getLoginName());
        dictDataService.updateDictData(dict);
        return JsonResult.ok();
    }

    @Log(title = "字典数据", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public JsonResult remove(@RequestBody PostModel postModel)
    {
        dictDataService.deleteDictDataByIds(postModel.getId());
        return JsonResult.ok();
    }

    @PostMapping("/removeById")
    @ResponseBody
    public JsonResult removeById(@RequestBody PostModel postModel){
        dictDataService.deleteDictDataById(Long.parseLong(postModel.getId()));
        return JsonResult.ok();
    }
}
