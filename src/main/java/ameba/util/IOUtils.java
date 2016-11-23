package ameba.util;

import com.google.common.collect.Lists;

import java.io.*;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.Manifest;

/**
 * <p>IOUtils class.</p>
 *
 * @author ICode
 * @since 13-8-14 下午7:33
 * @version $Id: $Id
 */
public class IOUtils extends org.apache.commons.io.IOUtils {

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    /**
     * <p>toByteArray.</p>
     *
     * @param file a {@link java.io.File} object.
     * @return an array of byte.
     */
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

    /**
     * <p>getResourceAsStream.</p>
     *
     * @param resource a {@link java.lang.String} object.
     * @return a {@link java.io.InputStream} object.
     */
    public static InputStream getResourceAsStream(String resource) {
        InputStream in = ClassUtils.getContextClassLoader().getResourceAsStream(resource);
        if (in == null) {
            in = IOUtils.class.getResourceAsStream(resource);
        }

        return in;
    }

    /**
     * <p>getResources.</p>
     *
     * @param resource a {@link java.lang.String} object.
     * @return a {@link java.util.Enumeration} object.
     */
    public static Enumeration<URL> getResources(String resource) {
        Enumeration<URL> urls = null;
        try {
            urls = ClassUtils.getContextClassLoader().getResources(resource);
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
            URL url = IOUtils.class.getResource(resource);
            if (url != null) {
                final Iterator<URL> it = Lists.newArrayList(url).iterator();
                urls = new Enumeration<URL>() {
                    public boolean hasMoreElements() {
                        return it.hasNext();
                    }

                    public URL nextElement() {
                        return it.next();
                    }
                };
            }
        }

        if (urls == null) {
            File file = new File(resource);
            if (file.isFile()) {
                try {
                    final Iterator<URL> it = Lists.newArrayList(file.toURI().toURL()).iterator();
                    urls = new Enumeration<URL>() {
                        public boolean hasMoreElements() {
                            return it.hasNext();
                        }

                        public URL nextElement() {
                            return it.next();
                        }
                    };
                } catch (MalformedURLException e) {
                    //no op
                }
            }
        }

        return urls == null ? new Enumeration<URL>() {
            public boolean hasMoreElements() {
                return false;
            }

            public URL nextElement() {
                return null;
            }
        } : urls;
    }

    /**
     * <p>getResource.</p>
     *
     * @param resource a {@link java.lang.String} object.
     * @return a {@link java.net.URL} object.
     */
    public static URL getResource(String resource) {
        URL url = ClassUtils.getContextClassLoader().getResource(resource);
        if (url == null) {
            url = IOUtils.class.getResource(resource);
        }

        return url;
    }

    /**
     * <p>read.</p>
     *
     * @param in a {@link java.io.InputStream} object.
     * @return a {@link java.lang.String} object.
     */
    public static String read(InputStream in) {
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return read(reader);
    }

    /**
     * <p>readFromResource.</p>
     *
     * @param resource a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public static String readFromResource(String resource) throws IOException {

        InputStream in = getResourceAsStream(resource);
        if (in == null) {
            return null;
        }
        try {
            return read(in);
        } finally {
            closeQuietly(in);
        }
    }

    /**
     * <p>readByteArrayFromResource.</p>
     *
     * @param resource a {@link java.lang.String} object.
     * @return an array of byte.
     * @throws java.io.IOException if any.
     */
    public static byte[] readByteArrayFromResource(String resource) throws IOException {
        InputStream in = getResourceAsStream(resource);
        if (in == null) {
            return null;
        }

        try {
            return readByteArray(in);
        } finally {
            closeQuietly(in);
        }
    }

    /**
     * <p>readByteArray.</p>
     *
     * @param input a {@link java.io.InputStream} object.
     * @return an array of byte.
     * @throws java.io.IOException if any.
     */
    public static byte[] readByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    /**
     * <p>read.</p>
     *
     * @param reader a {@link java.io.Reader} object.
     * @return a {@link java.lang.String} object.
     */
    public static String read(Reader reader) {
        StringWriter writer = new StringWriter();
        try {
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

    /**
     * <p>read.</p>
     *
     * @param reader a {@link java.io.Reader} object.
     * @param length a int.
     * @return a {@link java.lang.String} object.
     */
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

    /**
     * <p>toString.</p>
     *
     * @param date a {@link java.util.Date} object.
     * @return a {@link java.lang.String} object.
     */
    public static String toString(java.util.Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * <p>getStackTrace.</p>
     *
     * @param ex a {@link java.lang.Throwable} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getStackTrace(Throwable ex) {
        StringWriter buf = new StringWriter();
        ex.printStackTrace(new PrintWriter(buf));

        return buf.toString();
    }

    /**
     * <p>toString.</p>
     *
     * @param stackTrace an array of {@link java.lang.StackTraceElement} objects.
     * @return a {@link java.lang.String} object.
     */
    public static String toString(StackTraceElement[] stackTrace) {
        StringBuilder buf = new StringBuilder();
        for (StackTraceElement item : stackTrace) {
            buf.append(item.toString());
            buf.append("\n");
        }
        return buf.toString();
    }

    /**
     * <p>getJarImplVersion.</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getJarImplVersion(Class clazz) {
        return getJarManifestValue(clazz, "Implementation-Version");
    }

    /**
     * <p>getJarManifestValue.</p>
     *
     * @param clazz    a {@link java.lang.Class} object.
     * @param attrName a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getJarManifestValue(Class clazz, String attrName) {
        URL url = getResource("/" + clazz.getName().replace('.', '/')
                + ".class");
        if (url != null)
            try {
                URLConnection uc = url.openConnection();
                if (uc instanceof java.net.JarURLConnection) {
                    JarURLConnection juc = (JarURLConnection) uc;
                    Manifest m = juc.getManifest();
                    return m.getMainAttributes().getValue(attrName);
                }
            } catch (IOException e) {
                return null;
            }
        return null;
    }

    /**
     * <p>write.</p>
     *
     * @param in a {@link java.io.InputStream} object.
     * @param path a {@link java.nio.file.Path} object.
     */
    public static void write(InputStream in, Path path) {
        write(in, path, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * <p>write.</p>
     *
     * @param in a {@link java.io.InputStream} object.
     * @param file a {@link java.io.File} object.
     */
    public static void write(InputStream in, File file) {
        write(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * <p>write.</p>
     *
     * @param in a {@link java.io.InputStream} object.
     * @param path a {@link java.nio.file.Path} object.
     * @param op a {@link java.nio.file.StandardCopyOption} object.
     */
    public static void write(InputStream in, Path path, StandardCopyOption op) {
        try {
            Files.copy(in, path, op);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
