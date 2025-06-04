package com.xdd.init.controller;

import com.xdd.init.model.AjaxResult;
import com.xdd.init.service.QRCodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2024/9/5
 * @Version:v1.0
 */
@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @RequestMapping("/generate")
    public AjaxResult generateQRCode(@RequestParam String content, HttpServletResponse response) throws Exception {
        qrCodeService.generateQRCode(content, response);
        return AjaxResult.success();
    }

    @RequestMapping("/generate/logo")
    public AjaxResult generateLogoQRCode(@RequestParam String content, HttpServletResponse response) throws Exception {
        qrCodeService.generateLogoQRCode(content, response);
        return AjaxResult.success();
    }


    @RequestMapping("/github/qrcode")
    public AjaxResult gitHubQRCode(@RequestParam String content, HttpServletResponse response) throws Exception {
        qrCodeService.gitHubQRCode(content, response);
        return AjaxResult.success();
    }

    @RequestMapping("/github/qrcode/logo")
    public AjaxResult gitHubQRCodeLogo(@RequestParam String content, HttpServletResponse response) throws Exception {
        qrCodeService.gitHubQRCodeLogo(content, response);
        return AjaxResult.success();
    }

    @RequestMapping("/github/qrcode/color")
    public AjaxResult gitHubQRCodeColor(@RequestParam String content, HttpServletResponse response) throws Exception {
        qrCodeService.gitHubQRCodeColor(content, response);
        return AjaxResult.success();
    }

    @RequestMapping("/github/qrcode/bg")
    public AjaxResult gitHubQRCodeBg(@RequestParam String content, HttpServletResponse response) throws Exception {
        qrCodeService.gitHubQRCodeBg(content, response);
        return AjaxResult.success();
    }

    @RequestMapping("/github/qrcode/shape")
    public AjaxResult gitHubQRCodeShape(@RequestParam String content, HttpServletResponse response) throws Exception {
        qrCodeService.gitHubQRCodeShape(content, response);
        return AjaxResult.success();
    }
}
