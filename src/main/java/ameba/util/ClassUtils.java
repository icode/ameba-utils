package ameba.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author icode
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {
    public static final String CLASS_EXTENSION = ".class";
    public static final String JAVA_EXTENSION = ".java";

    public static ClassLoader getContextClassLoader() {
        ClassLoader loader = null;
        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable e) {
        }
        if (loader == null) {
            loader = ClassUtils.class.getClassLoader();
        }
        return loader;
    }

    public static List<URL> getClasspathURLs(ClassLoader loader) {
        List<URL> urls = Lists.newArrayList();
        if (loader != null) {
            final Enumeration<URL> resources;
            try {
                resources = loader.getResources("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (resources.hasMoreElements()) {
                URL location = resources.nextElement();
                urls.add(location);
            }
        }
        return urls;
    }

    public static Object newInstance(String name) {
        try {
            return getClass(name).newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static Class<?> getGenericClass(Class<?> cls) {
        return getGenericClass(cls, 0);
    }

    public static Class<?> getGenericClass(Class<?> cls, int i) {
        try {
            ParameterizedType parameterizedType;
            if (cls.getGenericInterfaces().length > 0
                    && cls.getGenericInterfaces()[0] instanceof ParameterizedType) {
                parameterizedType = ((ParameterizedType) cls.getGenericInterfaces()[0]);
            } else if (cls.getGenericSuperclass() instanceof ParameterizedType) {
                parameterizedType = (ParameterizedType) cls.getGenericSuperclass();
            } else {
                parameterizedType = null;
            }
            if (parameterizedType != null) {
                Object genericClass = parameterizedType.getActualTypeArguments()[i];
                if (genericClass instanceof ParameterizedType) { // 处理多级泛型
                    return (Class<?>) ((ParameterizedType) genericClass).getRawType();
                } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
                    Class<?> componentType = (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
                    if (componentType.isArray()) {
                        return componentType;
                    } else {
                        return Array.newInstance(componentType, 0).getClass();
                    }
                } else if (genericClass instanceof Class) {
                    return (Class<?>) genericClass;
                }
            }
        } catch (Exception e) {
        }
        if (cls.getSuperclass() != null && cls.getSuperclass() != Object.class) {
            return getGenericClass(cls.getSuperclass(), i);
        } else {
            throw new IllegalArgumentException(cls.getName() + " generic type undefined!");
        }
    }

    public static String getJavaVersion() {
        return System.getProperty("java.specification.version");
    }

    public static boolean isBeforeJava5(String javaVersion) {
        return (StringUtils.isEmpty(javaVersion) || "1.0".equals(javaVersion)
                || "1.1".equals(javaVersion) || "1.2".equals(javaVersion)
                || "1.3".equals(javaVersion) || "1.4".equals(javaVersion));
    }

    public static boolean isBeforeJava6(String javaVersion) {
        return isBeforeJava5(javaVersion) || "1.5".equals(javaVersion);
    }

    @SuppressWarnings("unchecked")
    public static Object searchProperty(Object leftParameter, String name) throws Exception {
        Class<?> leftClass = leftParameter.getClass();
        Object result;
        if (leftParameter.getClass().isArray() && "length".equals(name)) {
            result = Array.getLength(leftParameter);
        } else if (leftParameter instanceof Map) {
            result = ((Map<Object, Object>) leftParameter).get(name);
        } else {
            try {
                String getter = "get" + name.substring(0, 1).toUpperCase()
                        + name.substring(1);
                Method method = leftClass.getMethod(getter);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                result = method.invoke(leftParameter);
            } catch (NoSuchMethodException e2) {
                try {
                    String getter = "is"
                            + name.substring(0, 1).toUpperCase()
                            + name.substring(1);
                    Method method = leftClass.getMethod(getter);
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    result = method.invoke(leftParameter);
                } catch (NoSuchMethodException e3) {
                    Field field = leftClass.getField(name);
                    result = field.get(leftParameter);
                }
            }
        }
        return result;
    }

}
