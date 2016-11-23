package ameba.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * <p>FileUtils class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
    /**
     * Constant <code>ONE_KB_BD</code>
     */
    public static final BigDecimal ONE_KB_BD = new BigDecimal(1024L);
    private static final int DEFAULT_MAXCHARS = 3;

    /**
     * Adopted and improved version of
     * {@link org.apache.commons.io.FileUtils#byteCountToDisplaySize(BigInteger)}.
     * <p>
     * Warning! it is not advised to use <code>maxChars &lt; 3</code> because it produces
     * correctly rounded, but non-intuitive results like "0 KB" for 100 bytes.
     *
     * @param size a {@link java.math.BigInteger} object.
     * @param maxChars maximum length of digit part, ie. '1.2'
     * @return rounded byte size as {@link java.lang.String}
     */
    public static String byteCountToDisplaySize(BigInteger size, int maxChars) {
        String displaySize;
        BigDecimal bdSize = new BigDecimal(size);
        SizeSuffix selectedSuffix = SizeSuffix.B;
        for (SizeSuffix sizeSuffix : SizeSuffix.values()) {
            if (sizeSuffix.equals(SizeSuffix.B)) {
                continue;
            }
            if (bdSize.setScale(0, RoundingMode.HALF_UP).toString().length() <= maxChars) {
                break;
            }
            selectedSuffix = sizeSuffix;
            bdSize = bdSize.divide(ONE_KB_BD);
        }
        displaySize = bdSize.setScale(0, RoundingMode.HALF_UP).toString();
        if (displaySize.length() < maxChars - 1) {
            displaySize = bdSize.setScale(
                    maxChars - 1 - displaySize.length(), RoundingMode.HALF_UP).toString();
        }
        return displaySize + " " + selectedSuffix.toString();
    }

    /**
     * See {@link #byteCountToDisplaySize(BigInteger, int)}.
     *
     * @param size a long.
     * @param maxChars a int.
     * @return a {@link java.lang.String} object.
     */
    public static String byteCountToDisplaySize(long size, int maxChars) {
        return byteCountToDisplaySize(BigInteger.valueOf(size), maxChars);
    }

    /** {@inheritDoc} */
    public static String byteCountToDisplaySize(long size) {
        return byteCountToDisplaySize(BigInteger.valueOf(size), DEFAULT_MAXCHARS);
    }

    enum SizeSuffix {
        B, KB, MB, GB, TB, PB, EB, ZB, YB
    }
}
