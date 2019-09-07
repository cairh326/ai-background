package net.dreamlu.system.vo;

import lombok.Data;

/**
 * camera vo
 * @author Administrator
 */
@Data
public class CameraVO {
	private String code;
	private String locationName;
	private String brandCode;
	private String ip;
	private String user;
	private String passwd;
	private String mainStream;
	private String faceWidth;
	private String faceHeight;
}
