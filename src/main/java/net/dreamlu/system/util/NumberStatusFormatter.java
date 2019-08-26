package net.dreamlu.system.util;

import org.springframework.format.Formatter;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;

/**
 * 数字类型状态格式化
 *
 * @author tangxw
 */
public class NumberStatusFormatter implements Formatter<Number> {
	private final String[] status;
	private final int defaultIndex;

	public NumberStatusFormatter(String[] status, int defaultIndex) {
		this.status = status;
		this.defaultIndex = defaultIndex;
	}

	@Override
	public Number parse(String text, Locale locale) throws ParseException {
		int index = Arrays.asList(status).indexOf(text);
		return index == -1 ? defaultIndex : index;
	}

	@Override
	public String print(Number object, Locale locale) {
		int index = ConvertUtils.convert(object, Integer.class);
		return status[status.length < index ? defaultIndex : index];
	}
}
