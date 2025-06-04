package com.xdd.init.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.xdd.init.entity.SysMenu;

import java.awt.*;
import java.util.List;


/**
 * 菜单权限表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
public interface SysMenuService extends IService<SysMenu> {


    List<SysMenu> getCurrentUserMenu(Long userId);

    void deleteByIds(Long[] ids);
}

