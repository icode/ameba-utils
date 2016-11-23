package ameba.captcha.gimpy;

import com.jhlabs.image.RippleFilter;

import java.awt.image.BufferedImage;

/**
 * <p>RippleGimpyRenderer class.</p>
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * request
 */
public class RippleGimpyRenderer implements GimpyRenderer {

    /**
     * {@inheritDoc}
     *
     * Apply a RippleFilter to the image.
     */
    @Override
    public void gimp(BufferedImage image) {
        RippleFilter filter = new RippleFilter();
        filter.setWaveType(RippleFilter.SINE);
        filter.setXAmplitude(2.6f);
        filter.setYAmplitude(1.7f);
        filter.setXWavelength(15);
        filter.setYWavelength(5);

        filter.filter(image, image);
    }
}
