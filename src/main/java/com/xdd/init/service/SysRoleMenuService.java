package com.xdd.init.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.xdd.init.entity.SysRoleMenu;


/**
 * 角色和菜单关联表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
public interface SysRoleMenuService extends IMppService<SysRoleMenu> {


    void deleteByRoleId(Long roleId);
}

