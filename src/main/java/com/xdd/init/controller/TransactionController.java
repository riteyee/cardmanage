package com.xdd.init.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.constant.SystemConstants;
import com.xdd.init.entity.Card;
import com.xdd.init.entity.Notice;
import com.xdd.init.entity.Transaction;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.service.CardService;
import com.xdd.init.service.ITransactionService;
import com.xdd.init.service.NoticeService;
import com.xdd.init.utils.CommonUtils;
import org.omg.IOP.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 交易
 *
 * @author jizhengyu
 */
@RestController
@RequestMapping("api/transaction")
public class TransactionController extends BaseController {

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CardService cardService;

    /**
     * 列表
     */
    @PostMapping("/page")
    public AjaxResult page(@RequestBody JSONObject params) {
        Page page = transactionService.queryPage(params);
        return AjaxResult.success(page);
    }

    @PostMapping("/list")
    public AjaxResult list() {
        List<Transaction> list = transactionService.list();
        return AjaxResult.success(list);
    }

    @PostMapping("/{tid}")
    public AjaxResult getInfo(@PathVariable Long tid) {
        Transaction byId = transactionService.getInfo(tid);
        return AjaxResult.success(byId);
    }

    @PostMapping("/add")
    public AjaxResult addOrUpdateCard(@RequestBody Transaction transaction) {
        transaction.setCreateTime(new Date());
        String no = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        // 发布状态
        transaction.setState(SystemConstants.TRANSACTION_STATE_PUBLISH);
        transaction.setNo(no);
        transaction.setCreateBy(getUser().getUserId());
        List<Transaction> list = transactionService.list();

        if (transaction.getType().equals("1")) {
            // 发布数据为求购
            transaction.setBuyUser(getUser().getUserId());

            // 求购人为空、卡片名称相同、期望价格最小的 出售数据
            Optional<Transaction> min = list.stream()
                    .filter(t -> t.getType().equals("2") && CommonUtils.isEmpty(t.getBuyUser()) && t.getCardName().equals(transaction.getCardName()))
                    .min(Comparator.comparing(Transaction::getExpectationPrice));

            if (min.isPresent()) {

                // 出售数据
                Transaction transaction1 = min.get();
                // 设置购买人位当前用户
                transaction1.setBuyUser(getUser().getUserId());
                Notice notice1 = new Notice();
                notice1.setUid(transaction.getCreateBy());
                notice1.setCreateTime(new Date());
                notice1.setContent("您发布的求购数据【" + transaction1.getCardName() + "】已被用户匹配，请及时确认价格");
                noticeService.save(notice1);

                // 设置出售人为匹配的数据发起人
                transaction.setSellUser(transaction1.getSellUser());

                // 改变状态
                transaction.setState(SystemConstants.TRANSACTION_STATE_CONSULT);
                transaction1.setState(SystemConstants.TRANSACTION_STATE_CONSULT);
                transaction1.setNo(no);
                transactionService.updateById(transaction1);
                Notice notice = new Notice();
                notice.setUid(transaction1.getCreateBy());
                notice.setContent("您发布的出售数据【" + transaction1.getCardName() + "】匹配，请及时确认价格");
                notice.setCreateTime(new Date());
                noticeService.save(notice);
            }

        } else if (transaction.getType().equals("2")) {
            // 发布数据为出售
            transaction.setSellUser(getUser().getUserId());
            // 求购人为空、卡片名称相同、期望价格最大的 求购数据
            Optional<Transaction> max = list.stream()
                    .filter(t -> t.getType().equals("1") && CommonUtils.isEmpty(t.getSellUser()) && t.getCardName().equals(transaction.getCardName()))
                    .max(Comparator.comparing(Transaction::getExpectationPrice));

            if (max.isPresent()) {
                // 求购数据
                Transaction transaction1 = max.get();
                //
                transaction1.setSellUser(getUser().getUserId());
                Notice notice1 = new Notice();
                notice1.setUid(transaction.getCreateBy());
                notice1.setCreateTime(new Date());
                notice1.setContent("您发布的出售数据【" + transaction1.getCardName() + "】已被用户匹配，请及时确认价格");
                noticeService.save(notice1);

                // 设置出售人为匹配的数据发起人
                transaction.setBuyUser(transaction1.getBuyUser());

                // 改变状态
                transaction.setState(SystemConstants.TRANSACTION_STATE_CONSULT);
                transaction1.setState(SystemConstants.TRANSACTION_STATE_CONSULT);
                transaction1.setNo(no);
                transactionService.updateById(transaction1);
                Notice notice = new Notice();
                notice.setUid(transaction1.getCreateBy());
                notice.setContent("您发布的求购数据【" + transaction1.getCardName() + "】已匹配，请及时确认价格");
                notice.setCreateTime(new Date());
                noticeService.save(notice);
            }
        } else {
            // 发布为交换
            transaction.setSellUser(getUser().getUserId());
            // 求购人为空、卡片名称相同
            Optional<Transaction> max = list.stream()
                    .filter(t -> t.getType().equals("3") && CommonUtils.isEmpty(t.getChangeUser()) && t.getCardName().equals(transaction.getChangeCard()))
                    .findFirst();

            if (max.isPresent()) {
                // 交换数据
                Transaction transaction1 = max.get();
                //
                transaction1.setChangeUser(getUser().getUserId());
                Notice notice1 = new Notice();
                notice1.setUid(transaction.getCreateBy());
                notice1.setCreateTime(new Date());
                notice1.setContent("您发布的交换数据【" + transaction1.getCardName() + "】已匹配完成");
                noticeService.save(notice1);

                // 设置出售人为匹配的数据发起人
                transaction.setChangeUser(transaction1.getCreateBy());

                // 改变状态
                transaction.setState(SystemConstants.TRANSACTION_STATE_FINISH);
                transaction1.setState(SystemConstants.TRANSACTION_STATE_FINISH);
                transaction1.setNo(no);
                transactionService.updateById(transaction1);
                Notice notice = new Notice();
                notice.setUid(transaction1.getCreateBy());
                notice.setContent("您发布的交换数据【" + transaction1.getCardName() + "】已匹配完成");
                notice.setCreateTime(new Date());
                noticeService.save(notice);
            }
        }

        transactionService.saveOrUpdate(transaction);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody Transaction transaction) {
        transactionService.updateById(transaction);
        return AjaxResult.success();
    }

    @PostMapping("/confirmPrice")
    @Transactional
    public AjaxResult confirmPrice(@RequestBody Transaction transaction) {
        List<Transaction> no = transactionService.list(new QueryWrapper<Transaction>().eq("no", transaction.getNo()));


        for (Transaction transaction1 : no) {
            Notice notice = new Notice();
            notice.setCreateTime(new Date());
            notice.setUid(transaction1.getCreateBy());
            transaction1.setState(transaction.getState());
            transaction1.setRealPrice(transaction.getRealPrice());
            if (transaction1.getState().equals(SystemConstants.TRANSACTION_STATE_PAY)) {
                notice.setContent("您发布的数据【" + transaction1.getCardName() + "】已被用户【" + getUser().getUserName() + "】确认价格");
            }
            if (transaction1.getState().equals(SystemConstants.TRANSACTION_STATE_FINISH)) {
                notice.setContent("您发布的数据【" + transaction1.getCardName() + "】已被用户【" + getUser().getUserName() + "】交易完成");
            }
            noticeService.save(notice);
        }
        if(transaction.getState().equals(SystemConstants.TRANSACTION_STATE_FINISH)){
            Optional<Transaction> first = no.stream().filter(t -> t.getType().equals("1")).findFirst();
            String cardName = first.get().getCardName();
            Long buyUser = first.get().getBuyUser();
            Long sellUser = first.get().getSellUser();
            Integer number = first.get().getNumber();

            Card sellCard = cardService.getOne(new QueryWrapper<Card>().eq("card_name", cardName).eq("uid", sellUser));
            Card buyCard = cardService.getOne(new QueryWrapper<Card>().eq("uid", buyUser).eq("card_name", cardName));

            // 购买人添加库存
            if (buyCard != null) {
                buyCard.setNumber(buyCard.getNumber() + number);
                cardService.updateById(buyCard);
            }else {
                Card card = new Card();
                BeanUtils.copyProperties(sellCard,card);
                card.setNumber(number);
                card.setId(null);
                card.setUid(buyUser);
                card.setCreateTime(new Date());
                cardService.save(card);
            }
            // 减库存
            if (sellCard != null) {
                if (sellCard.getNumber() - number <= 0) {
                    cardService.removeById(sellCard.getId());
                } else {
                    sellCard.setNumber(sellCard.getNumber() - number);
                    cardService.updateById(sellCard);
                }
            }
        }

        transactionService.saveOrUpdateBatch(no);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{tid}")
    public AjaxResult delete(@PathVariable Long tid) {
        // 不是交易完成数据 ，删除系统匹配数据关联项
        Transaction byId = transactionService.getById(tid);
        if (CommonUtils.isNotEmpty(byId)) {
            if (!byId.getState().equals(SystemConstants.TRANSACTION_STATE_FINISH)) {
                List<Transaction> no = transactionService.list(new QueryWrapper<Transaction>().eq("no", byId.getNo()));
                no.stream().filter(t -> !t.getId().equals(tid)).findFirst().ifPresent(t -> {
                    UpdateWrapper<Transaction> updateWrapper = new UpdateWrapper<>();
                    if (t.getType().equals("1")) {
//                        t.setSellUser(null);
                        updateWrapper.set("sell_user", null);
                    } else {
                        updateWrapper.set("buy_user", null);
//                        t.setBuyUser(null);
                    }
//                    t.setState("1");
                    updateWrapper.set("state", "1");
//                    t.setRealPrice(null);
                    updateWrapper.set("real_price", null);
//                    t.setNo(null);
                    updateWrapper.set("no", null);
                    updateWrapper.eq("id", t.getId());
                    transactionService.update(updateWrapper);
                });
            }
        }
        transactionService.removeById(tid);
        return AjaxResult.success();
    }
}
