package ameba.util.bean;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>BeanSet class.</p>
 *
 * @author icode
 * request
 */
public class BeanSet<T> extends AbstractSet {

    protected final Set<T> set;
    protected final BeanTransformer beanTransformer;

    /**
     * <p>Constructor for BeanSet.</p>
     *
     * @param set             a {@link java.util.Set} object.
     * @param beanTransformer a {@link ameba.util.bean.BeanTransformer} object.
     */
    public BeanSet(Set<T> set, BeanTransformer beanTransformer) {
        this.set = set;
        this.beanTransformer = beanTransformer;
    }

    /**
     * <p>Constructor for BeanSet.</p>
     *
     * @param set a {@link java.util.Set} object.
     */
    public BeanSet(Set<T> set) {
        this.set = set;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator iterator() {
        return beanTransformer._transform(set.iterator());
    }

    /**
     * <p>toArray.</p>
     *
     * @return an array of {@link java.lang.Object} objects.
     */
    public Object[] toArray() {
        return beanTransformer._transform(set.toArray());
    }

    /**
     * <p>toArray.</p>
     *
     * @param a an array of {@link java.lang.Object} objects.
     * @return an array of {@link java.lang.Object} objects.
     */
    public Object[] toArray(Object[] a) {
        return beanTransformer._transform(set.toArray(a));
    }

    /** {@inheritDoc} */
    public boolean add(Object o) {
        return set.add((T) o);
    }

    /** {@inheritDoc} */
    public boolean remove(Object o) {
        return set.remove(o);
    }

    /** {@inheritDoc} */
    public boolean addAll(Collection c) {
        return set.addAll(c);
    }

    /**
     * <p>clear.</p>
     */
    public void clear() {
        set.clear();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        return set.equals(o);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return set.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return set.size();
    }

    /**
     * <p>isEmpty.</p>
     *
     * @return a boolean.
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /** {@inheritDoc} */
    public boolean contains(Object o) {
        return set.contains(o);
    }

    /** {@inheritDoc} */
    public boolean containsAll(Collection c) {
        return set.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(Collection c) {
        return set.retainAll(c);
    }

    /** {@inheritDoc} */
    public boolean removeAll(Collection c) {
        return set.removeAll(c);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return set.toString();
    }
}
