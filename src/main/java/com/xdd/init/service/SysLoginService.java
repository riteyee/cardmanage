package com.xdd.init.service;

import com.xdd.init.constant.CacheConstants;
import com.xdd.init.constant.Constants;
import com.xdd.init.constant.SystemConstants;
import com.xdd.init.entity.SysUser;
import com.xdd.init.jwt.JwtService;
import com.xdd.init.utils.CommonUtils;
import com.xdd.init.utils.RedisCache;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2024/8/8
 * @Version:v1.0
 */
@Service("loginService")
public class SysLoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    JwtService jwtService;

    @Autowired
    private RedisCache redisCache;

    public Map<String, Object> login(String username, String password, String code, String uuid) {
        // 校验验证码
        validateCaptcha(username, code, uuid);

        SysUser user = sysUserService.getUserByUsername(username);

        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        if (user.getStatus().equals(SystemConstants.USER_STOP_STATUS)) {
            throw new RuntimeException("用户被停用");
        }
        if (user.getDelFlag().equals(SystemConstants.DEL)) {
            throw new RuntimeException("用户被删除");
        }
        Map<String,Object> map = new HashMap<>();

        String token = jwtService.generateToken(user);
        map.put(Constants.TOKEN, token);
        map.put(Constants.USER_KEY, user);
        return map;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new RuntimeException("验证码已失效");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new RuntimeException("验证码错误");
        }
    }

    public Map<String,Object> mail(String mail, String code, String uuid) {

        validateMail(mail, code, uuid);

        SysUser user = sysUserService.findByEmail(mail);

        if (user == null) {
            user = new SysUser();
            user.setEmail(mail);
            user.setPassword(BCrypt.hashpw(SystemConstants.INIT_PASSWORD, BCrypt.gensalt()));
            user.setUserName(mail);
            user.setStatus(SystemConstants.USER_UN_STOP_STATUS);
            user.setDelFlag(SystemConstants.UN_DEL);
            sysUserService.saveUser(user);
        }
        Map<String,Object> map = new HashMap<>();
        String token = jwtService.generateToken(user);
        map.put(Constants.TOKEN, token);
        map.put(Constants.USER_KEY, user);
        return map;
    }

    private void validateMail(String mail, String code, String uuid) {
        if (CommonUtils.isEmpty(mail) || CommonUtils.isEmpty(code) || CommonUtils.isEmpty(uuid)) {
            throw new RuntimeException("邮箱或验证码不能为空");
        }
        if (!CommonUtils.isMail(mail)) {
            throw new RuntimeException("邮箱格式错误");
        }
        String cacheCode = (String) redisCache.getCacheObject(CacheConstants.MAIL_CODE_KEY + uuid);
        redisCache.deleteObject(CacheConstants.MAIL_CODE_KEY + uuid);
        if (CommonUtils.isEmpty(cacheCode)) {
            throw new RuntimeException("验证码已失效");
        }
        if (!code.equalsIgnoreCase(cacheCode)) {
            throw new RuntimeException("验证码错误");
        }
    }
}
