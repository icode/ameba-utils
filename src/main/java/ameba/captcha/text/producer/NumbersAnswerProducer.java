package ameba.captcha.text.producer;


/**
 * TextProducer implementation that will return a series of numbers.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @version $Id: $Id
 */
public class NumbersAnswerProducer implements TextProducer {

    private static final int DEFAULT_LENGTH = 5;
    private static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'};

    private final TextProducer _txtProd;

    /**
     * <p>Constructor for NumbersAnswerProducer.</p>
     */
    public NumbersAnswerProducer() {
        this(DEFAULT_LENGTH);
    }

    /**
     * <p>Constructor for NumbersAnswerProducer.</p>
     *
     * @param length a int.
     */
    public NumbersAnswerProducer(int length) {
        _txtProd = new DefaultTextProducer(length, NUMBERS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return new StringBuffer(_txtProd.getText()).toString();
    }
}
