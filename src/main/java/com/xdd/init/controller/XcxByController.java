package com.xdd.init.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xdd.init.entity.SysRole;
import com.xdd.init.model.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xdd.init.entity.XcxBy;
import com.xdd.init.service.XcxByService;




/**
 *
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2025-03-27 07:20:47
 */
@RestController
@RequestMapping("init/xcxby")
public class XcxByController {

    @Autowired
    private XcxByService xcxByService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("init:xcxby:list")
    public AjaxResult list(){
        List<XcxBy> list = xcxByService.list();
        return AjaxResult.success(list);
    }

    @PostMapping("/batchSave")
    //@RequiresPermissions("init:xcxby:list")
    public AjaxResult batchSave(@RequestBody List<XcxBy> lists){
        boolean b = xcxByService.saveOrUpdateBatch(lists);
        return AjaxResult.success(b);
    }



}
