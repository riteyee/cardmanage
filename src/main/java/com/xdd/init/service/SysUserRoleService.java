package com.xdd.init.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.xdd.init.entity.SysUserRole;


/**
 * 用户和角色关联表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
public interface SysUserRoleService extends IMppService<SysUserRole> {


    void deleteByUSerId(Long userId);
}

