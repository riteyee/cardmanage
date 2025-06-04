package com.xdd.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.entity.Card;
import com.xdd.init.entity.SysRole;
import com.xdd.init.entity.SysRoleMenu;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.service.CardService;
import com.xdd.init.service.SysRoleMenuService;
import com.xdd.init.service.SysRoleService;
import com.xdd.init.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户信息表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@RestController
@RequestMapping("api/card")
public class CardController extends BaseController {

    @Autowired
    private CardService cardService;

    /**
     * 列表
     */
    @PostMapping("/page")
    public AjaxResult page(@RequestBody JSONObject params) {
        Page page = cardService.queryPage(params);
        return AjaxResult.success(page);
    }

    @PostMapping("/list")
    public AjaxResult list() {
        List<Card> list = cardService.list();
        return AjaxResult.success(list);
    }

    @PostMapping("/{cid}")
    public AjaxResult getRoleInfo(@PathVariable Long cid) {
        Card byId = cardService.getById(cid);
        return AjaxResult.success(byId);
    }

    @PostMapping("/addOrUpdate")
    public AjaxResult addOrUpdateCard(@RequestBody Card card) {

        card.setCreateTime(new Date());
        card.setUid(getUser().getUserId());
        cardService.saveOrUpdate(card);
        return AjaxResult.success();
    }



    @PostMapping("/delete/{cid}")
    public AjaxResult delete(@PathVariable Long cid) {
        cardService.removeById(cid);
        return AjaxResult.success();
    }
}
