package com.xdd.init.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xdd.init.entity.Card;
import com.xdd.init.entity.Forum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@Mapper
public interface ForumMapper extends BaseMapper<Forum> {


    List<Forum> queryList(@Param("params") JSONObject forum);
}
