package com.xdd.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.entity.Card;
import com.xdd.init.entity.Notice;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.service.CardService;
import com.xdd.init.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 通知
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@RestController
@RequestMapping("api/notice")
public class NoticeController extends BaseController {

    @Autowired
    private NoticeService noticeService;



    @PostMapping("/list")
    public AjaxResult list() {
        Long userId = getUser().getUserId();
        List<Notice> list = noticeService.list(new QueryWrapper<Notice>().eq("uid", userId).eq("consumption","0"));
        return AjaxResult.success(list);
    }

    /**
     * 消费通知
     * @return
     */
    @PostMapping("/consumption")
    public AjaxResult consumption(@RequestBody List<Long> ids) {
        noticeService.update(new UpdateWrapper<Notice>().set("consumption", "1").set("consumption_time", new Date()).in("id", ids));
        return AjaxResult.success();
    }
}
