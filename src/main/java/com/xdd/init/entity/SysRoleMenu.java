package com.xdd.init.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

/**
 * 角色和菜单关联表
 *
 * @author jzy
 * @email 913011175@gmail.com
 * @date 2024-08-08 12:58:40
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@MppMultiId
	@TableField(value = "role_id")
	private Long roleId;
	/**
	 * 菜单ID
	 */
	@MppMultiId
	@TableField(value = "menu_id")
	private Long menuId;

}
