package ameba.util.bean;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author icode
 */
public class BeanCollection<T> extends AbstractCollection {
    protected final Collection<T> collection;
    protected final BeanTransformer beanTransformer;

    public BeanCollection(Collection<T> collection, BeanTransformer beanTransformer) {
        this.collection = collection;
        this.beanTransformer = beanTransformer;
    }

    public BeanCollection(Collection<T> collection) {
        this.collection = collection;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }

    @Override
    public Iterator iterator() {
        return beanTransformer._transform(collection.iterator());
    }

    public Object[] toArray() {
        return beanTransformer._transform(collection.toArray());
    }

    public Object[] toArray(Object[] a) {
        return beanTransformer._transform(collection.toArray(a));
    }

    public boolean add(Object o) {
        return collection.add((T) o);
    }

    public boolean remove(Object o) {
        return collection.remove(o);
    }

    public boolean addAll(Collection c) {
        return collection.addAll(c);
    }

    public void clear() {
        collection.clear();
    }

    @Override
    public boolean equals(Object o) {
        return collection.equals(o);
    }

    @Override
    public int hashCode() {
        return collection.hashCode();
    }

    @Override
    public int size() {
        return collection.size();
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public boolean contains(Object o) {
        return collection.contains(o);
    }

    public boolean containsAll(Collection c) {
        return collection.containsAll(c);
    }

    public boolean retainAll(Collection c) {
        return collection.retainAll(c);
    }

    public boolean removeAll(Collection c) {
        return collection.removeAll(c);
    }

    @Override
    public String toString() {
        return collection.toString();
    }
}
