package com.xdd.init.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.xdd.init.constant.SystemConstants;
import com.xdd.init.entity.SysUserRole;
import com.xdd.init.service.SysUserRoleService;
import com.xdd.init.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xdd.init.mapper.SysRoleMapper;
import com.xdd.init.entity.SysRole;
import com.xdd.init.service.SysRoleService;

import javax.management.relation.Role;


@Service("sysRoleService")
public class SysRoleServiceImpl extends MppServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public List<SysRole> listRolesByUserId(Long userId) {
        List<SysRole> roles = this.baseMapper.listRolesByUserId(userId);
        return roles;
    }

    @Override
    public Page queryPage(JSONObject params) {
        Page page = CommonUtils.getPage(params);
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (CommonUtils.isNotEmpty(params)) {
            wrapper.eq("del_flag", SystemConstants.UN_DEL);
            wrapper.like(CommonUtils.isNotEmpty(params.getString("roleName")), "role_name", params.getString("roleName"));
            wrapper.like(CommonUtils.isNotEmpty(params.getString("status")), "status", params.getString("status"));
        }
        Page roleWrapper = this.page(page, wrapper);
        return roleWrapper;
    }

    @Override
    public void saveUserRole(Long userId, List<Long> roleIds) {
        List<SysUserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        sysUserRoleService.deleteByUSerId(userId);
        sysUserRoleService.saveOrUpdateBatchByMultiId(userRoles);
    }

    @Override
    public void deleteBatchByIds(List<Long> roleIds) {
        this.baseMapper.deleteBatchByIds(roleIds, SystemConstants.DEL);
    }
}
