package com.xdd.init.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdd.init.entity.Forum;
import com.xdd.init.entity.SysUser;
import com.xdd.init.mapper.ForumMapper;
import com.xdd.init.service.IForumService;
import com.xdd.init.service.SysUserService;
import com.xdd.init.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2025/5/18
 * @Version:v1.0
 */
@Service("forumService")
public class IForumServiceImpl extends ServiceImpl<ForumMapper,Forum> implements IForumService {

    @Autowired
    private SysUserService userService;
    @Override
    public List<Forum> queryList(JSONObject params) {
        List<SysUser> userList = userService.list();
        HashMap<Long, String> userMap = userList.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getNickName, (k1, k2) -> k1, HashMap::new));

        List<Forum> list = this.baseMapper.queryList(params);
        for (Forum forum : list) {
            forum.setUName(userMap.get(forum.getUid()));
            forum.getForums().forEach(forum1 -> forum1.setUName(userMap.get(forum1.getUid())));
        }
        return list;
    }
}
