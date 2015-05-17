package ameba.util.bean;

import java.util.*;

/**
 * @author icode
 */
public class BeanList<T> extends AbstractList {

    protected final List<T> list;
    protected final BeanTransformer beanTransformer;

    public BeanList(List<T> list, BeanTransformer beanTransformer) {
        this.list = list;
        this.beanTransformer = beanTransformer;
    }

    public BeanList(List<T> list) {
        this.list = list;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }

    @Override
    public Object get(int index) {
        return beanTransformer.transform(list.get(index));
    }

    public Object set(int index, Object element) {
        return beanTransformer.transform(list.set(index, (T) element));
    }

    public void add(int index, Object element) {
        list.add(index, (T) element);
    }

    public Object remove(int index) {
        return beanTransformer.transform(list.remove(index));
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator listIterator() {
        return beanTransformer._transform(list.listIterator());
    }

    public ListIterator listIterator(int index) {
        return beanTransformer._transform(list.listIterator(index));
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return beanTransformer._transform(list.subList(fromIndex, toIndex));
    }

    public boolean retainAll(Collection c) {
        return list.retainAll(c);
    }

    public boolean containsAll(Collection c) {
        return list.containsAll(c);
    }

    public boolean removeAll(Collection c) {
        return list.removeAll(c);
    }

    @Override
    public Iterator iterator() {
        return beanTransformer._transform(list.iterator());
    }

    public Object[] toArray() {
        return beanTransformer._transform(list.toArray());
    }

    public Object[] toArray(Object[] a) {
        return beanTransformer._transform(list.toArray(a));
    }

    public boolean add(Object o) {
        return list.add((T) o);
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public boolean addAll(Collection c) {
        return list.addAll(c);
    }

    public boolean addAll(int index, Collection c) {
        return list.addAll(index, c);
    }

    public void clear() {
        list.clear();
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}