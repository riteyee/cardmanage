package com.xdd.init.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdd.init.entity.Forum;

import java.util.List;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2025/5/18
 * @Version:v1.0
 */
public interface IForumService extends IService<Forum> {
    List<Forum> queryList(JSONObject forum);
}
