package com.xdd.init.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户信息表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    Page queryPage(JSONObject params, Page page);

    void deleteBatchByIds(@Param("ids") List<Long> ids,@Param("delFlag")String delFlag);
}
