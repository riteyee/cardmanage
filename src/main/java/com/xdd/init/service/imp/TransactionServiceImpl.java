package com.xdd.init.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdd.init.entity.Card;
import com.xdd.init.entity.SysUser;
import com.xdd.init.entity.Transaction;
import com.xdd.init.mapper.TransactionMapper;
import com.xdd.init.service.ITransactionService;
import com.xdd.init.service.SysUserService;
import com.xdd.init.utils.CommonUtils;
import com.xdd.init.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("transactionService")
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements ITransactionService {

    @Autowired
    SysUserService userService;
    @Override
    public Page queryPage(JSONObject params) {
        List<SysUser> list = userService.list();
        Map<Long, SysUser> userMap = list.stream().collect(Collectors.toMap(SysUser::getUserId, Function.identity(), (k1, k2) -> k1, HashMap::new));
        Page page = CommonUtils.getPage(params);
        SysUser user = (SysUser) ServletUtils.getRequest().getAttribute("user");
        QueryWrapper<Transaction> wrapper = new QueryWrapper<>();
        if(CommonUtils.isNotEmpty(params.getString("type"))){
            wrapper.eq("type",params.getString("type"));
        }
        if(CommonUtils.isNotEmpty(params.getString("state"))){
            wrapper.eq("state",params.getString("state"));
        }
        if(CommonUtils.isNotEmpty(params.getString("cardName"))){
            wrapper.eq("card_name",params.getString("cardName"));
        }
//        wrapper.and(w -> {
//            w.eq("sell_user",user.getUserId()).or().eq("buy_user",user.getUserId());
//        });
        wrapper.eq("create_by",user.getUserId());

        wrapper.orderByDesc("create_time");

        Page page1 = page(page, wrapper);
        List<Transaction> records = page1.getRecords();
        for (Transaction record : records) {
            if(CommonUtils.isNotEmpty(userMap.get(record.getBuyUser()))){
                SysUser sysUser = userMap.get(record.getBuyUser());
                record.setBuyUserName(sysUser.getNickName());
                record.setBuyUserPhone(user.getPhonenumber());
            }
            if(CommonUtils.isNotEmpty(userMap.get(record.getSellUser()))){
                SysUser sysUser = userMap.get(record.getSellUser());
                record.setSellUserName(sysUser.getNickName());
                record.setSellUserPhone(user.getPhonenumber());
            }

            if(CommonUtils.isNotEmpty(userMap.get(record.getChangeUser()))){
                SysUser sysUser = userMap.get(record.getChangeUser());
                record.setChangeName(sysUser.getNickName());
                record.setChangePhone(user.getPhonenumber());
            }

        }
        page1.setRecords(records);
        return page1;
    }

    @Override
    public Transaction getInfo(Long tid) {
        List<SysUser> list = userService.list();
        Map<Long, SysUser> userMap = list.stream().collect(Collectors.toMap(SysUser::getUserId, Function.identity(), (k1, k2) -> k1, HashMap::new));
        Transaction byId = getById(tid);
        if(CommonUtils.isNotEmpty(userMap.get(byId.getBuyUser()))){
            SysUser sysUser = userMap.get(byId.getBuyUser());
            byId.setBuyUserName(sysUser.getNickName());
            byId.setBuyUserPhone(sysUser.getPhonenumber());
        }
        if(CommonUtils.isNotEmpty(userMap.get(byId.getSellUser()))){
            SysUser sysUser = userMap.get(byId.getSellUser());
            byId.setSellUserName(sysUser.getNickName());
            byId.setSellUserPhone(sysUser.getPhonenumber());
        }

        if(CommonUtils.isNotEmpty(userMap.get(byId.getChangeUser()))){
            SysUser sysUser = userMap.get(byId.getChangeUser());
            byId.setChangeName(sysUser.getNickName());
            byId.setChangePhone(sysUser.getPhonenumber());
        }
        return byId;
    }
}
