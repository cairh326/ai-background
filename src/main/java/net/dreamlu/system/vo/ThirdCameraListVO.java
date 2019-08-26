package net.dreamlu.system.vo;

import lombok.Data;
import net.dreamlu.system.util.StringUtils;

/**
 * third cameraList vo
 * @author Administrator
 */
@Data
public class ThirdCameraListVO extends ThirdBaseVO {

	public ThirdCameraListVO(){
		super();
		this.cloudAddress = StringUtils.EMPTY;
		this.cloudKey = StringUtils.EMPTY;
		this.cloudPs = StringUtils.EMPTY;
	}

	private String cloudAddress;
	private String cloudKey;
	private String cloudPs;
	private Object data;
}
