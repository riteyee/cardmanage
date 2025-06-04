package com.xdd.init.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.xdd.init.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关联表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@Mapper
public interface SysUserRoleMapper extends MppBaseMapper<SysUserRole> {

    void deleteByUSerId(Long userId);
}
