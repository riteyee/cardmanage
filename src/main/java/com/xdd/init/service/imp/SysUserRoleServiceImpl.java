package com.xdd.init.service.imp;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.stereotype.Service;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xdd.init.mapper.SysUserRoleMapper;
import com.xdd.init.entity.SysUserRole;
import com.xdd.init.service.SysUserRoleService;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends MppServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {


    @Override
    public void deleteByUSerId(Long userId) {
        this.baseMapper.deleteByUSerId(userId);
    }
}
