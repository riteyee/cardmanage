package com.xdd.init.service;

import java.io.File;
import java.util.List;

public interface IEmailService {


    /**
     * 普通邮件发送
     *
     * @param to      发送对象
     * @param subject 主题
     * @param content 内容
     */
    void sendText( String to, String subject, String content) throws IllegalAccessException;

    /**
     * Html邮件发送
     *
     * @param form    发送人
     * @param to      发送对象
     * @param subject 主题
     * @param content 内容
     */
    void sendHtml( String to, String subject, String content) throws IllegalAccessException;

    /**
     * 邮件发送
     *
     * @param to      发送对象
     * @param subject 主题
     * @param content 内容
     * @param isHtml  是否为html
     * @param cc      抄送，多人用逗号隔开
     * @param bcc     密送，多人用逗号隔开
     * @param files   文件
     */
    void send( String to, String subject, String content, Boolean isHtml, String cc, String bcc, List<File> files) throws IllegalAccessException
    ;


}
