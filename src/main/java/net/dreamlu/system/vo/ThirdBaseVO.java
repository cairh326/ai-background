package net.dreamlu.system.vo;

import lombok.Data;
import net.dreamlu.system.util.StringUtils;

/**
 * third vo
 * @author Administrator
 */
@Data
public class ThirdBaseVO {

	public ThirdBaseVO(){
		this.success = Boolean.TRUE;
		this.message = StringUtils.EMPTY;
	}

	private Boolean success;
	private String message;

}
