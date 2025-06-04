package com.xdd.init.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.xdd.init.entity.SysRole;

import javax.management.relation.Role;
import java.util.List;


/**
 * 角色信息表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
public interface SysRoleService extends IMppService<SysRole> {


    List<SysRole> listRolesByUserId(Long userId);

    Page queryPage(JSONObject params);

    void saveUserRole(Long userId, List<Long> roleIds);

    void deleteBatchByIds(List<Long> asList);
}

