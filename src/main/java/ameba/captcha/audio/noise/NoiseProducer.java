package ameba.captcha.audio.noise;

import ameba.captcha.audio.Sample;

import java.util.List;

public interface NoiseProducer {
    Sample addNoise(List<Sample> target);
}
