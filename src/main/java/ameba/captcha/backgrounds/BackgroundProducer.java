package ameba.captcha.backgrounds;

import java.awt.image.BufferedImage;

/**
 * <p>BackgroundProducer interface.</p>
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @version $Id: $Id
 */
public interface BackgroundProducer {

    /**
     * Add the background to the given image.
     *
     * @param image The image onto which the background will be rendered.
     * @return The image with the background rendered.
     */
    BufferedImage addBackground(BufferedImage image);

    /**
     * <p>getBackground.</p>
     *
     * @param width  a int.
     * @param height a int.
     * @return a {@link java.awt.image.BufferedImage} object.
     */
    BufferedImage getBackground(int width, int height);
}
