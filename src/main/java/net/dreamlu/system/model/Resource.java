/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dreamlu.system.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.dreamlu.mica.core.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 资源
 * </p>
 *
 * @author L.cm
 * @since 2018-01-29
 */
@Data
@TableName("t_resource")
public class Resource implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 资源名称
	 */
	private String name;
	/**
	 * 资源的权限
	 */
	private String permissions;
	/**
	 * 资源路径
	 */
	private String url;
	/**
	 * 打开方式 ajax,iframe
	 */
	@TableField("open_mode")
	private String openMode;
	/**
	 * 资源介绍
	 */
	private String description;
	/**
	 * 资源图标
	 */
	@TableField("icon_cls")
	private String iconCls;
	/**
	 * 父级资源id
	 */
	private Integer pid;
	/**
	 * 排序
	 */
	private Integer seq;
	/**
	 * 打开状态
	 */
	private Boolean opened;
	/**
	 * 资源类别
	 */
	@TableField("resource_type")
	private Integer resourceType;
	/**
	 * 状态[0:失效,1:正常]
	 */
	@TableLogic
	private Integer status;
	/**
	 * 创建时间
	 */
	@TableField(value = "create_time",fill = FieldFill.INSERT)
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "update_time",fill = FieldFill.UPDATE)
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private LocalDateTime updateTime;
}
