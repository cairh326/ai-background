package net.dreamlu.system.util;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * 集合工具类
 *
 * @author tangxw
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {

    /**
     * Check whether the given Array contains the given element.
     *
     * @param array   the Array to check
     * @param element the element to look for
     * @param <T>     The generic tag
     * @return {@code true} if found, {@code false} else
     */
    public static <T> boolean contains(T[] array, final T element) {
        if (array == null) {
            return false;
        }
        return Arrays.stream(array).anyMatch(x -> ObjectUtil.nullSafeEquals(x, element));
    }

    /**
     * 获取list存储的对象列表中某属性与指定值相匹配的元素
     *
     * @param objs         对象列表
     * @param propertyName (该属性不可以用hibernate model中其他对象的名称，因为是代理的所以用equals无法比对) 指定的属性
     * @param destObj      要匹配的值
     * @return Object
     */
    public static <T> T getMatchedBean(Collection<T> objs, String propertyName, Object destObj) {
        return getMatchedBean(objs, new String[]{propertyName}, new Object[]{destObj});
    }

    /**
     * 获取集合存储的对象列表中与某组属性与指定一组值相匹配的元素
     *
     * @param objs          对象列表
     * @param propertyNames (该属性不可以用hibernate model中其他对象的名称，因为是代理的所以用equals无法比对) 指定的属性数组
     * @param destObjs      要匹配的值数组
     * @return Object
     */
    public static <T> T getMatchedBean(Collection<T> objs, String[] propertyNames, Object[] destObjs) {
        T matchBean = null;
        if (isNotEmpty(objs)) {
            Iterator<T> it = objs.iterator();
            while (it.hasNext()) {
                T bean = it.next();
                int i = 0;
                for (i = 0; i < propertyNames.length; i++) {
                    Object propertyValue = null;
                    try {
                        propertyValue = BeanUtils.getProperty(bean, propertyNames[i]);
                    } catch (Exception e) {

                    }
                    if (!destObjs[i].equals(propertyValue)) {
                        break;
                    }
                }
                if (i == propertyNames.length) {
                    matchBean = bean;
                    break;
                }
            }
        }
        return matchBean;
    }

    /**
     * 移除集合对象列表中某属性与指定值相匹配的元素
     *
     * @param objs         对象列表
     * @param propertyName (该属性不可以用hibernate model中其他对象的名称，因为是代理的所以用equals无法比对) 指定的属性
     * @param destObj      要匹配的值
     */
    public static <T> void removeMatchedBean(Collection<T> objs, String propertyName, Object destObj) {
        removeMatchedBean(objs, new String[]{propertyName}, new Object[]{destObj});
    }

    /**
     * 移除集合对象列表中某组属性与指定一组值相匹配的元素
     *
     * @param objs          对象列表
     * @param propertyNames (该属性不可以用hibernate model中其他对象的名称，因为是代理的所以用equals无法比对) 指定的属性数组
     * @param destObjs      要匹配的值数组
     */
    public static <T> void removeMatchedBean(Collection<T> objs, String[] propertyNames, Object[] destObjs) {
        if (isNotEmpty(objs)) {
            Iterator<T> it = objs.iterator();
            while (it.hasNext()) {
                T bean = it.next();
                int i = 0;
                for (i = 0; i < propertyNames.length; i++) {
                    Object propertyValue = null;
                    try {
                        propertyValue = BeanUtils.getProperty(bean, propertyNames[i]);
                    } catch (Exception e) {
                    }
                    if (!destObjs[i].equals(propertyValue)) {
                        break;
                    }
                }
                if (i == propertyNames.length) {
                    it.remove();
                }
            }
        }
    }


    /**
     * 判断list是否不为空
     *
     * @param collection 要判断的目标list
     * @return boolean
     * @author:zhaixm
     */
    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }
}
