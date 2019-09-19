package net.dreamlu.system.vo;

import lombok.Data;
import net.dreamlu.system.util.StringUtils;

/**
 * third config vo
 * @author Administrator
 */
@Data
public class ThirdConfigVO extends ThirdBaseVO {
	private Object data;
    private BoxVO box;
}
