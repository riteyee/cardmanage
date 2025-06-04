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
@TableName("transaction")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 出售人
	 */
	private Long sellUser;
	/**
	 * 求购人
	 */
	private Long buyUser;

	/**
	 * 交换人
	 */
	private Long changeUser;


	/**
	 * 类型 1求购 2 出售
	 */
	private String type;
	/**
	 * 期望价格
	 */
	private String expectationPrice;
	/**
	 * 卡牌数量
	 */
	private Integer number;
	/**
	 * 卡牌名称
	 */
	private String cardName;
	/**
	 * 实际价格
	 */
	private String realPrice;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 1、发布 2、协商价格 3、待支付 4、交易完成
	 */
	private String state;

	/**
	 * 交易编号 求购 和 出售 匹配成功编号
	 */
	private String no;



	private transient String sellUserPhone;

	private transient String buyUserPhone;

	private transient String changePhone;

	private transient String sellUserName;
	private transient String changeName;

	private transient String buyUserName;

	/**
	 * 交换卡片
	 */
	private  String changeCard;
}
