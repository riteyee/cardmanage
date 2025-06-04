package com.xdd.init.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import com.xdd.init.entity.Card;
import com.xdd.init.entity.SysUser;
import com.xdd.init.mapper.CardMapper;
import com.xdd.init.service.CardService;
import com.xdd.init.utils.CommonUtils;
import com.xdd.init.utils.ServletUtils;
import org.springframework.stereotype.Service;

@Service("cardService")
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {
    @Override
    public Page queryPage(JSONObject params) {
        SysUser user = (SysUser) ServletUtils.getRequest().getAttribute("user");
        Page page = CommonUtils.getPage(params);
        QueryWrapper<Card> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getUserId());
        if(CommonUtils.isNotEmpty(params.getString("cardName"))){
            wrapper.like("card_name",params.getString("cardName"));
        }
        if(CommonUtils.isNotEmpty(params.getString("attribute"))){
            wrapper.like("attribute",params.getString("attribute"));
        }
        if(CommonUtils.isNotEmpty(params.getString("rarity"))){
            wrapper.like("rarity",params.getString("rarity"));
        }
        wrapper.orderByDesc("create_time");
        return page(page,wrapper);
    }
}
