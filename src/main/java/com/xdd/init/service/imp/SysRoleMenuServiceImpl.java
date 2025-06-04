package com.xdd.init.service.imp;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.stereotype.Service;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xdd.init.mapper.SysRoleMenuMapper;
import com.xdd.init.entity.SysRoleMenu;
import com.xdd.init.service.SysRoleMenuService;


@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends MppServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {


    @Override
    public void deleteByRoleId(Long roleId) {
        this.baseMapper.deleteByRoleId(roleId);
    }
}
