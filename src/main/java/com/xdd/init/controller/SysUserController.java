package com.xdd.init.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.config.CommonConfig;
import com.xdd.init.config.SkipAuth;
import com.xdd.init.entity.SysUserRole;
import com.xdd.init.model.AjaxResult;
import com.xdd.init.service.SysRoleService;
import com.xdd.init.service.SysUserRoleService;
import com.xdd.init.utils.CommonUtils;
import com.xdd.init.utils.FileUploadUtils;
import com.xdd.init.utils.MimeTypeUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xdd.init.entity.SysUser;
import com.xdd.init.service.SysUserService;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户信息表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@RestController
@RequestMapping("api/user")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;


    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody JSONObject params) {
        Page page = sysUserService.queryPage(params);
        return AjaxResult.success(page);
    }

    @PostMapping("/{userId}")
    public AjaxResult getUserInfo(@PathVariable Long userId) {
        SysUser user = sysUserService.getById(userId);
        if (CommonUtils.isNotEmpty(user)) {
            user.setRoleIds(
                    sysUserRoleService
                            .list(new QueryWrapper<SysUserRole>().eq("user_id", userId))
                            .stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
        }
        return AjaxResult.success(user);
    }

    @SkipAuth
    @PostMapping("/save")
    public AjaxResult save(@RequestBody SysUser user) {
        sysUserService.saveUser(user);
        if (CommonUtils.isNotEmpty(user.getRoleIds())) {
            sysRoleService.saveUserRole(user.getUserId(), user.getRoleIds());
        }
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody SysUser user) {
        if (CommonUtils.isNotEmpty(user.getResPassword())) {
            String resPassword = user.getResPassword();
            String hashpw = BCrypt.hashpw(resPassword, BCrypt.gensalt());
            user.setPassword(hashpw);
        }
        if (CommonUtils.isNotEmpty(user.getRoleIds())) {
            sysRoleService.saveUserRole(user.getUserId(), user.getRoleIds());
        }
        sysUserService.updateById(user);
        return AjaxResult.success();
    }

    @PostMapping("/delete/{userIds}")
    public AjaxResult delete(@PathVariable Long[] userIds) {
        sysUserService.deleteBatchByIds(Arrays.asList(userIds));
        return AjaxResult.success();
    }

    /**
     * 头像上传
     */
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception
    {
        if (!file.isEmpty())
        {
            SysUser user = CommonUtils.getUser();
            String avatar = FileUploadUtils.upload(CommonConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            if (sysUserService.updateUserAvatar(user.getUserId(), avatar))
            {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", avatar);
                // 更新缓存用户头像

                return ajax;
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

}
