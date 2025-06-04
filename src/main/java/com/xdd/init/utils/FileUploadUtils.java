package com.xdd.init.utils;


import com.xdd.init.Exception.InvalidExtensionException;

import com.xdd.init.config.CommonConfig;
import com.xdd.init.constant.Constants;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


public class FileUploadUtils {
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024L;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;







    /**
     *
     * @param baseDir  本地上传路径
     * @param file  上传文件
     * @param allowedExtension 允许的文件后缀
     * @return
     * @throws Exception
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension) throws Exception {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new IllegalAccessException("文件后缀命太长");
        }
//      校验文件大小，文件类型是否符合规范
        assertAllowed(file, allowedExtension);

//      计算出文件名  年/月/日/文件名
        String fileName = extractFilename(file);
//      拼接文件路径 文件夹不存在就创建
        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
//      保存到本地
        file.transferTo(new File(absPath));
//      返回处理过的文件路径
        return getPathFileName(baseDir, fileName);
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {
        return String.format("%s/%s_%s.%s", DateFormatUtils.format(new Date(),"yyyy/MM/dd"),
                FilenameUtils.getBaseName(file.getOriginalFilename()), IdUtils.randomUUID(), getExtension(file));
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = CommonConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension) throws IllegalAccessException {
        long size = file.getSize();
//      校验文件大小
        if (size > DEFAULT_MAX_SIZE) {
            throw new IllegalAccessException("文件大小超出限制");
        }
//        提取文件后缀名
        String extension = getExtension(file);
//      控制文件不为空且不在允许范围报错
        if(CommonUtils.isNotEmpty(allowedExtension) && !isAllowedExtension(extension, allowedExtension)){
            throw new IllegalAccessException("文件类型不允许");
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        assert filename != null;
        int index = filename.lastIndexOf(".");
        return filename.substring(index + 1);
    }
}
