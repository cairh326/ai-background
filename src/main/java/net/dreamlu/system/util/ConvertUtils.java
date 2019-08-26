package net.dreamlu.system.util;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

/**
 * 基于 spring ConversionService 类型转换
 *
 * 添加针对 Boolean、Integer、Long的状态模式格式化 @StatusFormat。
 *
 * @author tangxw
 */
public class ConvertUtils {
	private static DefaultFormattingConversionService conversionService;

	static {
		conversionService = new DefaultFormattingConversionService();
		DefaultConversionService.addDefaultConverters(conversionService);
		conversionService.addFormatterForFieldAnnotation(new StatusFormatAnnotationFormatterFactory());
	}

	/**
	 * Convenience operation for converting a source object to the specified targetType.
	 * {@link TypeDescriptor#forObject(Object)}.
	 * @param source the source object
	 * @param targetType the target type
	 * @param <T> 泛型标记
	 * @return the converted value
	 * @throws IllegalArgumentException if targetType is {@code null},
	 * or sourceType is {@code null} but source is not {@code null}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convert(Object source, Class<T> targetType) {
		return conversionService.convert(source, targetType);
	}

	/**
	 * Convenience operation for converting a source object to the specified targetType,
	 * where the target type is a descriptor that provides additional conversion context.
	 * {@link TypeDescriptor#forObject(Object)}.
	 * @param source the source object
	 * @param sourceType the source type
	 * @param targetType the target type
	 * @return the converted value
	 * @throws IllegalArgumentException if targetType is {@code null},
	 * or sourceType is {@code null} but source is not {@code null}
	 */
	public static Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		return conversionService.convert(source, sourceType, targetType);
	}

	/**
	 * Convenience operation for converting a source object to the specified targetType,
	 * where the target type is a descriptor that provides additional conversion context.
	 * Simply delegates to {@link #convert(Object, TypeDescriptor, TypeDescriptor)} and
	 * encapsulates the construction of the source type descriptor using
	 * {@link TypeDescriptor#forObject(Object)}.
	 * @param source the source object
	 * @param targetType the target type
	 * @return the converted value
	 * @throws IllegalArgumentException if targetType is {@code null},
	 * or sourceType is {@code null} but source is not {@code null}
	 */
	public static Object convert(Object source, TypeDescriptor targetType) {
		return conversionService.convert(source, targetType);
	}

}
