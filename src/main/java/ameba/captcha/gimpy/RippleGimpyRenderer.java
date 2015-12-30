package ameba.captcha.gimpy;

import com.jhlabs.image.RippleFilter;

import java.awt.image.BufferedImage;

/**
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 */
public class RippleGimpyRenderer implements GimpyRenderer {

    /**
     * Apply a RippleFilter to the image.
     *
     * @param image The image to be distorted
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