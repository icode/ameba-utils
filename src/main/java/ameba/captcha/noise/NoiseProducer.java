package ameba.captcha.noise;

import java.awt.image.BufferedImage;

/**
 * <p>NoiseProducer interface.</p>
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @version $Id: $Id
 */
public interface NoiseProducer {
    /**
     * <p>makeNoise.</p>
     *
     * @param image a {@link java.awt.image.BufferedImage} object.
     */
    void makeNoise(BufferedImage image);
}
