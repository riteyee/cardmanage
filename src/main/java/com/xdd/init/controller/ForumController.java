package com.xdd.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.entity.Forum;
import com.xdd.init.entity.Transaction;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.service.IForumService;
import com.xdd.init.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 交易
 *
 * @author jizhengyu
 */
@RestController
@RequestMapping("api/forum")
public class ForumController extends BaseController {

    @Autowired
    private IForumService forumService;



    @PostMapping("/list")
    public AjaxResult list(@RequestBody JSONObject params) {
        List<Forum> list = forumService.queryList(params);
        return AjaxResult.success(list);
    }

    @PostMapping("/{fid}")
    public AjaxResult getInfo(@PathVariable Long tid) {
        Forum byId = forumService.getById(tid);
        return AjaxResult.success(byId);
    }

    @PostMapping("/addOrUpdate")
    public AjaxResult addOrUpdateCard(@RequestBody Forum forum) {
        forum.setUid(getUser().getUserId());
        forum.setCreateTime(new Date());
        forumService.saveOrUpdate(forum);
        return AjaxResult.success();
    }


    @PostMapping("/delete/{tid}")
    public AjaxResult delete(@PathVariable Long tid) {
        forumService.removeById(tid);
        return AjaxResult.success();
    }
}
