package ameba.captcha.gimpy;

import com.jhlabs.image.ShadowFilter;

import java.awt.image.BufferedImage;

/**
 * Adds a dark drop-shadow.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @version $Id: $Id
 */
public class DropShadowGimpyRenderer implements GimpyRenderer {
    private static final int DEFAULT_RADIUS = 3;
    private static final int DEFAULT_OPACITY = 75;

    private final int _radius;
    private final int _opacity;

    /**
     * <p>Constructor for DropShadowGimpyRenderer.</p>
     */
    public DropShadowGimpyRenderer() {
        this(DEFAULT_RADIUS, DEFAULT_OPACITY);
    }

    /**
     * <p>Constructor for DropShadowGimpyRenderer.</p>
     *
     * @param radius  a int.
     * @param opacity a int.
     */
    public DropShadowGimpyRenderer(int radius, int opacity) {
        _radius = radius;
        _opacity = opacity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gimp(BufferedImage image) {
        ShadowFilter sFilter = new ShadowFilter();
        sFilter.setRadius(_radius);
        sFilter.setOpacity(_opacity);
        sFilter.filter(image, image);
    }
}
