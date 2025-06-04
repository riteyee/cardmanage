package com.xdd.init.controller;

import com.xdd.init.config.CommonConfig;
import com.xdd.init.entity.SysUser;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.utils.CommonUtils;
import com.xdd.init.utils.FileUploadUtils;
import com.xdd.init.utils.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
public class FileController {
    /**
     * 头像上传
     */
    @PostMapping("/upload")
    public AjaxResult avatar(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            AjaxResult ajax = AjaxResult.success();
            String avatar = FileUploadUtils.upload(CommonConfig.getCardPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            ajax.put("imgUrl", avatar);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}
