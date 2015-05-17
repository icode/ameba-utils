package ameba.util;

import java.io.Serializable;

/**
 * @author icode
 */
public class MetaData implements Serializable {
    private Type type;
    private String info;
    private String description;

    public static enum Type {
        CLASS, METHOD, FIELD
    }
}
