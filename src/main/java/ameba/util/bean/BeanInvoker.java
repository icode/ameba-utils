package ameba.util.bean;

/**
 * @author icode
 */

public abstract class BeanInvoker {

    private final String propertyName;
    private final Object bean;

    public BeanInvoker(String propertyName, Object bean) {
        this.propertyName = propertyName;
        this.bean = bean;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getBean() {
        return bean;
    }

    public abstract Object invoke(Object arg) throws Throwable;

    public Object invoke() throws Throwable {
        return invoke(null);
    }
}