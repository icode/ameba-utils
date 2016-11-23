package ameba.captcha.backgrounds;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Generates a transparent background.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @version $Id: $Id
 */
public class TransparentBackgroundProducer implements BackgroundProducer {

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage addBackground(BufferedImage image) {
        return getBackground(image.getWidth(), image.getHeight());
    }

    /** {@inheritDoc} */
    @Override
    public BufferedImage getBackground(int width, int height) {
        BufferedImage bg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g = bg.createGraphics();

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
        g.fillRect(0, 0, width, height);

        return bg;
    }

}
