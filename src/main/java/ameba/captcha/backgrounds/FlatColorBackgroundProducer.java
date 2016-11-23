package ameba.captcha.backgrounds;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * <p>FlatColorBackgroundProducer class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public final class FlatColorBackgroundProducer implements BackgroundProducer {

    private Color _color = Color.GRAY;

    /**
     * <p>Constructor for FlatColorBackgroundProducer.</p>
     */
    public FlatColorBackgroundProducer() {
        this(Color.GRAY);
    }

    /**
     * <p>Constructor for FlatColorBackgroundProducer.</p>
     *
     * @param color a {@link java.awt.Color} object.
     */
    public FlatColorBackgroundProducer(Color color) {
        _color = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage addBackground(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        return this.getBackground(width, height);
    }

    /** {@inheritDoc} */
    @Override
    public BufferedImage getBackground(int width, int height) {
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(_color);
        graphics.fill(new Rectangle2D.Double(0, 0, width, height));
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();

        return img;
    }
}
