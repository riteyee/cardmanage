package com.xdd.init.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.entity.SysUser;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2024/8/8
 * @Version:v1.0
 */
public class CommonUtils {
    public static Page getPage(JSONObject params) {
        Integer pageIndex = (Integer) params.getOrDefault("pageIndex", 0);
        Integer pageSize = (Integer) params.getOrDefault("pageSize", 10);
        return new Page(pageIndex, pageSize);
    }

    public static Boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).trim().length() == 0;
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).isEmpty();
        }

        return false;
    }

    public static Boolean isMail(String mail) {
        Pattern p = compile("[a-zA-Z0-9]+@[A-Za-z0-9]+\\.[a-z0-9]");
        Matcher m = p.matcher(mail);
        return m.find();
    }

    public static Boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static SysUser getUser(){
        SysUser user = (SysUser) ServletUtils.getRequest().getAttribute("user");
        return user;
    }
    public static Long getUserId(){
        SysUser user = getUser();
        return user.getUserId();
    }

    public static boolean isNull(byte[] addr) {
        return addr == null || addr.length == 0;
    }

    public static String uuid(){
        return IdUtils.fastUUID();


    }
}
