package com.xdd.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.constant.SystemConstants;
import com.xdd.init.entity.SysMenu;
import com.xdd.init.entity.SysUser;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.service.SysMenuService;
import com.xdd.init.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.Arrays;
import java.util.List;


/**
 * 用户信息表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController extends BaseController{
    @Autowired
    private SysMenuService menuService;

    /**
     * 列表
     */
    @GetMapping("/current/permission")
    public AjaxResult currentUserPermission(){
        SysUser user = getUser();
        List<SysMenu> menus = menuService.getCurrentUserMenu(user.getUserId());
        return AjaxResult.success(menus);
    }

    @GetMapping()
    public AjaxResult permission(){
        List<SysMenu> list = menuService.list();
        return AjaxResult.success(list);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody SysMenu menu){
        menuService.save(menu);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody SysMenu menu){
        menuService.updateById(menu);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{ids}")
    public AjaxResult delete(@PathVariable Long[] ids){
        menuService.deleteByIds(ids);
        return AjaxResult.success();
    }
}
