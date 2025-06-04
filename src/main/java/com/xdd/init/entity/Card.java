package com.xdd.init.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2025-05-17 11:31:59
 */
@Data
@TableName("card")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 用户id

	 */
	private Long uid;
	/**
	 * 名称
	 */
	private String cardName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 属性
	 */
	private String attribute;
	/**
	 * 稀有度
	 */
	private String rarity;
	/**
	 * 图片
	 */
	private String pic;


	private Integer number;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

}
