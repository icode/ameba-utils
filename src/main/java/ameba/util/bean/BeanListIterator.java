package ameba.util.bean;

import java.util.ListIterator;

/**
 * <p>BeanListIterator class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class BeanListIterator<T> extends BeanIterator<T> implements ListIterator {

    /**
     * <p>Constructor for BeanListIterator.</p>
     *
     * @param listIterator    a {@link java.util.ListIterator} object.
     * @param beanTransformer a {@link ameba.util.bean.BeanTransformer} object.
     */
    public BeanListIterator(ListIterator<T> listIterator, BeanTransformer beanTransformer) {
        super(listIterator, beanTransformer);
    }

    /**
     * <p>Constructor for BeanListIterator.</p>
     *
     * @param listIterator a {@link java.util.ListIterator} object.
     */
    public BeanListIterator(ListIterator<T> listIterator) {
        super(listIterator);
    }

    /**
     * <p>hasNext.</p>
     *
     * @return a boolean.
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * <p>next.</p>
     *
     * @return a {@link java.lang.Object} object.
     */
    public Object next() {
        return beanTransformer.transform(iterator.next());
    }

    /**
     * <p>hasPrevious.</p>
     *
     * @return a boolean.
     */
    public boolean hasPrevious() {
        return ((ListIterator)iterator).hasPrevious();
    }

    /**
     * <p>previous.</p>
     *
     * @return a {@link java.lang.Object} object.
     */
    public Object previous() {
        return beanTransformer.transform(((ListIterator)iterator).previous());
    }

    /**
     * <p>nextIndex.</p>
     *
     * @return a int.
     */
    public int nextIndex() {
        return ((ListIterator)iterator).nextIndex();
    }

    /**
     * <p>previousIndex.</p>
     *
     * @return a int.
     */
    public int previousIndex() {
        return ((ListIterator)iterator).previousIndex();
    }

    /**
     * <p>remove.</p>
     */
    public void remove() {
        iterator.remove();
    }

    /**
     * {@inheritDoc}
     */
    public void set(Object o) {
        ((ListIterator<T>)iterator).set((T) o);
    }

    /** {@inheritDoc} */
    public void add(Object o) {
        ((ListIterator<T>)iterator).add((T) o);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return iterator.toString();
    }
}
