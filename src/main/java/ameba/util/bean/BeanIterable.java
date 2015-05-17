package ameba.util.bean;

import java.util.Iterator;

/**
 * @author icode
 */
public class BeanIterable<T> implements Iterable {
    protected final Iterable<T> iterable;
    protected final BeanTransformer beanTransformer;

    public BeanIterable(Iterable<T> iterable, BeanTransformer beanTransformer) {
        this.iterable = iterable;
        this.beanTransformer = beanTransformer;
    }

    public BeanIterable(Iterable<T> iterator) {
        this.iterable = iterator;
        this.beanTransformer = BeanTransformer.DEFAULT;
    }


    @Override
    public Iterator iterator() {
        return beanTransformer._transform(iterable.iterator());
    }

}