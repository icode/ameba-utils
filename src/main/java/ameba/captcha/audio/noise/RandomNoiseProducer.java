package ameba.captcha.audio.noise;

import ameba.captcha.audio.Mixer;
import ameba.captcha.audio.Sample;
import ameba.captcha.util.SampleUtil;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * Adds noise to a {@link ameba.captcha.audio.Sample} from one of the given <code>noiseFiles</code>.
 * By default this noise comes from one of three files, all located in
 * <code>/sounds/noises/</code>: <code>radio_tuning.wav</code>,
 * <code>restaurant.wav</code>, and <code>swimming.wav</code>. This can be
 * overridden by passing the location of your own sound files to the
 * constructor, e.g.:
 * <br>
 * <pre>
 * String myFiles = { &quot;/mysounds/noise1.wav&quot;, &quot;/mysounds/noise2.wav&quot; };
 * NoiseProducer myNp = new RandomNoiseProducer(myFiles);
 * </pre>
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @version $Id: $Id
 */
public class RandomNoiseProducer implements NoiseProducer {

    private static final Random RAND = new SecureRandom();
    private static final String[] DEFAULT_NOISES = {
            "/sounds/noises/radio_tuning.wav",
            "/sounds/noises/restaurant.wav",
            "/sounds/noises/swimming.wav",};

    private final String _noiseFiles[];

    /**
     * <p>Constructor for RandomNoiseProducer.</p>
     */
    public RandomNoiseProducer() {
        this(DEFAULT_NOISES);
    }

    /**
     * <p>Constructor for RandomNoiseProducer.</p>
     *
     * @param noiseFiles an array of {@link java.lang.String} objects.
     */
    public RandomNoiseProducer(String[] noiseFiles) {
        _noiseFiles = noiseFiles;
    }

    /**
     * {@inheritDoc}
     *
     * Append the given <code>samples</code> to each other, then add random
     * noise to the result.
     */
    @Override
    public Sample addNoise(List<Sample> samples) {
        Sample appended = Mixer.append(samples);
        String noiseFile = _noiseFiles[RAND.nextInt(_noiseFiles.length)];
        Sample noise = SampleUtil.readSample(noiseFile);

        // Decrease the volume of the noise to make sure the voices can be heard
        return Mixer.mix(appended, 1.0, noise, 0.6);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[Noise files: ");
        sb.append(_noiseFiles);
        sb.append("]");

        return sb.toString();
    }
}
