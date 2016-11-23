package ameba.captcha.audio.noise;

import ameba.captcha.audio.Sample;

import java.util.List;

/**
 * <p>NoiseProducer interface.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public interface NoiseProducer {
    /**
     * <p>addNoise.</p>
     *
     * @param target a {@link java.util.List} object.
     * @return a {@link ameba.captcha.audio.Sample} object.
     */
    Sample addNoise(List<Sample> target);
}
