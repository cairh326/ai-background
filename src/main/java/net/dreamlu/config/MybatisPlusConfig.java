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

package net.dreamlu.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.secrity.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.type.EnumTypeHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MybatisPlus配置
 *
 * @author L.cm
 */
@Configuration
@MapperScan("net.dreamlu.**.mapper.**")
public class MybatisPlusConfig {

	/**
	 * mybatis-plus分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * SQL执行效率插件
	 *
	 * @return PerformanceInterceptor
	 */
	@Bean
	@Profile({"dev", "test"})
	public PerformanceInterceptor performanceInterceptor() {
		PerformanceInterceptor interceptor = new PerformanceInterceptor();
		interceptor.setMaxTime(1);
		interceptor.setWriteInLog(true);
		return interceptor;
	}

	/**
	 * IEnum 枚举配置
	 */
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
		return new MybatisPlusCustomizers();
	}

	/**
	 * 自定义配置
	 */
	class MybatisPlusCustomizers implements ConfigurationCustomizer {
		@Override
		public void customize(MybatisConfiguration configuration) {
			configuration.setDefaultEnumTypeHandler(EnumTypeHandler.class);
		}
	}

	/**
	 * 全局参数填充
	 */
	@Slf4j
	@Component
	public static class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
		@Override
		public void insertFill(MetaObject metaObject) {
			log.info("mybatis plus start insert fill ....");
			this.fillValIfNullByName("createTime", LocalDateTime.now(), metaObject);
			this.fillValIfNullByName("createBy", SecurityUtils.getUserName(), metaObject);
			// 逻辑删除的填充，避免数据库没有设置默认值，存储为 1 正常
			this.fillValIfNullByName("status", 1, metaObject);
		}

		@Override
		public void updateFill(MetaObject metaObject) {
			log.info("mybatis plus start update fill ....");
			this.fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject);
			this.fillValIfNullByName("updateBy", SecurityUtils.getUserName(), metaObject);
		}

		/**
		 * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
		 *
		 * @param fieldName  属性名
		 * @param fieldVal   属性值
		 * @param metaObject MetaObject
		 */
		private void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject) {
			// 用户手动设置的值
			Object userSetValue = this.getFieldValByName(fieldName, metaObject);
			if (userSetValue != null) {
				return;
			}
			this.setFieldValByName(fieldName, fieldVal, metaObject);
		}
	}
}
