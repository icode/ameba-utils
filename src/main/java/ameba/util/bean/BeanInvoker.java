package ameba.util.bean;

/**
 * <p>Abstract BeanInvoker class.</p>
 *
 * @author icode
 * request
 */
public abstract class BeanInvoker {

    private final String propertyName;
    private final Object bean;

    /**
     * <p>Constructor for BeanInvoker.</p>
     *
     * @param propertyName a {@link java.lang.String} object.
     * @param bean         a {@link java.lang.Object} object.
     */
    public BeanInvoker(String propertyName, Object bean) {
        this.propertyName = propertyName;
        this.bean = bean;
    }

    /**
     * <p>Getter for the field <code>propertyName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * <p>Getter for the field <code>bean</code>.</p>
     *
     * @return a {@link java.lang.Object} object.
     */
    public Object getBean() {
        return bean;
    }

    /**
     * <p>invoke.</p>
     *
     * @param arg a {@link java.lang.Object} object.
     * @return a {@link java.lang.Object} object.
     * @throws java.lang.Throwable if any.
     */
    public abstract Object invoke(Object arg) throws Throwable;

    /**
     * <p>invoke.</p>
     *
     * @return a {@link java.lang.Object} object.
     * @throws java.lang.Throwable if any.
     */
    public Object invoke() throws Throwable {
        return invoke(null);
    }
}
