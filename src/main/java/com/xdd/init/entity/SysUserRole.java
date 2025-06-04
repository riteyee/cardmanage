package com.xdd.init.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

/**
 * 用户和角色关联表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@Data
@TableName("sys_user_role")
public class SysUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@MppMultiId
	@TableField(value = "user_id")
	private Long userId;
	/**
	 * 角色ID
	 */
	@MppMultiId
	@TableField(value = "role_id")
	private Long roleId;

}
