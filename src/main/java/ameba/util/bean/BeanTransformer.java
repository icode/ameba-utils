package ameba.util.bean;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * @author icode
 */
public abstract class BeanTransformer<T> {

    public static final BeanTransformer DEFAULT = new BeanTransformer() {
        @Override
        protected Object onTransform(Object obj) {
            return new BeanMap<>(obj);
        }
    };

    protected abstract T onTransform(Object obj);

    protected boolean needTransform(Object obj) {
        Class clazz = obj.getClass();
        return !clazz.isPrimitive() && !clazz.getName().startsWith("java.");
    }

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

    @SuppressWarnings("unchecked")
    protected List _transform(final List list) {
        if (list == null) return null;
        if (list instanceof BeanList) return list;
        return new BeanList(list, this);
    }

    @SuppressWarnings("unchecked")
    protected Set _transform(final Set set) {
        if (set == null) return null;
        if (set instanceof BeanSet) return set;
        return new BeanSet<>(set, this);
    }

    @SuppressWarnings("unchecked")
    protected Collection _transform(final Collection collection) {
        if (collection == null) return null;
        if (collection instanceof BeanCollection) return collection;
        return new BeanCollection(collection, this);
    }

    protected Object[] _transform(final Object[] array) {
        Object[] result = new Object[array.length];
        for (Object object : array) {
            ArrayUtils.add(result, transform(object));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    protected Iterable _transform(final Iterable iterable) {
        if (iterable == null) return null;
        if (iterable instanceof BeanIterable) return iterable;
        return new BeanIterable<>(iterable, this);
    }

    @SuppressWarnings("unchecked")
    protected Iterator _transform(final Iterator iterator) {
        if (iterator == null) return null;
        if (iterator instanceof BeanIterator) return iterator;
        return new BeanIterator(iterator, this);
    }

    @SuppressWarnings("unchecked")
    protected ListIterator _transform(final ListIterator listIterator) {
        return new BeanListIterator(listIterator, this);
    }

    @SuppressWarnings("unchecked")
    protected BeanMap _transform(final Map map) {
        return new BeanMap<>(map);
    }
}
