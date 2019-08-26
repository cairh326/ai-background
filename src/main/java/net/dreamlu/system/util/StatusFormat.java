package net.dreamlu.system.util;

import java.lang.annotation.*;


/**
 * 针对 Boolean、Integer、Long的状态模式格式化
 *
 * @author tangxw
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
public @interface StatusFormat {

	/**
	 * status 状态值
	 * @return {String[]}
	 */
	String[] value() default {};

	/**
	 * 默认值 index
	 * @return {int}
	 */
	int defaultIndex() default 0;

}
