package com.xdd.init.service.imp;

import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenWrapper;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xdd.init.constant.Constants;
import com.xdd.init.service.QRCodeService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2024/9/5
 * @Version:v1.0
 */
@Service
public class QRCodeServiceImpl implements QRCodeService {

    public static final int CODE_WIDTH = 300;
    public static final int CODE_HEIGHT = 300;

    @Override
    public void generateQRCode(String content, HttpServletResponse response) throws Exception {
        // 二维码相关参数
        Map map = new HashMap<>();
//        设置二维码误差校正级别
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        设置二维码字符集
        map.put(EncodeHintType.CHARACTER_SET, Constants.UTF8);
//        设置二维码四周留白
        map.put(EncodeHintType.MARGIN, 1);
//      创建Zxing和新对象
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//      参数： 二维码内容、格式、宽、高、参数
//        返回位矩阵对象是一个二维数组，，二位数组每一个元素是boolean类型，true为黑，false为白
        BitMatrix encode = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, map);
        int height = encode.getHeight();
        int width = encode.getWidth();

//      生成二维码图片
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, encode.get(x, y) ? 0 : 0xFFFFFF);
            }
        }

        ServletOutputStream outputStream = response.getOutputStream();
        File file = new File("/Users/jizhengyu/Downloads/fileUpload/asd.png");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ImageIO.write(bufferedImage, "png", fileOutputStream);
        ImageIO.write(bufferedImage, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void generateLogoQRCode(String content, HttpServletResponse response) throws Exception {
        // 二维码相关参数
        Map map = new HashMap<>();
//        设置二维码误差校正级别
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        设置二维码字符集
        map.put(EncodeHintType.CHARACTER_SET, Constants.UTF8);
//        设置二维码四周留白
        map.put(EncodeHintType.MARGIN, 1);
//      创建Zxing和新对象
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//      参数： 二维码内容、格式、宽、高、参数
//        返回位矩阵对象是一个二维数组，，二位数组每一个元素是boolean类型，true为黑，false为白
        BitMatrix encode = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, map);
        int height = encode.getHeight();
        int width = encode.getWidth();

//      生成二维码图片
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, encode.get(x, y) ? 0 : 0xFFFFFF);
            }
        }

        // logo
        File file = new File("/Users/jizhengyu/Pictures/logo.jpg");
        Image logo = ImageIO.read(file);
        int logoHeight = logo.getHeight(null);
        int logoWidth = logo.getWidth(null);
        if (logoHeight > 60) {
            logoHeight = 60;
        }
        if (logoWidth > 60) {
            logoWidth = 60;
        }
//      将logo 进行比例图片缩放
        Image scaledLogo = logo.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);

        Graphics2D graphics = bufferedImage.createGraphics();

        int logoX = (CODE_WIDTH - logoWidth) / 2;
        int logoY = (CODE_HEIGHT - logoHeight) / 2;

//      将logo画上去
        graphics.drawImage(scaledLogo, logoX, logoY, null);

//      创建圆角矩形
        Shape shape = new RoundRectangle2D.Float(logoX, logoY, logoWidth, logoHeight, 10, 10);
//      使用一个像素为4的笔触
        graphics.setStroke(new BasicStroke(4f));

//      给logo绘画圆角矩形
        graphics.draw(shape);

//      释放画笔
        graphics.dispose();


        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void gitHubQRCode(String content, HttpServletResponse response) throws Exception {

//            黑白二维码
        BufferedImage image = QrCodeGenWrapper
                .of(content)
                .asBufferedImage();
        ImageIO.write(image, "png", response.getOutputStream());
    }

    @Override
    public void gitHubQRCodeLogo(String content, HttpServletResponse response) throws Exception {
        BufferedImage image = QrCodeGenWrapper
                .of(content)
                .setLogo("/Users/jizhengyu/Pictures/logo.jpg")
                .setLogoStyle(QrCodeOptions.LogoStyle.ROUND)
                .setErrorCorrection(ErrorCorrectionLevel.H)
                .setLogoRate(7)
                .asBufferedImage();
        ImageIO.write(image, "png", response.getOutputStream());
    }

    @Override
    public void gitHubQRCodeColor(String content, HttpServletResponse response) throws Exception {
        BufferedImage image = QrCodeGenWrapper
                .of(content)
                .setLogo("/Users/jizhengyu/Pictures/logo.jpg")
                .setLogoStyle(QrCodeOptions.LogoStyle.ROUND)
                .setErrorCorrection(ErrorCorrectionLevel.H)
                .setDrawPreColor(Color.RED)
                .setLogoRate(7)
                .asBufferedImage();

        ImageIO.write(image, "png", response.getOutputStream());

    }

    @Override
    public void gitHubQRCodeBg(String content, HttpServletResponse response) throws Exception {
        BufferedImage image = QrCodeGenWrapper
                .of(content)
                .setLogo("/Users/jizhengyu/Pictures/logo.jpg")
                .setErrorCorrection(ErrorCorrectionLevel.H)
                .setLogoStyle(QrCodeOptions.LogoStyle.ROUND)
                .setDrawPreColor(Color.RED)
                .setLogoRate(7)
                .setBgImg("/Users/jizhengyu/Pictures/logo.png")
                .setBgOpacity(0.5f)
                .asBufferedImage();
        ImageIO.write(image, "png", response.getOutputStream());
    }

    @Override
    public void gitHubQRCodeShape(String content, HttpServletResponse response)throws Exception {
        BufferedImage image = QrCodeGenWrapper
                .of(content)
                .setLogo("/Users/jizhengyu/Pictures/logo.jpg")
                .setLogoStyle(QrCodeOptions.LogoStyle.ROUND)
                .setErrorCorrection(ErrorCorrectionLevel.H)
                .setLogoRate(7)
                .setBgImg("/Users/jizhengyu/Pictures/logo.png")
                .setBgOpacity(0.5f)
                .setDrawEnableScale(true) // 开启二维码缩放
                .setDrawStyle(QrCodeOptions.DrawStyle.DIAMOND) // 钻石样式
                .asBufferedImage();
        ImageIO.write(image, "png", response.getOutputStream());
    }
}
