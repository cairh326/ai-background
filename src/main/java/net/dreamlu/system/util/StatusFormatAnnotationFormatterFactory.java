package net.dreamlu.system.util;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Formats fields annotated with the {@link StatusFormat} annotation.
 *
 * @author tangxw
 * @see StatusFormat
 */
public class StatusFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<StatusFormat> {

	private static final Set<Class<?>> FIELD_TYPES;

	static {
		Set<Class<?>> fieldTypes = new HashSet<>(3);
		fieldTypes.add(Boolean.class);
		fieldTypes.add(Integer.class);
		fieldTypes.add(Long.class);
		FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
	}

	@Override
	public Set<Class<?>> getFieldTypes() {
		return FIELD_TYPES;
	}

	@Override
	public Printer<?> getPrinter(StatusFormat annotation, Class<?> fieldType) {
		return configureFormatterFrom(annotation, fieldType);
	}

	@Override
	public Parser<?> getParser(StatusFormat annotation, Class<?> fieldType) {
		return configureFormatterFrom(annotation, fieldType);
	}

	private Formatter<?> configureFormatterFrom(StatusFormat annotation, Class<?> type) {
		if (type == Boolean.class || type == boolean.class) {
			return new BooleanStatusFormatter(annotation.value(), annotation.defaultIndex());
		} else {
			return new NumberStatusFormatter(annotation.value(), annotation.defaultIndex());
		}
	}

}
