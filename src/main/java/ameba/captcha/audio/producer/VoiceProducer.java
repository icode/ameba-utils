package ameba.captcha.audio.producer;

import ameba.captcha.audio.Sample;

/**
 * Generates a vocalization for a single character.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * @version $Id: $Id
 */
public interface VoiceProducer {
    /**
     * <p>getVocalization.</p>
     *
     * @param letter a char.
     * @return a {@link ameba.captcha.audio.Sample} object.
     */
    Sample getVocalization(char letter);
}
