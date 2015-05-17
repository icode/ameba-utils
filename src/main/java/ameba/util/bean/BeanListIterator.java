package ameba.util.bean;

import java.util.ListIterator;

/**
 * @author icode
 */
public class BeanListIterator<T> extends BeanIterator<T> implements ListIterator {

    public BeanListIterator(ListIterator<T> listIterator, BeanTransformer beanTransformer) {
        super(listIterator, beanTransformer);
    }

    public BeanListIterator(ListIterator<T> listIterator) {
        super(listIterator);
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Object next() {
        return beanTransformer.transform(iterator.next());
    }

    public boolean hasPrevious() {
        return ((ListIterator)iterator).hasPrevious();
    }

    public Object previous() {
        return beanTransformer.transform(((ListIterator)iterator).previous());
    }

    public int nextIndex() {
        return ((ListIterator)iterator).nextIndex();
    }

    public int previousIndex() {
        return ((ListIterator)iterator).previousIndex();
    }

    public void remove() {
        iterator.remove();
    }

    public void set(Object o) {
        ((ListIterator<T>)iterator).set((T) o);
    }

    public void add(Object o) {
        ((ListIterator<T>)iterator).add((T) o);
    }

    @Override
    public String toString() {
        return iterator.toString();
    }
}