package net.dreamlu.system.vo;

import lombok.Data;

import java.util.List;

/**
 * Network responcerr
 * 网卡信息响应
 * @author L.cm
 */
@Data
public class NetworkResponse {
	/**
	 * 0表示成功
	 */
	private String code;
	/**
	 * code不为0,对应的失败信息
	 * code为0,表示网卡信息数组字符串
	 */
	private String message;

	private List<CardVO> data;
}
