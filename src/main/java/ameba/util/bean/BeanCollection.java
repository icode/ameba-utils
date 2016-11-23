package ameba.util.bean;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>BeanCollection class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class BeanCollection<T> extends AbstractCollection {
    protected final Collection<T> collection;
    protected final BeanTransformer beanTransformer;

    /**
     * <p>Constructor for BeanCollection.</p>
     *
     * @param collection      a {@link java.util.Collection} object.
     * @param beanTransformer a {@link ameba.util.bean.BeanTransformer} object.
     */
    public BeanCollection(Collection<T> collection, BeanTransformer beanTransformer) {
        this.collection = collection;
        this.beanTransformer = beanTransformer;
    }

    /**
     * <p>Constructor for BeanCollection.</p>
     *
     * @param collection a {@link java.util.Collection} object.
     */
    public BeanCollection(Collection<T> collection) {
        this.collection = collection;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }

    /** {@inheritDoc} */
    @Override
    public Iterator iterator() {
        return beanTransformer._transform(collection.iterator());
    }

    /**
     * <p>toArray.</p>
     *
     * @return an array of {@link java.lang.Object} objects.
     */
    public Object[] toArray() {
        return beanTransformer._transform(collection.toArray());
    }

    /**
     * <p>toArray.</p>
     *
     * @param a an array of {@link java.lang.Object} objects.
     * @return an array of {@link java.lang.Object} objects.
     */
    public Object[] toArray(Object[] a) {
        return beanTransformer._transform(collection.toArray(a));
    }

    /** {@inheritDoc} */
    public boolean add(Object o) {
        return collection.add((T) o);
    }

    /** {@inheritDoc} */
    public boolean remove(Object o) {
        return collection.remove(o);
    }

    /** {@inheritDoc} */
    public boolean addAll(Collection c) {
        return collection.addAll(c);
    }

    /**
     * <p>clear.</p>
     */
    public void clear() {
        collection.clear();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        return collection.equals(o);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return collection.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return collection.size();
    }

    /**
     * <p>isEmpty.</p>
     *
     * @return a boolean.
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /** {@inheritDoc} */
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    /** {@inheritDoc} */
    public boolean containsAll(Collection c) {
        return collection.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean retainAll(Collection c) {
        return collection.retainAll(c);
    }

    /** {@inheritDoc} */
    public boolean removeAll(Collection c) {
        return collection.removeAll(c);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return collection.toString();
    }
}
