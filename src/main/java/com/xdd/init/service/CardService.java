package com.xdd.init.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.xdd.init.entity.Card;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2025/5/17
 * @Version:v1.0
 */
public interface CardService extends IService<Card> {
    Page queryPage(JSONObject params);
}
