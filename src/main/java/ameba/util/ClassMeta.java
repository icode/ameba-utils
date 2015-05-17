package ameba.util;

import java.io.Serializable;

/**
 * 类源信息
 *
 * @author icode
 */
public class ClassMeta implements Serializable {

    private ClassMeta(Class clazz) {

    }

    public static ClassMeta from(Class clazz) {
        return new ClassMeta(clazz);
    }
}
