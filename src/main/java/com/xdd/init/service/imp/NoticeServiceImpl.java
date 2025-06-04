package com.xdd.init.service.imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdd.init.entity.Notice;
import com.xdd.init.mapper.NoticeMapper;
import com.xdd.init.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * @Description: TODO
 * @Author: jzy
 * @Date: 2025/5/19
 * @Version:v1.0
 */
@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper,Notice> implements NoticeService {

}
