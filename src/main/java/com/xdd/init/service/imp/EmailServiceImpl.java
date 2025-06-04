package com.xdd.init.service.imp;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.xdd.init.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;//注入邮件工具类

    @Value("${spring.mail.properties.from}")
    private String from;

    @Override
    public void send(String to, String subject, String content, Boolean isHtml, String cc, String bcc, List<File> files) throws IllegalAccessException {

        if (StringUtils.isAnyBlank(to, subject, content)) {
            throw new IllegalAccessException("接收人,主题,内容均不可为空");
        }
        try {
            //true表示支持复杂类型
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            //邮件发信人
            messageHelper.setFrom(new InternetAddress("<" + from + ">"));
            //邮件收信人
            messageHelper.setTo(to.split(","));
            //邮件主题
            messageHelper.setSubject(subject);
            //邮件内容
            messageHelper.setText(content, isHtml);
            //抄送
            if (!StringUtils.isEmpty(cc)) {
                messageHelper.setCc(cc.split(","));
            }
            //密送
            if (!StringUtils.isEmpty(bcc)) {
                messageHelper.setCc(bcc.split(","));
            }
            //添加邮件附件
            if (CollectionUtils.isNotEmpty(files)) {
                for (File file : files) {
                    messageHelper.addAttachment(file.getName(), file);
                }
            }
            // 邮件发送时间
            messageHelper.setSentDate(new Date());
            //正式发送邮件
            javaMailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalAccessException("邮件发送失败");
        }
    }


    @Override
    public void sendText(String to, String subject, String content) throws IllegalAccessException {
        this.send(to, subject, content, false, null, null, null);
    }

    @Override
    public void sendHtml(String to, String subject, String content) throws IllegalAccessException {
        this.send(to, subject, content, true, null, null, null);
    }

}

