package net.dreamlu.system.vo;

import lombok.Data;

/**
 * 盒子配置信息
 * @author Administrator
 */
@Data
public class BoxVO {
	/**
	 * 序列号
	 */
	private String serialNo;
	/**
	 * 平台地址
	 */
	private String platformAddress;

	/**
	 * 平台端口
	 */
	private String platformPort;

	/**
	 * 平台用户
	 */
	private String platformUser;

	/**
	 * 平台密码
	 */
	private String platformPwd;

	/**
	 * MQ地址
	 */
	private String mqAddress;

	/**
	 * MQ端口
	 */
	private String mqPort;

	/**
	 * MQ用户
	 */
	private String mqUser;

	/**
	 * MQ密码
	 */
	private String mqPwd;
}
