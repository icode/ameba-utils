package ameba.util;

import com.google.common.collect.Lists;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author ICode
 * @since 13-8-14 下午7:33
 */
public class IOUtils extends org.apache.commons.io.IOUtils{

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public static byte[] toByteArray(File file) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            return toByteArray(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(is);
        }
    }

    public static InputStream getResourceAsStream(String resource) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        if (in == null) {
            in = IOUtils.class.getResourceAsStream(resource);
        }

        return in;
    }

    public static Enumeration<URL> getResources(String resource) {
        Enumeration<URL> urls = null;
        try {
            urls = Thread.currentThread().getContextClassLoader().getResources(resource);
        } catch (IOException e) {
            //noop
        }
        if (urls == null) {
            try {
                urls = IOUtils.class.getClassLoader().getResources(resource);
            } catch (IOException e) {
                //noop
            }
        }

        if (urls == null) {
            final Iterator<URL> it = Lists.newArrayList(IOUtils.class.getResource(resource)).iterator();
            urls = new Enumeration<URL>() {
                public boolean hasMoreElements() {
                    return it.hasNext();
                }

                public URL nextElement() {
                    return it.next();
                }
            };
        }
        return urls;
    }

    public static URL getResource(String resource) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (url == null) {
            url = IOUtils.class.getResource(resource);
        }

        return url;
    }

    public static String read(InputStream in) {
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return read(reader);
    }

    public static String readFromResource(String resource) throws IOException {

        InputStream in = getResourceAsStream(resource);
        if (in == null) {
            return null;
        }

        return read(in);
    }

    public static byte[] readByteArrayFromResource(String resource) throws IOException {
        InputStream in = getResourceAsStream(resource);
        if (in == null) {
            return null;
        }

        return readByteArray(in);
    }

    public static byte[] readByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    public static String read(Reader reader) {
        try {

            StringWriter writer = new StringWriter();

            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            int n;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }

            return writer.toString();
        } catch (IOException ex) {
            throw new IllegalStateException("read error", ex);
        }
    }

    public static String read(Reader reader, int length) {
        try {
            char[] buffer = new char[length];

            int offset = 0;
            int rest = length;
            int len;
            while ((len = reader.read(buffer, offset, rest)) != -1) {
                rest -= len;
                offset += len;

                if (rest == 0) {
                    break;
                }
            }

            return new String(buffer, 0, length - rest);
        } catch (IOException ex) {
            throw new IllegalStateException("read error", ex);
        }
    }

    public static String toString(java.util.Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String getStackTrace(Throwable ex) {
        StringWriter buf = new StringWriter();
        ex.printStackTrace(new PrintWriter(buf));

        return buf.toString();
    }

    public static String toString(StackTraceElement[] stackTrace) {
        StringBuilder buf = new StringBuilder();
        for (StackTraceElement item : stackTrace) {
            buf.append(item.toString());
            buf.append("\n");
        }
        return buf.toString();
    }

    public static Boolean getBoolean(Properties properties, String key) {
        String property = properties.getProperty(key);
        if ("true".equals(property)) {
            return Boolean.TRUE;
        } else if ("false".equals(property)) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static Integer getInteger(Properties properties, String key) {
        String property = properties.getProperty(key);

        if (property == null) {
            return null;
        }
        try {
            return Integer.parseInt(property);
        } catch (NumberFormatException ex) {
            // skip
        }
        return null;
    }

    public static Long getLong(Properties properties, String key) {
        String property = properties.getProperty(key);

        if (property == null) {
            return null;
        }
        try {
            return Long.parseLong(property);
        } catch (NumberFormatException ex) {
            // skip
        }
        return null;
    }

    public static Class<?> loadClass(String className) {
        Class<?> clazz = null;

        if (className == null) {
            return null;
        }

        ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
        if (ctxClassLoader != null) {
            try {
                clazz = ctxClassLoader.loadClass(className);
            } catch (ClassNotFoundException ex) {
                // skip
            }
        }

        if (clazz != null) {
            return clazz;
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e1) {
            return null;
        }
    }
}
