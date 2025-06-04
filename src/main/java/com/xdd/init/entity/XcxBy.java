package com.xdd.init.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2025-03-27 07:20:47
 */
@Data
@TableName("xcx_by")
public class XcxBy implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 *
	 */
	private String dl;
	/**
	 *
	 */
	private String area;
	/**
	 *
	 */
	private Integer hgstZ;
	/**
	 *
	 */
	private Integer hgstJ;
	/**
	 *
	 */
	private Integer hgshZ;
	/**
	 *
	 */
	private Integer hgshJ;
	/**
	 *
	 */
	private Integer fsjbZ;
	/**
	 *
	 */
	private Integer fsjbJ;
	/**
	 *
	 */
	private Integer hjlZ;
	/**
	 *
	 */
	private Integer hjlJ;
	/**
	 *
	 */
	private Integer lycxZ;
	/**
	 *
	 */
	private Integer lycxJ;
	/**
	 *
	 */
	private Integer ysdZ;
	/**
	 *
	 */
	private Integer ysdJ;
	/**
	 *
	 */
	private Integer sort;

}
