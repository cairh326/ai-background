package net.dreamlu.system.util;

import org.springframework.format.Formatter;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Locale;

/**
 * 布尔类型状态格式化
 *
 * 默认status[0]为true
 *
 * @author tangxw
 */
public class BooleanStatusFormatter implements Formatter<Boolean> {
	private final String[] status;
	private final int defaultIndex;

	public BooleanStatusFormatter(String[] status, int defaultIndex) {
		this.status = status;
		this.defaultIndex = defaultIndex;
	}

	@Override
	public Boolean parse(String text, Locale locale) throws ParseException {
		Assert.isTrue(status.length == 2, "@StatusFormat Boolean Field value() length must be 2.");
		return status[defaultIndex].equals(text);
	}

	@Override
	public String print(Boolean object, Locale locale) {
		Assert.isTrue(status.length == 2, "@StatusFormat Boolean Field value() length must be 2.");
		int otherIndex = defaultIndex == 0 ? 1 : 0;
		return object ? status[defaultIndex] : status[otherIndex];
	}
}
