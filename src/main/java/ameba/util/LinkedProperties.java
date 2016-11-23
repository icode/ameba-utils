package ameba.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>LinkedProperties class.</p>
 *
 * @author ICode
 * @since 13-8-17 下午7:46
 * @version $Id: $Id
 */
public class LinkedProperties extends Properties {
    private Map<Object, Object> linkMap = new LinkedHashMap<>();

    /**
     * <p>create.</p>
     *
     * @return a {@link ameba.util.LinkedProperties} object.
     */
    public static LinkedProperties create() {
        return new LinkedProperties();
    }

    /**
     * <p>create.</p>
     *
     * @param resources a {@link java.lang.String} object.
     * @return a {@link ameba.util.LinkedProperties} object.
     */
    public static LinkedProperties create(String... resources) {
        LinkedProperties properties = create();
        if (resources != null) {
            for (String res : resources) {
                if (StringUtils.isNotBlank(res)) {
                    Enumeration<URL> enumeration = IOUtils.getResources(res);
                    while (enumeration.hasMoreElements()) {
                        try (InputStream in = enumeration.nextElement().openStream()) {
                            properties.load(in);
                        } catch (IOException e) {
                            // no op
                        }
                    }
                }
            }
        }
        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProperty(String key) {
        Object oval = linkMap.get(key);
        String sval = (oval instanceof String) ? (String) oval : null;
        return ((sval == null) && (defaults != null)) ? defaults.getProperty(key) : sval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<?> propertyNames() {
        return Collections.enumeration(linkMap.keySet());
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<String>();

        for (Object key : linkMap.keySet()) {
            set.add((String) key);
        }

        return set;
    }

    /** {@inheritDoc} */
    @Override
    public synchronized Object put(Object key, Object value) {
        if (value instanceof String
                && key instanceof String) {
            boolean append = ((String) key).endsWith("+");
            boolean remove = ((String) key).endsWith("-");
            if (append || remove) {
                key = ((String) key).substring(0, ((String) key).length() - 1);
                Object v = linkMap.get(key);
                if (v instanceof String) {
                    if (append) {
                        value = v + "," + value;
                    } else {
                        value = ((String) v).replaceAll(Pattern.quote((String) value), "").replaceAll(",{2,}", ",");
                    }
                } else if (remove) {
                    key = null;
                }
            }
        }
        if (key != null)
            return linkMap.put(key, value);
        else return null;
    }

    /** {@inheritDoc} */
    @Override
    public synchronized boolean contains(Object value) {
        return linkMap.containsValue(value);
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsValue(Object value) {
        return linkMap.containsValue(value);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized Enumeration<Object> elements() {
        return Collections.enumeration(linkMap.values());
    }

    /** {@inheritDoc} */
    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return linkMap.entrySet();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void clear() {
        linkMap.clear();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized boolean containsKey(Object key) {
        return linkMap.containsKey(key);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized int size() {
        return linkMap.size();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized boolean isEmpty() {
        return linkMap.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized String toString() {
        return linkMap.toString();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(linkMap.keySet());
    }

    /** {@inheritDoc} */
    @Override
    public synchronized Object get(Object key) {
        return linkMap.get(key);
    }

    /** {@inheritDoc} */
    @Override
    protected void rehash() {

    }

    /** {@inheritDoc} */
    @Override
    public synchronized Object remove(Object key) {
        return linkMap.remove(key);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void putAll(Map<?, ?> t) {
        linkMap.putAll(t);
    }

    /** {@inheritDoc} */
    @Override
    public Set<Object> keySet() {
        return linkMap.keySet();
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Object> values() {
        return linkMap.values();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized boolean equals(Object o) {
        return linkMap.equals(o);
    }

    /** {@inheritDoc} */
    @Override
    public synchronized int hashCode() {
        return linkMap.hashCode();
    }
}
