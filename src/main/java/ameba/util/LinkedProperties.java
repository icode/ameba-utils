package ameba.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author ICode
 * @since 13-8-17 下午7:46
 */
public class LinkedProperties extends Properties {
    private Map<Object, Object> linkMap = new LinkedHashMap<>();

    public static LinkedProperties create() {
        return new LinkedProperties();
    }

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

    @Override
    public String getProperty(String key) {
        Object oval = linkMap.get(key);
        String sval = (oval instanceof String) ? (String) oval : null;
        return ((sval == null) && (defaults != null)) ? defaults.getProperty(key) : sval;
    }

    @Override
    public Enumeration<?> propertyNames() {
        return Collections.enumeration(linkMap.keySet());
    }

    @Override
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<String>();

        for (Object key : linkMap.keySet()) {
            set.add((String) key);
        }

        return set;
    }

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

    @Override
    public synchronized boolean contains(Object value) {
        return linkMap.containsValue(value);
    }

    @Override
    public boolean containsValue(Object value) {
        return linkMap.containsValue(value);
    }

    @Override
    public synchronized Enumeration<Object> elements() {
        return Collections.enumeration(linkMap.values());
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return linkMap.entrySet();
    }

    @Override
    public synchronized void clear() {
        linkMap.clear();
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        return linkMap.containsKey(key);
    }

    @Override
    public synchronized int size() {
        return linkMap.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return linkMap.isEmpty();
    }

    @Override
    public synchronized String toString() {
        return linkMap.toString();
    }

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(linkMap.keySet());
    }

    @Override
    public synchronized Object get(Object key) {
        return linkMap.get(key);
    }

    @Override
    protected void rehash() {

    }

    @Override
    public synchronized Object remove(Object key) {
        return linkMap.remove(key);
    }

    @Override
    public synchronized void putAll(Map<?, ?> t) {
        linkMap.putAll(t);
    }

    @Override
    public Set<Object> keySet() {
        return linkMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return linkMap.values();
    }

    @Override
    public synchronized boolean equals(Object o) {
        return linkMap.equals(o);
    }

    @Override
    public synchronized int hashCode() {
        return linkMap.hashCode();
    }
}
