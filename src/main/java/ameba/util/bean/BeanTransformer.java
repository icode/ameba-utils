package ameba.util.bean;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * <p>Abstract BeanTransformer class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public abstract class BeanTransformer<T> {

    /**
     * Constant <code>DEFAULT</code>
     */
    public static final BeanTransformer DEFAULT = new BeanTransformer() {
        @Override
        protected Object onTransform(Object obj) {
            return new BeanMap<>(obj);
        }
    };

    /**
     * <p>onTransform.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @return a T object.
     */
    protected abstract T onTransform(Object obj);

    /**
     * <p>needTransform.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @return a boolean.
     */
    protected boolean needTransform(Object obj) {
        Class clazz = obj.getClass();
        return !clazz.isPrimitive() && !clazz.getName().startsWith("java.");
    }

    /**
     * <p>transform.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @return a {@link java.lang.Object} object.
     */
    @SuppressWarnings("unchecked")
    public Object transform(final Object obj) {
        if (obj != null && !(obj instanceof BeanMap)) {
            Class clazz = obj.getClass();
            if (clazz.isArray()) {
                return _transform((Object[]) obj);
            } else if (obj instanceof Map) {
                return _transform((Map) obj);
            } else if (obj instanceof List) {
                return _transform((List) obj);
            } else if (obj instanceof Set) {
                return _transform((Set) obj);
            } else if (obj instanceof Collection) {
                return _transform((Collection) obj);
            } else if (obj instanceof Iterable) {
                return _transform((Iterable) obj);
            } else if (obj instanceof Iterator) {
                return _transform((Iterator) obj);
            } else if (needTransform(obj)) {
                return onTransform(obj);
            }
        }
        return obj;
    }

    /**
     * <p>_transform.</p>
     *
     * @param list a {@link java.util.List} object.
     * @return a {@link java.util.List} object.
     */
    @SuppressWarnings("unchecked")
    protected List _transform(final List list) {
        if (list == null) return null;
        if (list instanceof BeanList) return list;
        return new BeanList(list, this);
    }

    /**
     * <p>_transform.</p>
     *
     * @param set a {@link java.util.Set} object.
     * @return a {@link java.util.Set} object.
     */
    @SuppressWarnings("unchecked")
    protected Set _transform(final Set set) {
        if (set == null) return null;
        if (set instanceof BeanSet) return set;
        return new BeanSet<>(set, this);
    }

    /**
     * <p>_transform.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     * @return a {@link java.util.Collection} object.
     */
    @SuppressWarnings("unchecked")
    protected Collection _transform(final Collection collection) {
        if (collection == null) return null;
        if (collection instanceof BeanCollection) return collection;
        return new BeanCollection(collection, this);
    }

    /**
     * <p>_transform.</p>
     *
     * @param array an array of {@link java.lang.Object} objects.
     * @return an array of {@link java.lang.Object} objects.
     */
    protected Object[] _transform(final Object[] array) {
        Object[] result = new Object[array.length];
        for (Object object : array) {
            ArrayUtils.add(result, transform(object));
        }
        return result;
    }

    /**
     * <p>_transform.</p>
     *
     * @param iterable a {@link java.lang.Iterable} object.
     * @return a {@link java.lang.Iterable} object.
     */
    @SuppressWarnings("unchecked")
    protected Iterable _transform(final Iterable iterable) {
        if (iterable == null) return null;
        if (iterable instanceof BeanIterable) return iterable;
        return new BeanIterable<>(iterable, this);
    }

    /**
     * <p>_transform.</p>
     *
     * @param iterator a {@link java.util.Iterator} object.
     * @return a {@link java.util.Iterator} object.
     */
    @SuppressWarnings("unchecked")
    protected Iterator _transform(final Iterator iterator) {
        if (iterator == null) return null;
        if (iterator instanceof BeanIterator) return iterator;
        return new BeanIterator(iterator, this);
    }

    /**
     * <p>_transform.</p>
     *
     * @param listIterator a {@link java.util.ListIterator} object.
     * @return a {@link java.util.ListIterator} object.
     */
    @SuppressWarnings("unchecked")
    protected ListIterator _transform(final ListIterator listIterator) {
        return new BeanListIterator(listIterator, this);
    }

    /**
     * <p>_transform.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @return a {@link ameba.util.bean.BeanMap} object.
     */
    @SuppressWarnings("unchecked")
    protected BeanMap _transform(final Map map) {
        return new BeanMap<>(map);
    }
}
