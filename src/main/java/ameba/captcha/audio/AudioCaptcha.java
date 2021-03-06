package ameba.captcha.audio;

import ameba.captcha.audio.noise.NoiseProducer;
import ameba.captcha.audio.noise.RandomNoiseProducer;
import ameba.captcha.audio.producer.RandomNumberVoiceProducer;
import ameba.captcha.audio.producer.VoiceProducer;
import ameba.captcha.text.producer.NumbersAnswerProducer;
import ameba.captcha.text.producer.TextProducer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A builder for generating a CAPTCHA audio/answer pair.
 * <br>
 * <p>
 * Example for generating a new CAPTCHA:
 * </p>
 * <br>
 * <pre>
 * AudioCaptcha ac = new AudioCaptcha.Builder()
 *   .addAnswer()
 *   .addNoise()
 *   .build();
 * </pre>
 * <p>
 * Note that the <code>build()</code> method must always be called last. Other
 * methods are optional.
 * </p>
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * request
 */
public final class AudioCaptcha {

    /**
     * Constant <code>NAME="audioCaptcha"</code>
     */
    public static final String NAME = "audioCaptcha";
    private static final Random RAND = new SecureRandom();

    private Builder _builder;

    private AudioCaptcha(Builder builder) {
        _builder = builder;
    }

    /**
     * <p>isCorrect.</p>
     *
     * @param answer a {@link java.lang.String} object.
     * @return a boolean.
     */
    public boolean isCorrect(String answer) {
        return answer.equals(_builder._answer);
    }

    /**
     * <p>getAnswer.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAnswer() {
        return _builder._answer;
    }

    /**
     * <p>getChallenge.</p>
     *
     * @return a {@link ameba.captcha.audio.Sample} object.
     */
    public Sample getChallenge() {
        return _builder._challenge;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return _builder.toString();
    }

    public static class Builder {

        private String _answer = "";
        private Sample _challenge;
        private List<VoiceProducer> _voiceProds;
        private List<NoiseProducer> _noiseProds;

        public Builder() {
            _voiceProds = new ArrayList<>();
            _noiseProds = new ArrayList<>();
        }

        public Builder addAnswer() {
            return addAnswer(new NumbersAnswerProducer());
        }

        public Builder addAnswer(TextProducer ansProd) {
            _answer += ansProd.getText();

            return this;
        }

        public Builder addVoice() {
            _voiceProds.add(new RandomNumberVoiceProducer());

            return this;
        }

        public Builder addVoice(VoiceProducer vProd) {
            _voiceProds.add(vProd);

            return this;
        }

        public Builder addNoise() {
            return addNoise(new RandomNoiseProducer());
        }

        public Builder addNoise(NoiseProducer noiseProd) {
            _noiseProds.add(noiseProd);

            return this;
        }

        public AudioCaptcha build() {
            // Make sure we have at least one voiceProducer
            if (_voiceProds.size() == 0) {
                addVoice();
            }

            // Convert answer to an array
            char[] ansAry = _answer.toCharArray();

            // Make a List of Samples for each character
            VoiceProducer vProd;
            List<Sample> samples = new ArrayList<>();
            Sample sample;
            for (char anAnsAry : ansAry) {
                // Create Sample for this character from one of the
                // VoiceProducers
                vProd = _voiceProds.get(RAND.nextInt(_voiceProds.size()));
                sample = vProd.getVocalization(anAnsAry);
                samples.add(sample);
            }

            // 3. Add noise, if any, and return the result
            if (_noiseProds.size() > 0) {
                NoiseProducer nProd = _noiseProds.get(RAND.nextInt(_noiseProds
                        .size()));
                _challenge = nProd.addNoise(samples);

                return new AudioCaptcha(this);
            }

            _challenge = Mixer.append(samples);

            return new AudioCaptcha(this);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[Answer: ");
            sb.append(_answer);
            sb.append("]");

            return sb.toString();
        }
    }
}
