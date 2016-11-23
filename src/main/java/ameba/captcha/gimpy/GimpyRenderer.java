package ameba.captcha.gimpy;

import java.awt.image.BufferedImage;

/**
 * <p>GimpyRenderer interface.</p>
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * request
 */
public interface GimpyRenderer {
    /**
     * <p>gimp.</p>
     *
     * @param image a {@link java.awt.image.BufferedImage} object.
     */
    void gimp(BufferedImage image);
}
