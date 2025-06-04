package com.xdd.init.service.imp;

import com.xdd.init.entity.SysRole;
import com.xdd.init.entity.SysRoleMenu;
import com.xdd.init.service.SysRoleMenuService;
import com.xdd.init.service.SysRoleService;
import com.xdd.init.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xdd.init.mapper.SysMenuMapper;
import com.xdd.init.entity.SysMenu;
import com.xdd.init.service.SysMenuService;

import javax.management.relation.Role;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuService roleMenuService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysRoleService roleService;

    @Override
    public List<SysMenu> getCurrentUserMenu(Long userId) {
        List<SysRole> roles = roleService.listRolesByUserId(userId);
        if (roles != null && roles.size() > 0){
            List<Long> roleIds = roles.stream().map(SysRole::getRoleId).collect(Collectors.toList());
            List<SysMenu> menuList = this.baseMapper.listMenuByRoleIds(roleIds);
            return menuList;
        }
        return null;
    }

    @Override
    public void deleteByIds(Long[] ids) {
        List<Long> longs = Arrays.asList(ids);
        this.baseMapper.deleteBatchIds(longs);
    }
}
