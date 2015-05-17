package ameba.util.bean;

import java.util.Iterator;

/**
 * @author icode
 */
public class BeanIterator<T> implements Iterator {
    protected final Iterator<T> iterator;
    protected final BeanTransformer beanTransformer;

    public BeanIterator(Iterator<T> iterator, BeanTransformer beanTransformer) {
        this.iterator = iterator;
        this.beanTransformer = beanTransformer;
    }

    public BeanIterator(Iterator<T> iterator) {
        this.iterator = iterator;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Object next() {
        return beanTransformer.transform(iterator.next());
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}