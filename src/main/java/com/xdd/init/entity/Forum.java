package com.xdd.init.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 论坛
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2025-05-18 08:00:02
 */
@Data
@TableName("forum")
public class Forum implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 用户
     */
    private Long uid;

    private transient String uName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 类型 1 帖子 2 评论
     */
    private String type;
    /**
     * 图片
     */
    private String pic;
    /**
     * 点赞数
     */
    private Integer praise;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 点赞人
     */
    private String praiseUid;

    private Long fid;

    private transient List<Forum> forums;
}
