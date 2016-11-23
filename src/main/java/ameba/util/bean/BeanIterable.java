package ameba.util.bean;

import java.util.Iterator;

/**
 * <p>BeanIterable class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class BeanIterable<T> implements Iterable {
    protected final Iterable<T> iterable;
    protected final BeanTransformer beanTransformer;

    /**
     * <p>Constructor for BeanIterable.</p>
     *
     * @param iterable        a {@link java.lang.Iterable} object.
     * @param beanTransformer a {@link ameba.util.bean.BeanTransformer} object.
     */
    public BeanIterable(Iterable<T> iterable, BeanTransformer beanTransformer) {
        this.iterable = iterable;
        this.beanTransformer = beanTransformer;
    }

    /**
     * <p>Constructor for BeanIterable.</p>
     *
     * @param iterator a {@link java.lang.Iterable} object.
     */
    public BeanIterable(Iterable<T> iterator) {
        this.iterable = iterator;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }


    /** {@inheritDoc} */
    @Override
    public Iterator iterator() {
        return beanTransformer._transform(iterable.iterator());
    }

}
