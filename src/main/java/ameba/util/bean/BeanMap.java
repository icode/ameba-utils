package ameba.util.bean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.keyvalue.AbstractMapEntry;
import org.apache.commons.lang3.StringUtils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author icode
 */
public class BeanMap<T> extends AbstractMap<String, Object> implements Cloneable {

    protected BeanTransformer transformer;
    /**
     * An empty array.  Used to invoke accessors via reflection.
     */
    protected transient T bean;
    protected transient Class<T> beanClass;
    protected transient Map<String, BeanInvoker> readInvokers = Maps.newHashMap();
    protected transient Map<String, BeanInvoker> writeInvokers = Maps.newHashMap();
    protected transient Map<String, Class<?>> types = Maps.newLinkedHashMap();

    /**
     * Constructs a new empty <code>BeanMap</code>.
     */
    public BeanMap() {
        transformer = BeanTransformer.DEFAULT;
    }


    // Constructors
    //-------------------------------------------------------------------------

    /**
     * Constructs a new <code>BeanMap</code> that operates on the
     * specified bean.  If the given bean is <code>null</code>, then
     * this map will be empty.
     *
     * @param bean the bean for this map to operate on
     */
    public BeanMap(T bean) {
        transformer = BeanTransformer.DEFAULT;
        this.bean = bean;
        initialise();
    }

    public BeanMap(T bean, BeanTransformer beanTransformer) {
        this.transformer = beanTransformer;
        this.bean = bean;
        initialise();
    }

    public String getBeanName() {
        return beanClass.getSimpleName();
    }

    // Map interface
    //-------------------------------------------------------------------------

    /**
     * Renders a string representation of this object.
     *
     * @return a <code>String</code> representation of this object
     */
    @Override
    public String toString() {
        return "BeanMap<" + String.valueOf(bean) + ">";
    }

    /**
     * Clone this bean map using the following process:
     * <p/>
     * <ul>
     * <li>If there is no underlying bean, return a cloned BeanMap without a
     * bean.
     * <p/>
     * <li>Since there is an underlying bean, try to instantiate a new bean of
     * the same type using Class.newInstance().
     * <p/>
     * <li>If the instantiation fails, throw a CloneNotSupportedException
     * <p/>
     * <li>Clone the bean map and set the newly instantiated bean as the
     * underlying bean for the bean map.
     * <p/>
     * <li>Copy each property that is both readable and writable from the
     * existing object to a cloned bean map.
     * <p/>
     * <li>If anything fails along the way, throw a
     * CloneNotSupportedException.
     * <p/>
     * <ul>
     *
     * @return a cloned instance of this bean map
     * @throws CloneNotSupportedException if the underlying bean
     *                                    cannot be cloned
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        BeanMap<T> newMap = (BeanMap<T>) super.clone();

        if (bean == null) {
            // no bean, just an empty bean map at the moment.  return a newly
            // cloned and empty bean map.
            return newMap;
        }

        T newBean;
        try {
            newBean = beanClass.newInstance();
        } catch (Exception e) {
            // unable to instantiate
            throw new CloneNotSupportedException
                    ("Unable to instantiate the underlying bean \"" +
                            beanClass.getName() + "\": " + e);
        }

        try {
            newMap.setBean(newBean);
        } catch (Exception exception) {
            throw new CloneNotSupportedException
                    ("Unable to set bean in the cloned bean map: " +
                            exception);
        }

        try {
            // copy only properties that are readable and writable.  If its
            // not readable, we can't get the value from the old map.  If
            // its not writable, we can't write a value into the new map.
            for (String key : types.keySet()) {
                if (getReadInvoker(key) != null) {
                    newMap.put(key, get(key));
                }
            }
        } catch (Exception exception) {
            throw new CloneNotSupportedException
                    ("Unable to copy bean values to cloned bean map: " +
                            exception);
        }

        return newMap;
    }

    /**
     * Puts all of the writable properties from the given BeanMap into this
     * BeanMap. Read-only and Write-only properties will be ignored.
     *
     * @param map the BeanMap whose properties to put
     */
    public void putAllWriteable(BeanMap<T> map) {
        for (String key : map.types.keySet()) {
            if (getWriteInvoker(key) != null) {
                this.put(key, map.get(key));
            }
        }
    }


    /**
     * This method reinitializes the bean map to have default values for the
     * bean's properties.  This is accomplished by constructing a new instance
     * of the bean which the map uses as its underlying data source.  This
     * behavior for <code>clear()</code> differs from the Map contract in that
     * the mappings are not actually removed from the map (the mappings for a
     * BeanMap are fixed).
     */
    @Override
    public void clear() {
        if (bean == null) {
            return;
        }

        try {
            bean = beanClass.newInstance();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Could not create new instance of class: " + beanClass);
        }
    }

    /**
     * Returns true if the bean defines a property with the given name.
     * <p/>
     * The given name must be a <code>String</code>; if not, this method
     * returns false. This method will also return false if the bean
     * does not define a property with that name.
     * <p/>
     * Write-only properties will not be matched as the test operates against
     * property read methods.
     *
     * @param name the name of the property to check
     * @return false if the given name is null or is not a <code>String</code>;
     * false if the bean does not define a property with that name; or
     * true if the bean does define a property with that name
     */
    @Override
    public boolean containsKey(Object name) {
        return types.containsKey(name);
    }

    /**
     * Returns the value of the bean's property with the given name.
     * <p/>
     * The given name must be a {@link String} and must not be
     * null; otherwise, this method returns <code>null</code>.
     * If the bean defines a property with the given name, the value of
     * that property is returned.  Otherwise, <code>null</code> is
     * returned.
     * <p/>
     * Write-only properties will not be matched as the test operates against
     * property read methods.
     *
     * @param name the name of the property whose value to return
     * @return the value of the property with that name
     */
    @Override
    public Object get(Object name) {
        if (bean != null) {
            BeanInvoker invoker = getReadInvoker((String) name);
            if (invoker != null) {
                try {
                    return transform(invoker);
                } catch (Throwable e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return null;
    }

    protected Object transform(BeanInvoker invoker) throws Throwable {
        return transformer.transform(invoker.invoke());
    }

    /**
     * Sets the bean property with the given name to the given value.
     *
     * @param name  the name of the property to set
     * @param value the value to set that property to
     * @return the previous value of that property
     * @throws IllegalArgumentException if the given name is null;
     *                                  if the given name is not a {@link String}; if the bean doesn't
     *                                  define a property with that name; or if the bean property with
     *                                  that name is read-only
     * @throws ClassCastException       if an error occurs creating the method args
     */
    @Override
    public Object put(String name, Object value) throws IllegalArgumentException, ClassCastException {
        if (bean != null) {
            Object oldValue = get(name);
            BeanInvoker invoker = getWriteInvoker(name);
            if (invoker == null) {
                return null;
            }
            try {
                if (value instanceof BeanMap) {
                    value = ((BeanMap) value).bean;
                }

                invoker.invoke(value);

                Object newValue = get(name);
                firePropertyChange(name, oldValue, newValue);
            } catch (Throwable e) {
                throw new IllegalArgumentException(e.getMessage());
            }
            return oldValue;
        }
        return null;
    }

    public void set(String name, Object value) throws IllegalArgumentException, ClassCastException {
        if (bean != null) {
            BeanInvoker handle = getWriteInvoker(name);
            if (handle == null) {
                return;
            }
            try {
                if (value instanceof BeanMap) {
                    value = ((BeanMap) value).bean;
                }
                handle.invoke(value);
                firePropertyChange(name, null, value);
            } catch (Throwable e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    /**
     * Returns the number of properties defined by the bean.
     *
     * @return the number of properties defined by the bean
     */
    @Override
    public int size() {
        return types.size();
    }


    /**
     * Get the keys for this BeanMap.
     * <p/>
     * Write-only properties are <b>not</b> included in the returned set of
     * property names, although it is possible to set their value and to get
     * their type.
     *
     * @return BeanMap keys.  The Set returned by this method is not
     * modifiable.
     */
    @Override
    public Set<String> keySet() {
        return Collections.unmodifiableSet(types.keySet());
    }

    /**
     * Gets a Set of MapEntry objects that are the mappings for this BeanMap.
     * <p/>
     * Each MapEntry can be set but not removed.
     *
     * @return the unmodifiable set of mappings
     */
    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return Collections.unmodifiableSet(new AbstractSet<Map.Entry<String, Object>>() {
            @Override
            public Iterator<Map.Entry<String, Object>> iterator() {
                return entryIterator();
            }

            @Override
            public int size() {
                return BeanMap.this.types.size();
            }
        });
    }

    /**
     * Returns the values for the BeanMap.
     *
     * @return values for the BeanMap.  The returned collection is not
     * modifiable.
     */
    @Override
    public Collection<Object> values() {
        ArrayList<Object> answer = new ArrayList<>(types.size());
        for (Iterator<Object> iter = valueIterator(); iter.hasNext(); ) {
            answer.add(iter.next());
        }
        return Collections.unmodifiableList(answer);
    }


    // Helper methods
    //-------------------------------------------------------------------------

    /**
     * Returns the type of the property with the given name.
     *
     * @param name the name of the property
     * @return the type of the property, or <code>null</code> if no such
     * property exists
     */
    public Class<?> getType(String name) {
        return types.get(name);
    }

    /**
     * Convenience method for getting an iterator over the keys.
     * <p/>
     * Write-only properties will not be returned in the iterator.
     *
     * @return an iterator over the keys
     */
    public Iterator<String> keyIterator() {
        return types.keySet().iterator();
    }

    /**
     * Convenience method for getting an iterator over the values.
     *
     * @return an iterator over the values
     */
    public Iterator<Object> valueIterator() {
        final Iterator<String> iter = keyIterator();
        return new Iterator<Object>() {
            public boolean hasNext() {
                return iter.hasNext();
            }

            public Object next() {
                String key = iter.next();
                return get(key);
            }

            public void remove() {
                throw new UnsupportedOperationException("remove() not supported for BeanMap");
            }
        };
    }

    /**
     * Convenience method for getting an iterator over the entries.
     *
     * @return an iterator over the entries
     */
    public Iterator<Map.Entry<String, Object>> entryIterator() {
        final Iterator<String> iter = keyIterator();
        return new Iterator<Map.Entry<String, Object>>() {
            public boolean hasNext() {
                return iter.hasNext();
            }

            public Map.Entry<String, Object> next() {
                String key = iter.next();
                Object value = get(key);
                return new Entry<>(BeanMap.this, key, value);
            }

            public void remove() {
                throw new UnsupportedOperationException("remove() not supported for BeanMap");
            }
        };
    }


    // Properties
    //-------------------------------------------------------------------------

    /**
     * Returns the bean currently being operated on.  The return value may
     * be null if this map is empty.
     *
     * @return the bean being operated on by this map
     */
    public T getBean() {
        return bean;
    }

    /**
     * Sets the bean to be operated on by this map.  The given value may
     * be null, in which case this map will be empty.
     *
     * @param newBean the new bean to operate on
     */
    public void setBean(T newBean) {
        bean = newBean;
        reinitialise();
    }

    /**
     * Returns the accessor for the property with the given name.
     *
     * @param name the name of the property
     * @return the accessor method for the property, or null
     */
    public BeanInvoker getReadInvoker(String name) {
        BeanInvoker invoker;
        if (readInvokers.containsKey(name)) {
            invoker = readInvokers.get(name);
        } else {
            invoker = getInvoker(HandleType.GET, name);
            readInvokers.put(name, invoker);
        }
        return invoker;
    }

    /**
     * Returns the mutator for the property with the given name.
     *
     * @param name the name of the property
     * @return the mutator method for the property, or null
     */
    public BeanInvoker getWriteInvoker(String name) {
        BeanInvoker invoker;
        if (writeInvokers.containsKey(name)) {
            invoker = writeInvokers.get(name);
        } else {
            invoker = getInvoker(HandleType.SET, name);
            writeInvokers.put(name, invoker);
        }
        return invoker;
    }

    private BeanInvoker getInvoker(HandleType type, String name) {
        if (types.containsKey(name)) {
            if (getBean() instanceof Map) {
                return new MapInvoker(name);
            } else {
                try {
                    final String m = type.name().toLowerCase() + StringUtils.capitalize(name);
                    final MethodType methodType;
                    if (type == HandleType.GET) {
                        methodType = MethodType.methodType(types.get(name));
                    } else {
                        methodType = MethodType.methodType(void.class, types.get(name));
                    }
                    MethodHandle handle = MethodHandles.publicLookup().findVirtual(beanClass, m, methodType);
                    return new MethodHandleInvoker(name, handle);
                } catch (NoSuchMethodException | IllegalAccessException e) {
                    // no op
                }
            }
        }
        return null;
    }

    /**
     * Reinitializes this bean.  Called during {@link #setBean(Object)}.
     * Does introspection to find properties.
     */
    protected void reinitialise() {
        readInvokers.clear();
        writeInvokers.clear();
        types.clear();
        initialise();
    }

    protected String transformPropertyName(String name) {
        return name;
    }

    @SuppressWarnings("unchecked")
    protected void initialise() {
        if (getBean() == null) {
            return;
        }

        this.beanClass = (Class<T>) bean.getClass();
        if (!(getBean() instanceof Map)) {
            Class currentClass = beanClass;

            List<Class> classes = Lists.newLinkedList();

            while (currentClass != null) {
                classes.add(0, currentClass);
                currentClass = currentClass.getSuperclass();
            }

            for (Class clazz : classes) {
                for (Method m : clazz.getMethods()) {
                    String name = m.getName();
                    if (name.startsWith(HandleType.GET.name().toLowerCase()) && !name.equals("getClass")) {
                        name = transformPropertyName(StringUtils.uncapitalize(name.substring(3)));
                        if (StringUtils.isNotBlank(name)) {
                            types.put(name, m.getReturnType());
                        }
                    }
                }
            }
        } else {
            if (getBean() instanceof BeanMap) {
                types = ((BeanMap) getBean()).types;
                readInvokers = ((BeanMap) getBean()).readInvokers;
                writeInvokers = ((BeanMap) getBean()).writeInvokers;
                beanClass = (Class<T>) bean.getClass();
                bean = (T) ((BeanMap) bean).getBean();
            } else {
                Set<Entry> entries = ((Map) getBean()).entrySet();
                for (Entry entry : entries) {
                    String name = transformPropertyName((String) entry.getKey());
                    if (StringUtils.isNotBlank(name) && entry.getValue() != null) {
                        types.put(name, entry.getValue().getClass());
                    }
                }
            }
        }
    }

    /**
     * Called during a successful {@link #put(Object, Object)} operation.
     * Default implementation does nothing.  Override to be notified of
     * property changes in the bean caused by this map.
     *
     * @param key      the name of the property that changed
     * @param oldValue the old value for that property
     * @param newValue the new value for that property
     */
    protected void firePropertyChange(Object key, Object oldValue, Object newValue) {
    }

    private enum HandleType {
        GET, SET
    }

    /**
     * Map entry used by {@link BeanMap}.
     */
    protected static class Entry<T> extends AbstractMapEntry<String, Object> {
        private final BeanMap<T> owner;

        /**
         * Constructs a new <code>Entry</code>.
         *
         * @param owner the BeanMap this entry belongs to
         * @param key   the key for this entry
         * @param value the value for this entry
         */
        protected Entry(BeanMap<T> owner, String key, Object value) {
            super(key, value);
            this.owner = owner;
        }

        /**
         * Sets the value.
         *
         * @param value the new value for the entry
         * @return the old value for the entry
         */
        @Override
        public Object setValue(Object value) {
            String key = getKey();
            Object oldValue = owner.get(key);

            owner.put(key, value);
            Object newValue = owner.get(key);
            super.setValue(newValue);
            return oldValue;
        }
    }

    private class MapInvoker extends BeanInvoker {
        public MapInvoker(String methodName) {
            super(methodName, bean);
        }

        @Override
        public Object invoke(Object arg) throws Throwable {
            return ((Map) getBean()).get(arg);
        }
    }

    private class MethodHandleInvoker extends BeanInvoker {
        private MethodHandle handle;

        public MethodHandleInvoker(String methodName, MethodHandle handle) {
            super(methodName, BeanMap.this.bean);
            this.handle = handle;
        }

        @Override
        public Object invoke(Object arg) throws Throwable {
            if (arg == null) {
                return handle.invoke(bean);
            } else {
                return handle.invoke(bean, arg);
            }
        }
    }

}
