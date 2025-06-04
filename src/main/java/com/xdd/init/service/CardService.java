package com.xdd.init.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jeffreyning.mybatisplus.service.IMppService;
import com.xdd.init.entity.Card;

public interface CardService extends IService<Card> {
    Page queryPage(JSONObject params);
}
