package com.xdd.init.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdd.init.entity.Transaction;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2025/5/17
 * @Version:v1.0
 */
public interface ITransactionService extends IService<Transaction> {
    Page queryPage(JSONObject params);

    Transaction getInfo(Long tid);
}
