package ameba.captcha.noise;

import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 */
public interface NoiseProducer {
    void makeNoise(BufferedImage image);
}
