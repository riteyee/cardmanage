package com.xdd.init;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import com.xdd.init.entity.XcxBy;
import com.xdd.init.service.IEmailService;
import com.xdd.init.service.XcxByService;
import com.xdd.init.utils.IdUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class InitApplicationTests {
    @Autowired
    private IEmailService emailService;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private XcxByService xcxByService;
    @Test
    void sendText() {
        try {
            emailService.sendText("913011175@qq.com", "xxx后台管理验证码", "验证码：" + "asdasdasd");
        } catch (IllegalAccessException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Test
    void sendHtml() {
        try {
            Context context = new Context();
            context.setVariable("avatar", "https://q1.qlogo.cn/headimg_dl?dst_uin=1032065316&spec=100");
            context.setVariable("nickname", "Mr.Sun");
            context.setVariable("newContent", "测试新的评论");
            context.setVariable("oldContent", "这是旧的评论");
            context.setVariable("oldNickname", "腾讯视频");
            context.setVariable("url", "http://116.62.156.174");

            String content = templateEngine.process("mailTemplate.html", context);
            emailService.sendHtml("913011175@qq.com", "研究报告", content);
        } catch (IllegalAccessException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    @Test
    void sendMail() {
        String format = String.format("%s/%s", DateFormatUtils.format(new Date(), "yyyy/MM/dd"), IdUtils.randomUUID() + "." + FilenameUtils.getExtension("asdasdasd.png"));

        System.out.println(format);
    }

    @Test
    void generalData() {
        List<XcxBy> list = new ArrayList<>();
        String dl = "k31+594.2-k31+620";
        Integer js = 3;
        Integer jsi = 4;
        Integer jw = 0;
        Integer jl = 6;

        for (int i = 0; i < js; i++) {
            XcxBy xcxBy = new XcxBy();
            xcxBy.setDl(dl);
            xcxBy.setArea("93No."+(i+1));
            xcxBy.setSort(i);
            list.add(xcxBy);
        }

        for (int i = 0; i < jsi; i++) {
            XcxBy xcxBy = new XcxBy();
            xcxBy.setDl(dl);
            xcxBy.setArea("94No."+(i+1));
            xcxBy.setSort(i);
            list.add(xcxBy);
        }

        for (int i = 0; i < jw; i++) {
            XcxBy xcxBy = new XcxBy();
            xcxBy.setDl(dl);
            xcxBy.setArea("95No."+(i+1));
            xcxBy.setSort(i);
            list.add(xcxBy);
        }

        for (int i = 0; i < jl; i++) {
            XcxBy xcxBy = new XcxBy();
            xcxBy.setDl(dl);
            xcxBy.setArea("96No."+(i+1));
            xcxBy.setSort(i);
            list.add(xcxBy);
        }


        xcxByService.saveBatch(list);
    }
    @Test
    void pdfToWord(){
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.loadFromFile("/Users/jizhengyu/Documents/work/测量/沪武/123.pdf");
        pdfDocument.saveToFile("/Users/jizhengyu/Documents/work/测量/沪武/1.docx", FileFormat.DOCX);
        pdfDocument.close();

    }
}
