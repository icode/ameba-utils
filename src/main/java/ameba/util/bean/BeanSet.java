package ameba.util.bean;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author icode
 */
public class BeanSet<T> extends AbstractSet {

    protected final Set<T> set;
    protected final BeanTransformer beanTransformer;

    public BeanSet(Set<T> set, BeanTransformer beanTransformer) {
        this.set = set;
        this.beanTransformer = beanTransformer;
    }

    public BeanSet(Set<T> set) {
        this.set = set;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }

    @Override
    public Iterator iterator() {
        return beanTransformer._transform(set.iterator());
    }

    public Object[] toArray() {
        return beanTransformer._transform(set.toArray());
    }

    public Object[] toArray(Object[] a) {
        return beanTransformer._transform(set.toArray(a));
    }

    public boolean add(Object o) {
        return set.add((T) o);
    }

    public boolean remove(Object o) {
        return set.remove(o);
    }

    public boolean addAll(Collection c) {
        return set.addAll(c);
    }

    public void clear() {
        set.clear();
    }

    @Override
    public boolean equals(Object o) {
        return set.equals(o);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public int size() {
        return set.size();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public boolean contains(Object o) {
        return set.contains(o);
    }

    public boolean containsAll(Collection c) {
        return set.containsAll(c);
    }

    public boolean retainAll(Collection c) {
        return set.retainAll(c);
    }

    public boolean removeAll(Collection c) {
        return set.removeAll(c);
    }

    @Override
    public String toString() {
        return set.toString();
    }
}