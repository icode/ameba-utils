package ameba.util.bean;

import java.util.Iterator;

/**
 * <p>BeanIterator class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class BeanIterator<T> implements Iterator {
    protected final Iterator<T> iterator;
    protected final BeanTransformer beanTransformer;

    /**
     * <p>Constructor for BeanIterator.</p>
     *
     * @param iterator        a {@link java.util.Iterator} object.
     * @param beanTransformer a {@link ameba.util.bean.BeanTransformer} object.
     */
    public BeanIterator(Iterator<T> iterator, BeanTransformer beanTransformer) {
        this.iterator = iterator;
        this.beanTransformer = beanTransformer;
    }

    /**
     * <p>Constructor for BeanIterator.</p>
     *
     * @param iterator a {@link java.util.Iterator} object.
     */
    public BeanIterator(Iterator<T> iterator) {
        this.iterator = iterator;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /** {@inheritDoc} */
    @Override
    public Object next() {
        return beanTransformer.transform(iterator.next());
    }

    /** {@inheritDoc} */
    @Override
    public void remove() {
        iterator.remove();
    }
}
