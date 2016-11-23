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
 * <p>ClassUtils class.</p>
 *
 * @author icode
 * request
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {
    /**
     * Constant <code>CLASS_EXTENSION=".class"</code>
     */
    public static final String CLASS_EXTENSION = ".class";
    /**
     * Constant <code>JAVA_EXTENSION=".java"</code>
     */
    public static final String JAVA_EXTENSION = ".java";

    /**
     * <p>getContextClassLoader.</p>
     *
     * @return a {@link java.lang.ClassLoader} object.
     */
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

    /**
     * <p>getClasspathURLs.</p>
     *
     * @param loader a {@link java.lang.ClassLoader} object.
     * @return a {@link java.util.List} object.
     */
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

    /**
     * <p>newInstance.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     */
    public static <T> T newInstance(String name) {
        try {
            return (T) getClass(name).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * <p>newInstance.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param args a {@link java.lang.Object} object.
     * @param <T> a T object.
     * @return a T object.
     */
    public static <T> T newInstance(Class clazz, Object... args) {

        if (clazz == null) return null;

        Class[] argsClass = getArgsClasses(args);

        try {
            Constructor<T> constructor = clazz.<T>getConstructor(argsClass);
            return constructor.newInstance(args);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static Class[] getArgsClasses(Object... args) {
        Class[] argsClass = new Class[args.length];

        for (int i = 0; i < args.length; i++) {
            argsClass[i] = args[i] == null ? null : args[i].getClass();
        }
        return argsClass;
    }

    /**
     * <p>invoke.</p>
     *
     * @param instance a {@link java.lang.Object} object.
     * @param method a {@link java.lang.String} object.
     * @param args a {@link java.lang.Object} object.
     * @param <T> a T object.
     * @return a T object.
     */
    public static <T> T invoke(Object instance, String method, Object... args) {
        if (instance == null) return null;
        try {
            Method m = getPublicMethod(instance.getClass(), method, getArgsClasses(args));
            return (T) m.invoke(instance, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    /**
     * <p>getGenericClass.</p>
     *
     * @param cls a {@link java.lang.Class} object.
     * @return a {@link java.lang.Class} object.
     */
    public static Class<?> getGenericClass(Class<?> cls) {
        return getGenericClass(cls, 0);
    }

    /**
     * <p>getGenericClass.</p>
     *
     * @param cls a {@link java.lang.Class} object.
     * @param i a int.
     * @return a {@link java.lang.Class} object.
     */
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

    /**
     * <p>getJavaVersion.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String getJavaVersion() {
        return System.getProperty("java.specification.version");
    }

    /**
     * <p>isBeforeJava5.</p>
     *
     * @param javaVersion a {@link java.lang.String} object.
     * @return a boolean.
     */
    public static boolean isBeforeJava5(String javaVersion) {
        return (StringUtils.isEmpty(javaVersion) || "1.0".equals(javaVersion)
                || "1.1".equals(javaVersion) || "1.2".equals(javaVersion)
                || "1.3".equals(javaVersion) || "1.4".equals(javaVersion));
    }

    /**
     * <p>isBeforeJava6.</p>
     *
     * @param javaVersion a {@link java.lang.String} object.
     * @return a boolean.
     */
    public static boolean isBeforeJava6(String javaVersion) {
        return isBeforeJava5(javaVersion) || "1.5".equals(javaVersion);
    }


    /**
     * <p>toString.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param method a {@link java.lang.reflect.Method} object.
     * @return a {@link java.lang.String} object.
     */
    public static String toString(Class clazz, Method method) {
        try {
            if (clazz == null) {
                clazz = method.getDeclaringClass();
            }
            StringBuilder sb = new StringBuilder();
            int mod = method.getModifiers() & Modifier.methodModifiers();
            if (mod != 0) {
                sb.append(Modifier.toString(mod)).append(' ');
            }
            sb.append(method.getReturnType().getName()).append(' ');
            sb.append(clazz.getName()).append('.');
            sb.append(method.getName()).append('(');
            Class<?>[] params = method.getParameterTypes(); // avoid clone
            for (int j = 0; j < params.length; j++) {
                sb.append(params[j].getName());
                if (j < (params.length - 1))
                    sb.append(',');
            }
            sb.append(')');
            Class<?>[] exceptions = method.getExceptionTypes(); // avoid clone
            if (exceptions.length > 0) {
                sb.append(" throws ");
                for (int k = 0; k < exceptions.length; k++) {
                    sb.append(exceptions[k].getName());
                    if (k < (exceptions.length - 1))
                        sb.append(',');
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return "<" + e + ">";
        }
    }

    /**
     * <p>searchProperty.</p>
     *
     * @param leftParameter a {@link java.lang.Object} object.
     * @param name a {@link java.lang.String} object.
     * @return a {@link java.lang.Object} object.
     * @throws java.lang.Exception if any.
     */
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
