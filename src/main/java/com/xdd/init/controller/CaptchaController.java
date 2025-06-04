package com.xdd.init.controller;

import com.google.code.kaptcha.Producer;

import com.xdd.init.constant.CacheConstants;
import com.xdd.init.constant.Constants;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.service.IEmailService;
import com.xdd.init.utils.Base64;
import com.xdd.init.utils.CommonUtils;
import com.xdd.init.utils.IdUtils;
import com.xdd.init.utils.RedisCache;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author jzy
 */
@RestController
public class CaptchaController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    IEmailService emailService;

    @Value("${captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException
    {
        AjaxResult ajax = AjaxResult.success();

        // 保存验证码信息
        String uuid = UUID.randomUUID().toString().replace("-","");
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    @GetMapping("/sendMail")
    public AjaxResult sendMail(@RequestParam(value = "email",required = true) String mail) throws IllegalAccessException {
        if(!CommonUtils.isMail(mail)){
            throw new IllegalAccessException("邮箱格式错误");
        }
        if(redisCache.hasKey(CacheConstants.MAIL_CODE_KEY + mail)){
            throw new IllegalAccessException("请勿频繁发送验证码");
        }

        AjaxResult ajax = AjaxResult.success();
        String uuid = IdUtils.fastUUID();
        String verifyKey = CacheConstants.MAIL_CODE_KEY + uuid;

        String code = IdUtils.fastUUID().substring(0, 6).toUpperCase();

        emailService.sendText(mail, "xxx后台管理验证码", "验证码：" + code);

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        redisCache.setCacheObject(CacheConstants.MAIL_CODE_KEY + mail, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        ajax.put("uuid", uuid);
        return ajax;
    }
}
