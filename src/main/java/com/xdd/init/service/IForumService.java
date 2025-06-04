package com.xdd.init.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdd.init.entity.Forum;

import java.util.List;

public interface IForumService extends IService<Forum> {
    List<Forum> queryList(JSONObject forum);
}
