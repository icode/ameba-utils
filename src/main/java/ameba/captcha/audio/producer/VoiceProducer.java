package ameba.captcha.audio.producer;

import ameba.captcha.audio.Sample;

/**
 * Generates a vocalization for a single character.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 */
public interface VoiceProducer {
    Sample getVocalization(char letter);
}
