package com.xdd.init.service;

import com.google.zxing.WriterException;

import javax.servlet.http.HttpServletResponse;


public interface QRCodeService {
    void generateQRCode(String content, HttpServletResponse response) throws Exception;

    void generateLogoQRCode(String content, HttpServletResponse response) throws Exception;

    void gitHubQRCode(String content, HttpServletResponse response) throws Exception;

    void gitHubQRCodeLogo(String content, HttpServletResponse response) throws Exception;

    void gitHubQRCodeColor(String content, HttpServletResponse response) throws Exception;

    void gitHubQRCodeBg(String content, HttpServletResponse response) throws Exception;

    void gitHubQRCodeShape(String content, HttpServletResponse response) throws Exception;
}
