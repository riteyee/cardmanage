package com.xdd.init.model;

import lombok.Data;


/**
 * @author jizhengyu
 */
@Data
public class LoginBody
{
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;

    private String email;
}
