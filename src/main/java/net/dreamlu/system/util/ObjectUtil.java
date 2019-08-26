/*
 * *************************************************************************
 *   Copyright (c) 2018-2025, dreamlu.net All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the dreamlu.net developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: chunmeng.lu (qq596392912@gmail.com)
 * *************************************************************************
 */

package net.dreamlu.system.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

/**
 * 对象工具类
 *
 * @author tangxw
 */
@UtilityClass
public class ObjectUtil extends org.springframework.util.ObjectUtils {

	/**
	 * 判断数组不为空
	 * @param array 数组
	 * @return 数组是否为空
	 */
	public static boolean isNotEmpty(@Nullable Object[] array) {
		return !ObjectUtil.isEmpty(array);
	}

}
