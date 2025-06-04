package com.xdd.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.entity.SysRole;
import com.xdd.init.entity.SysRoleMenu;
import com.xdd.init.entity.SysUser;
import com.xdd.init.entity.SysUserRole;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.service.SysRoleMenuService;
import com.xdd.init.service.SysRoleService;
import com.xdd.init.service.SysUserRoleService;
import com.xdd.init.service.SysUserService;
import com.xdd.init.utils.CommonUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户信息表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@RestController
@RequestMapping("api/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;


    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 列表
     */
    @PostMapping("/page")
    public AjaxResult page(@RequestBody JSONObject params) {
        Page page = sysRoleService.queryPage(params);
        return AjaxResult.success(page);
    }

    @PostMapping("/list")
    public AjaxResult list() {
        List<SysRole> list = sysRoleService.list();
        return AjaxResult.success(list);
    }

    @PostMapping("/{roleId}")
    public AjaxResult getRoleInfo(@PathVariable Long roleId) {
        SysRole role = sysRoleService.getById(roleId);
        List<SysRoleMenu> menuList = sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));

        if (CommonUtils.isNotEmpty(menuList)) {
            role.setMenuIds(menuList.stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toList()));
        }
        return AjaxResult.success(role);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody SysRole role) {
        sysRoleService.save(role);
        if (CommonUtils.isNotEmpty(role.getMenuIds())) {
            Long roleId = role.getRoleId();
            List<Long> menuIds = role.getMenuIds();
            List<SysRoleMenu> roleMenus = new ArrayList<>();
            for (Long menuId : menuIds) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(roleId);
                roleMenus.add(sysRoleMenu);
            }
            sysRoleMenuService.deleteByRoleId(roleId);
            sysRoleMenuService.saveOrUpdateBatchByMultiId(roleMenus);
        }

        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody SysRole role) {
        Long roleId = role.getRoleId();
        if (CommonUtils.isNotEmpty(role.getMenuIds())) {
            List<Long> menuIds = role.getMenuIds();
            List<SysRoleMenu> roleMenus = new ArrayList<>();
            for (Long menuId : menuIds) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(roleId);
                roleMenus.add(sysRoleMenu);
            }
            sysRoleMenuService.deleteByRoleId(roleId);
            sysRoleMenuService.saveOrUpdateBatchByMultiId(roleMenus);
        }
        sysRoleService.updateById(role);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{userIds}")
    public AjaxResult delete(@PathVariable Long[] userIds) {
        sysRoleService.deleteBatchByIds(Arrays.asList(userIds));
        return AjaxResult.success();
    }
}
