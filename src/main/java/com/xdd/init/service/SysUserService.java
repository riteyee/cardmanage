package com.xdd.init.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.xdd.init.entity.SysUser;

import java.util.List;


/**
 * 用户信息表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
public interface SysUserService extends IService<SysUser> {


    Page queryPage(JSONObject params);

    SysUser getUserByUsername(String username);

    SysUser findByPhoneNumber(String phone);

    void saveUser(SysUser user);

    void deleteBatchByIds(List<Long> asList);

    boolean updateUserAvatar(Long userId, String avatar);

    SysUser findByEmail(String mail);
}

