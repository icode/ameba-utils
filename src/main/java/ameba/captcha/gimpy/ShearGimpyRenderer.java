package ameba.captcha.gimpy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

/**
 * <p>ShearGimpyRenderer class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class ShearGimpyRenderer implements GimpyRenderer {

    private static final Random RAND = new SecureRandom();
    private final Color _color;

    /**
     * <p>Constructor for ShearGimpyRenderer.</p>
     */
    public ShearGimpyRenderer() {
        this(Color.GRAY);
    }

    /**
     * <p>Constructor for ShearGimpyRenderer.</p>
     *
     * @param color a {@link java.awt.Color} object.
     */
    public ShearGimpyRenderer(Color color) {
        _color = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gimp(BufferedImage bi) {
        Graphics2D g = bi.createGraphics();
        shearX(g, bi.getWidth(), bi.getHeight());
        shearY(g, bi.getWidth(), bi.getHeight());
        g.dispose();
    }

    private void shearX(Graphics2D g, int w1, int h1) {
        int period = RAND.nextInt(10) + 5;

        boolean borderGap = true;
        int frames = 15;
        int phase = RAND.nextInt(5) + 2;

        for (int i = 0; i < h1; i++) {
            double d = (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * phase) / frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(_color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }

    private void shearY(Graphics2D g, int w1, int h1) {
        int period = RAND.nextInt(30) + 10; // 50;

        boolean borderGap = true;
        int frames = 15;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (period >> 1)
                    * Math.sin((float) i / period
                    + (6.2831853071795862D * phase) / frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(_color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }
}
