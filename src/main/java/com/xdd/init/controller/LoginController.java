package com.xdd.init.controller;

import com.xdd.init.constant.Constants;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.model.LoginBody;
import com.xdd.init.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 用户信息表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SysLoginService loginService;

    /**
     * 账号密码登录
     */
    @PostMapping()
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        try {
            Map map = loginService.login(loginBody.getUserName(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
            ajax.putAll(map);
        } catch (Exception e) {
            e.printStackTrace();
            ajax = AjaxResult.error(e.getMessage());
        }
        return ajax;
    }

    @PostMapping("/email")
    public AjaxResult mail(@RequestBody LoginBody loginBody) {

        AjaxResult ajax = AjaxResult.success();
        try {
            Map<String,Object> map = loginService.mail(loginBody.getEmail(),loginBody.getCode(),loginBody.getUuid());
            ajax.putAll(map);
        } catch (Exception e) {
            e.printStackTrace();
            ajax = AjaxResult.error(e.getMessage());
        }
        return ajax;
    }
}
