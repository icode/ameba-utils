package ameba.util.bean;

import java.util.*;

/**
 * <p>BeanList class.</p>
 *
 * @author icode
 * request
 */
public class BeanList<T> extends AbstractList {

    protected final List<T> list;
    protected final BeanTransformer beanTransformer;

    /**
     * <p>Constructor for BeanList.</p>
     *
     * @param list            a {@link java.util.List} object.
     * @param beanTransformer a {@link ameba.util.bean.BeanTransformer} object.
     */
    public BeanList(List<T> list, BeanTransformer beanTransformer) {
        this.list = list;
        this.beanTransformer = beanTransformer;
    }

    /**
     * <p>Constructor for BeanList.</p>
     *
     * @param list a {@link java.util.List} object.
     */
    public BeanList(List<T> list) {
        this.list = list;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(int index) {
        return beanTransformer.transform(list.get(index));
    }

    /** {@inheritDoc} */
    public Object set(int index, Object element) {
        return beanTransformer.transform(list.set(index, (T) element));
    }

    /** {@inheritDoc} */
    public void add(int index, Object element) {
        list.add(index, (T) element);
    }

    /** {@inheritDoc} */
    public Object remove(int index) {
        return beanTransformer.transform(list.remove(index));
    }

    /** {@inheritDoc} */
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    /** {@inheritDoc} */
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    /**
     * <p>listIterator.</p>
     *
     * @return a {@link java.util.ListIterator} object.
     */
    public ListIterator listIterator() {
        return beanTransformer._transform(list.listIterator());
    }

    /** {@inheritDoc} */
    public ListIterator listIterator(int index) {
        return beanTransformer._transform(list.listIterator(index));
    }

    /** {@inheritDoc} */
    public List<T> subList(int fromIndex, int toIndex) {
        return beanTransformer._transform(list.subList(fromIndex, toIndex));
    }

    /** {@inheritDoc} */
    public boolean retainAll(Collection c) {
        return list.retainAll(c);
    }

    /** {@inheritDoc} */
    public boolean containsAll(Collection c) {
        return list.containsAll(c);
    }

    /** {@inheritDoc} */
    public boolean removeAll(Collection c) {
        return list.removeAll(c);
    }

    /** {@inheritDoc} */
    @Override
    public Iterator iterator() {
        return beanTransformer._transform(list.iterator());
    }

    /**
     * <p>toArray.</p>
     *
     * @return an array of {@link java.lang.Object} objects.
     */
    public Object[] toArray() {
        return beanTransformer._transform(list.toArray());
    }

    /**
     * <p>toArray.</p>
     *
     * @param a an array of {@link java.lang.Object} objects.
     * @return an array of {@link java.lang.Object} objects.
     */
    public Object[] toArray(Object[] a) {
        return beanTransformer._transform(list.toArray(a));
    }

    /** {@inheritDoc} */
    public boolean add(Object o) {
        return list.add((T) o);
    }

    /** {@inheritDoc} */
    public boolean remove(Object o) {
        return list.remove(o);
    }

    /** {@inheritDoc} */
    public boolean addAll(Collection c) {
        return list.addAll(c);
    }

    /** {@inheritDoc} */
    public boolean addAll(int index, Collection c) {
        return list.addAll(index, c);
    }

    /**
     * <p>clear.</p>
     */
    public void clear() {
        list.clear();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return list.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * <p>isEmpty.</p>
     *
     * @return a boolean.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /** {@inheritDoc} */
    public boolean contains(Object o) {
        return list.contains(o);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return list.toString();
    }
}
