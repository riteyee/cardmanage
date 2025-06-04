package com.xdd.init.controller;

import com.xdd.init.entity.SysUser;
import com.xdd.init.utils.ServletUtils;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2024/8/9
 * @Version:v1.0
 */
public class BaseController {
    public SysUser getUser() {
        SysUser user = (SysUser) ServletUtils.getRequest().getAttribute("user");
        return user;
    }

}
