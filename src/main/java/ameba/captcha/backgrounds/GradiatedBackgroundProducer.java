package ameba.captcha.backgrounds;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Creates a gradiated background with the given <i>from</i> and <i>to</i>
 * Color values. If none are specified they default to light gray and white
 * respectively.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @version $Id: $Id
 */
public class GradiatedBackgroundProducer implements BackgroundProducer {

    private Color _fromColor = Color.DARK_GRAY;
    private Color _toColor = Color.WHITE;

    /**
     * <p>Constructor for GradiatedBackgroundProducer.</p>
     */
    public GradiatedBackgroundProducer() {
        this(Color.DARK_GRAY, Color.WHITE);
    }

    /**
     * <p>Constructor for GradiatedBackgroundProducer.</p>
     *
     * @param from a {@link java.awt.Color} object.
     * @param to   a {@link java.awt.Color} object.
     */
    public GradiatedBackgroundProducer(Color from, Color to) {
        _fromColor = from;
        _toColor = to;
    }

    /** {@inheritDoc} */
    @Override
    public BufferedImage getBackground(int width, int height) {
        // create an opaque image
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = img.createGraphics();
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setRenderingHints(hints);

        // create the gradient color
        GradientPaint ytow = new GradientPaint(0, 0, _fromColor, width, height,
                _toColor);

        g.setPaint(ytow);
        // draw gradient color
        g.fill(new Rectangle2D.Double(0, 0, width, height));

        // draw the transparent image over the background
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return img;
    }

    /** {@inheritDoc} */
    @Override
    public BufferedImage addBackground(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        return getBackground(width, height);
    }

    /**
     * <p>setFromColor.</p>
     *
     * @param fromColor a {@link java.awt.Color} object.
     */
    public void setFromColor(Color fromColor) {
        _fromColor = fromColor;
    }

    /**
     * <p>setToColor.</p>
     *
     * @param toColor a {@link java.awt.Color} object.
     */
    public void setToColor(Color toColor) {
        _toColor = toColor;
    }
}
