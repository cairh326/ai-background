package net.dreamlu.system.util;

import org.springframework.cglib.core.Converter;
import org.springframework.core.convert.TypeDescriptor;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 组合 spring cglib Converter 和 spring ConversionService
 *
 * @author tangxw
 */
public class MosquitoConverter implements Converter {
	private static ConcurrentMap<String, TypeDescriptor> typeCache = new ConcurrentHashMap<>();
	private final Class<?> srcClazz;
	private final Class<?> targetClazz;

	public MosquitoConverter(Class<?> srcClazz, Class<?> targetClazz) {
		this.srcClazz = srcClazz;
		this.targetClazz = targetClazz;
	}

	/**
	 * cglib convert
	 * @param value 源对象属性
	 * @param target 目标对象属性类
	 * @param context 目标对象setter方法名
	 * @return {Object}
	 */
	@Override
	public Object convert(Object value, Class target, final Object context) {
		if (value.getClass() == target) {
			return value;
		}
		// 获取
		String fieldName = StringUtils.firstCharToLowerCase(((String) context).substring(3));
		try {
			TypeDescriptor srcDescriptor = MosquitoConverter.getTypeDescriptor(srcClazz, context, fieldName);
			TypeDescriptor targetDescriptor = MosquitoConverter.getTypeDescriptor(targetClazz, context, fieldName);
			return ConvertUtils.convert(value, srcDescriptor, targetDescriptor);
		} catch (RuntimeException e) {
			return null;
		}
	}

	private static TypeDescriptor getTypeDescriptor(final Class<?> clazz, final Object context, String fieldName) {
		String srcCacheKey = clazz.getName() + context;
		return typeCache.computeIfAbsent(srcCacheKey, Try.of(k -> {
			Field srcField = clazz.getDeclaredField(fieldName);
			return new TypeDescriptor(srcField);
		}));
	}
}
