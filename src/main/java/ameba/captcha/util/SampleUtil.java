package ameba.captcha.util;

import ameba.captcha.audio.Sample;
import ameba.util.IOUtils;

import java.io.InputStream;

/**
 * <p>SampleUtil class.</p>
 *
 * @author icode
 * request
 */
public class SampleUtil {

    /**
     * Get a file resource and return it as an InputStream. Intended primarily
     * to read in binary files which are contained in a jar.
     *
     * @param filename filename
     * @return An @{link InputStream} to the file
     */
    public static InputStream readResource(String filename) {
        return IOUtils.getResourceAsStream(filename);
    }

    /**
     * <p>readSample.</p>
     *
     * @param filename a {@link java.lang.String} object.
     * @return a {@link ameba.captcha.audio.Sample} object.
     */
    public static Sample readSample(String filename) {
        InputStream is = readResource(filename);
        return new Sample(is);
    }
}
