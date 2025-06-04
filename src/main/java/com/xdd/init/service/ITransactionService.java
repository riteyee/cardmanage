package com.xdd.init.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdd.init.entity.Transaction;


public interface ITransactionService extends IService<Transaction> {
    Page queryPage(JSONObject params);

    Transaction getInfo(Long tid);
}
