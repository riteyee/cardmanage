package com.xdd.init.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xdd.init.constant.SystemConstants;
import com.xdd.init.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xdd.init.mapper.SysUserMapper;
import com.xdd.init.entity.SysUser;
import com.xdd.init.service.SysUserService;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public Page queryPage(JSONObject params) {
        Page page = CommonUtils.getPage(params);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (CommonUtils.isNotEmpty(params.get("userName"))) {
            wrapper.like("user_name", params.get("userName"));
        }
        if (CommonUtils.isNotEmpty(params.get("nickName"))) {
            wrapper.like("nick_name", params.get("nickName"));
        }
        if (CommonUtils.isNotEmpty(params.get("sex"))) {
            wrapper.eq("sex", params.get("sex"));
        }
        wrapper.eq("del_flag", SystemConstants.UN_DEL);
        wrapper.orderByAsc("user_id");
        Page userPage = this.page(page, wrapper);
        return userPage;
    }

    @Override
    public SysUser getUserByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        if (this.getOne(wrapper) != null) {
            return this.getOne(wrapper);
        }
        return null;
    }

    @Override
    public SysUser findByPhoneNumber(String phone) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("phonenumber", phone);
        if (this.getOne(wrapper) != null) {
            return this.getOne(wrapper);
        }
        return null;
    }

    @Override
    public void saveUser(SysUser user) {
        String resPassword = user.getResPassword();
        String hashPw = BCrypt.hashpw(resPassword, BCrypt.gensalt());
        user.setPassword(hashPw);
        this.save(user);

    }

    @Override
    public void deleteBatchByIds(List<Long> asList) {
        this.baseMapper.deleteBatchByIds(asList, SystemConstants.DEL);

    }

    @Override
    public boolean updateUserAvatar(Long userId, String avatar) {
        SysUser sysUser = this.baseMapper.selectById(userId);
        if (sysUser != null) {
            sysUser.setAvatar(avatar);
            this.baseMapper.updateById(sysUser);
            return true;
        }
        return false;
    }

    @Override
    public SysUser findByEmail(String mail) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email", mail);
        SysUser sysUser = this.baseMapper.selectOne(wrapper);
        return sysUser;
    }
}
