package com.xdd.init.controller;

import com.xdd.init.entity.SysUser;
import com.xdd.init.utils.ServletUtils;

public class BaseController {
    public SysUser getUser() {
        SysUser user = (SysUser) ServletUtils.getRequest().getAttribute("user");
        return user;
    }

}
