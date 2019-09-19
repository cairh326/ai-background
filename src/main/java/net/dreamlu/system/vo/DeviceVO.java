package net.dreamlu.system.vo;

import lombok.Data;

/**
 * device vo
 * @author Administrator
 */
@Data
public class DeviceVO {
	private String serialNo;
	private String type;
	private String brandCode;
	private String ip;
	private String user;
	private String passwd;
	private String mainStream;
	private String faceWidth;
	private String faceHeight;
}
