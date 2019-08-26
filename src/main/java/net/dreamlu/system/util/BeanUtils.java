package net.dreamlu.system.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体工具类
 *
 * @author tangxw
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 实例化对象
     *
     * @param clazz 类
     * @param <T>   泛型标记
     * @return 对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        return (T) instantiateClass(clazz);
    }

    /**
     * 实例化对象
     *
     * @param clazzStr 类名
     * @param <T>      泛型标记
     * @return 对象
     */
    public static <T> T newInstance(String clazzStr) {
        try {
            Class<?> clazz = Class.forName(clazzStr);
            return newInstance(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取Bean的属性
     *
     * @param bean         bean
     * @param propertyName 属性名
     * @return 属性值
     */
    public static Object getProperty(Object bean, String propertyName) {
        if (bean instanceof Map) {
            return ((Map) bean).get(propertyName);
        } else {
            PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
            if (pd == null) {
                throw new RuntimeException("Could not read property '" + propertyName + "' from bean PropertyDescriptor is null");
            }
            Method readMethod = pd.getReadMethod();
            if (readMethod == null) {
                throw new RuntimeException("Could not read property '" + propertyName + "' from bean readMethod is null");
            }
            if (!readMethod.isAccessible()) {
                readMethod.setAccessible(true);
            }
            try {
                return readMethod.invoke(bean);
            } catch (Throwable ex) {
                throw new RuntimeException("Could not read property '" + propertyName + "' from bean", ex);
            }
        }
    }

    /**
     * 设置Bean属性
     *
     * @param bean         bean
     * @param propertyName 属性名
     * @param value        属性值
     */
    public static void setProperty(Object bean, String propertyName, Object value) {
        if (bean instanceof Map) {
            ((Map) bean).put(propertyName, value);
        } else {
            PropertyDescriptor pd = getPropertyDescriptor(bean.getClass(), propertyName);
            if (pd == null) {
                throw new RuntimeException("Could not set property '" + propertyName + "' to bean PropertyDescriptor is null");
            }
            Method writeMethod = pd.getWriteMethod();
            if (writeMethod == null) {
                throw new RuntimeException("Could not set property '" + propertyName + "' to bean writeMethod is null");
            }
            if (!writeMethod.isAccessible()) {
                writeMethod.setAccessible(true);
            }
            try {
                writeMethod.invoke(bean, value);
            } catch (Throwable ex) {
                throw new RuntimeException("Could not set property '" + propertyName + "' to bean", ex);
            }
        }
    }

    /**
     * 深复制
     * <p>
     * 注意：不支持链式Bean
     *
     * @param src 源对象
     * @param <T> 泛型标记
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T src) {
        return (T) BeanUtils.copy(src, src.getClass());
    }

    /**
     * copy 对象属性到另一个对象，默认不使用Convert
     * <p>
     * 注意：不支持链式Bean，链式用 copyProperties
     *
     * @param src   源对象
     * @param clazz 类名
     * @param <T>   泛型标记
     * @return T
     */
    public static <T> T copy(Object src, Class<T> clazz) {
        BeanCopier copier = BeanCopier.create(src.getClass(), clazz, false);

        T to = newInstance(clazz);
        copier.copy(src, to, null);
        return to;
    }

    /**
     * 拷贝对象
     * <p>
     * 注意：不支持链式Bean，链式用 copyProperties
     *
     * @param src  源对象
     * @param dist 需要赋值的对象
     */
    public static void copy(Object src, Object dist) {
        BeanCopier copier = BeanCopier
                .create(src.getClass(), dist.getClass(), false);

        copier.copy(src, dist, null);
    }

    /**
     * 拷贝对象并对不同类型属性进行转换
     * <p>
     * 注意：不支持链式Bean，链式用 copyProperties
     *
     * @param src   源对象
     * @param clazz 类名
     * @param <T>   泛型标记
     * @return T
     */
    public static <T> T copyConvert(Object src, Class<T> clazz) {
        Class<?> srcClazz = src.getClass();
        BeanCopier copier = BeanCopier.create(srcClazz, clazz, true);

        T to = newInstance(clazz);
        copier.copy(src, to, new MosquitoConverter(srcClazz, clazz));
        return to;
    }

    /**
     * Copy the property values of the given source bean into the target class.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source the source bean
     * @param target the target bean class
     * @param <T>    泛型标记
     * @return T
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static <T> T copyProperties(Object source, Class<T> target) throws BeansException {
        T to = newInstance(target);
        BeanUtils.copyProperties(source, to);
        return to;
    }

    /**
     * Copy the property values of the given source bean into the target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source the source bean
     * @param target the target bean
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null, null, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * only setting properties defined in the given "editable" class (or interface).
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source   the source bean
     * @param target   the target bean
     * @param editable the class (or interface) to restrict property setting to
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, Class<?> editable) throws BeansException {
        copyProperties(source, target, editable, null, (String[]) null);
    }

    /**
     * Copy the property values of the given source bean into the given target bean,
     * ignoring the given "ignoreProperties".
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
        copyProperties(source, target, null, null, ignoreProperties);
    }

    /**
     * 拷贝对象并对不同类型属性进行转换
     *
     * @param source 源对象
     * @param target 类名
     * @param <T>    泛型标记
     * @return T
     */
    public static <T> T copyPropertiesConvert(Object source, Class<T> target) throws BeansException {
        Class<?> sourceClazz = source.getClass();
        T to = newInstance(target);
        copyProperties(source, to, null, new MosquitoConverter(sourceClazz, target), (String[]) null);
        return to;
    }

    /**
     * Copy the property values of the given source bean into the given target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param editable         the class (or interface) to restrict property setting to
     * @param converter        converter source target Field Types
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, Converter converter, String... ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> targetClass = target.getClass();
        Class<?> actualEditable = targetClass;
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod == null) {
                        continue;
                    }
                    if (converter == null &&
                            !ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        continue;
                    }
                    String writeMethodName = writeMethod.getName();
                    try {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        if (converter != null) {
                            value = converter.convert(value, targetClass, writeMethodName);
                        }
                        writeMethod.invoke(target, value);
                    } catch (Throwable ex) {
                        throw new FatalBeanException(
                                "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                    }
                }
            }
        }
    }

    /**
     * 比较对象
     *
     * @param src  源对象
     * @param dist 新对象
     * @return {BeanDiff}
     */
    public static BeanDiff diff(final Object src, final Object dist) {
        Assert.notNull(src, "diff Object src is null.");
        Assert.notNull(src, "diff Object dist is null.");
        return diff(BeanUtils.toMap(src), BeanUtils.toMap(dist));
    }

    /**
     * 比较Map
     *
     * @param src  源Map
     * @param dist 新Map
     * @return {BeanDiff}
     */
    public static BeanDiff diff(final Map<String, Object> src, final Map<String, Object> dist) {
        Assert.notNull(src, "diff Map src is null.");
        Assert.notNull(src, "diff Map dist is null.");
        // 改变
        Map<String, Object> difference = new HashMap<>();
        difference.putAll(src);
        difference.putAll(dist);
        difference.entrySet().removeAll(src.entrySet());
        // 老值
        Map<String, Object> oldValues = new HashMap<>();
        difference.keySet().forEach((k) -> {
            oldValues.put(k, src.get(k));
        });
        BeanDiff diff = new BeanDiff();
        diff.getFields().addAll(difference.keySet());
        diff.getOldValues().putAll(oldValues);
        diff.getNewValues().putAll(difference);
        return diff;
    }

    /**
     * 生成新Bean 需要继承 BeanDiff 类
     *
     * @param bean 源bean
     * @param <T>  泛型标记
     * @return BeanDiff
     */
    @SuppressWarnings("unchecked")
    public static <T extends BeanDiff> T ofDiffBean(final T bean) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            BeanDiff beanDiff = (BeanDiff) obj;
            String methodName = method.getName();
            if (methodName.startsWith("set")) {
                String propertyName = StringUtils.firstCharToLowerCase(methodName.substring(3));
                Object oldValue = BeanUtils.getProperty(bean, propertyName);
                Object newValue = args[0];
                if (!ObjectUtil.nullSafeEquals(oldValue, newValue)) {
                    beanDiff.getFields().add(propertyName);
                    beanDiff.getOldValues().put(propertyName, oldValue);
                    beanDiff.getNewValues().put(propertyName, args[0]);
                }
                // 设置老bean
                method.invoke(bean, args);
            }
            return proxy.invokeSuper(obj, args);
        });
        return (T) enhancer.create();
    }

    /**
     * 给一个Bean添加字段
     *
     * @param superBean 父级Bean
     * @param props     新增属性
     * @return {Object}
     */
    public static Object generator(Object superBean, BeanProperty... props) {
        Class<?> superclass = superBean.getClass();
        Object genBean = generator(superclass, props);
        BeanUtils.copy(superBean, genBean);
        return genBean;
    }

    /**
     * 给一个class添加字段
     *
     * @param superclass 父级
     * @param props      新增属性
     * @return {Object}
     */
    public static Object generator(Class<?> superclass, BeanProperty... props) {
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(superclass);
        generator.setUseCache(true);
        for (BeanProperty prop : props) {
            generator.addProperty(prop.getName(), prop.getType());
        }
        return generator.create();
    }

    /**
     * 将对象装成map形式
     *
     * @param src 源对象
     * @return {Map}
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object src) {
        return BeanMap.create(src);
    }

    /**
     * 将map 转为 bean
     *
     * @param beanMap   map
     * @param valueType 对象类型
     * @param <T>       泛型标记
     * @return {T}
     */
    public static <T> T toBean(Map<String, Object> beanMap, Class<T> valueType) {
        T bean = BeanUtils.newInstance(valueType);
        PropertyDescriptor[] beanPds = getPropertyDescriptors(valueType);
        for (PropertyDescriptor propDescriptor : beanPds) {
            String propName = propDescriptor.getName();
            // 过滤class属性
            if ("class".equals(propName)) {
                continue;
            }
            if (beanMap.containsKey(propName)) {
                Method writeMethod = propDescriptor.getWriteMethod();
                if (null == writeMethod) {
                    continue;
                }
                Object value = beanMap.get(propName);
                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                    writeMethod.setAccessible(true);
                }
                try {
                    writeMethod.invoke(bean, value);
                } catch (Throwable e) {
                    throw new FatalBeanException("Could not set property '" + propName + "' to bean", e);
                }
            }
        }
        return bean;
    }

}
