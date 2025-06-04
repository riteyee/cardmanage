package com.xdd.init.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.xdd.init.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.management.relation.Role;
import java.util.List;

/**
 * 角色信息表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@Mapper
public interface SysRoleMapper extends MppBaseMapper<SysRole> {

    List<SysRole> listRolesByUserId(Long userId);

    void deleteBatchByIds(@Param("ids") List<Long> roleIds,@Param("delFlag") String flag);
}
