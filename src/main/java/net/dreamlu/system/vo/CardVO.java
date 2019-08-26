package net.dreamlu.system.vo;

import lombok.Data;

@Data
public class CardVO {
	/**
	 * 名称
	 */
	private String name;

	/**
	 * ip获取方式
	 */
	private String method;

	/**
	 * 首选DNS
	 */
	private String dns;

	/**
	 * 备用DNS
	 */
	private String dns2;

	/**
	 * 默认网关
	 */
	private String gateway;

	/**
	 * IPV4地址
	 */
	private String ipv4;

	private Boolean is_auto;

	/**
	 * mac地址
	 */
	private String mac;

	/**
	 * 子网掩码
	 */
	private String mask;
}
