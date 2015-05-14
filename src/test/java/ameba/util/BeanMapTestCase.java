package ameba.util;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author icode
 */
public class BeanMapTestCase {

    public static class BeanWithProperties implements Serializable {
        private int someInt;
        private long someLong;
        private double someDouble;
        private float someFloat;
        private short someShort;
        private byte someByte;
        private char someChar;
        private Integer someInteger;
        private String someString;
        private Object someObject;

        public int getSomeInt() {
            return someInt;
        }

        public void setSomeInt(int someInt) {
            this.someInt = someInt;
        }

        public long getSomeLong() {
            return someLong;
        }

        public void setSomeLong(long someLong) {
            this.someLong = someLong;
        }

        public double getSomeDouble() {
            return someDouble;
        }

        public void setSomeDouble(double someDouble) {
            this.someDouble = someDouble;
        }

        public float getSomeFloat() {
            return someFloat;
        }

        public void setSomeFloat(float someFloat) {
            this.someFloat = someFloat;
        }

        public short getSomeShort() {
            return someShort;
        }

        public void setSomeShort(short someShort) {
            this.someShort = someShort;
        }

        public byte getSomeByte() {
            return someByte;
        }

        public void setSomeByte(byte someByte) {
            this.someByte = someByte;
        }

        public char getSomeChar() {
            return someChar;
        }

        public void setSomeChar(char someChar) {
            this.someChar = someChar;
        }

        public Integer getSomeInteger() {
            return someInteger;
        }

        public void setSomeInteger(Integer someInteger) {
            this.someInteger = someInteger;
        }

        public String getSomeString() {
            return someString;
        }

        public void setSomeString(String someString) {
            this.someString = someString;
        }

        public Object getSomeObject() {
            return someObject;
        }

        public void setSomeObject(Object someObject) {
            this.someObject = someObject;
        }
    }

    /**
     *  An object value that will be stored in the bean map as a value.  Need
     *  to save this externally so that we can make sure the object instances
     *  are equivalent since getSampleValues() would otherwise construct a new
     *  and different Object each time.
     **/
    private final Object objectInFullMap = new Object();

    public Map<Object, Object> makeFullMap() {
        // note: These values must match (i.e. .equals() must return true)
        // those returned from getSampleValues().
        BeanWithProperties bean = new BeanWithProperties();
        bean.setSomeInteger(1234);
        bean.setSomeLong(1298341928234L);
        bean.setSomeDouble(123423.34);
        bean.setSomeFloat(1213332.12f);
        bean.setSomeShort((short) 134);
        bean.setSomeByte((byte) 10);
        bean.setSomeChar('a');
        bean.setSomeInteger(new Integer(1432));
        bean.setSomeString("SomeStringValue");
        bean.setSomeObject(objectInFullMap);
        return new BeanMap(bean);
    }


    @Test
    public void testBeanMapPutAllWriteable() {
        BeanMap map1 = (BeanMap)makeFullMap();
        BeanMap map2 = (BeanMap)makeFullMap();
        map2.put("someInteger", new Integer(0));
        map1.putAllWriteable(map2);
        assertEquals(map1.get("someInteger"), new Integer(0));
    }

}