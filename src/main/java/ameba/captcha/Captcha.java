package ameba.captcha;

import ameba.captcha.backgrounds.BackgroundProducer;
import ameba.captcha.backgrounds.TransparentBackgroundProducer;
import ameba.captcha.gimpy.GimpyRenderer;
import ameba.captcha.gimpy.RippleGimpyRenderer;
import ameba.captcha.noise.CurvedLineNoiseProducer;
import ameba.captcha.noise.NoiseProducer;
import ameba.captcha.text.producer.DefaultTextProducer;
import ameba.captcha.text.producer.TextProducer;
import ameba.captcha.text.renderer.DefaultWordRenderer;
import ameba.captcha.text.renderer.WordRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * A builder for generating a CAPTCHA image/answer pair.
 * <br>
 * <p>
 * Example for generating a new CAPTCHA:
 * </p>
 * <pre>Captcha captcha = new Captcha.Builder(200, 50)
 * 	.addText()
 * 	.addBackground()
 * 	.build();</pre>
 * <p>Note that the <code>build()</code> must always be called last. Other methods are optional,
 * and can sometimes be repeated. For example:</p>
 * <pre>Captcha captcha = new Captcha.Builder(200, 50)
 * 	.addText()
 * 	.addNoise()
 * 	.addNoise()
 * 	.addNoise()
 * 	.addBackground()
 * 	.build();</pre>
 * <p>Adding multiple backgrounds has no affect; the last background added will simply be the
 * one that is eventually rendered.</p>
 * <p>To validate that <code>answerStr</code> is a correct answer to the CAPTCHA:</p>
 * <br>
 * <code>captcha.isCorrect(answerStr);</code>
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 */
public final class Captcha implements Serializable {

    public static final String NAME = "simpleCaptcha";
    private static final long serialVersionUID = 617511236L;
    private Builder _builder;

    private Captcha(Builder builder) {
        _builder = builder;
    }

    public boolean isCorrect(String answer) {
        return answer.equals(_builder._answer);
    }

    public String getAnswer() {
        return _builder._answer;
    }

    /**
     * Get the CAPTCHA image, a PNG.
     *
     * @return A PNG CAPTCHA image.
     */
    public BufferedImage getImage() {
        return _builder._img;
    }

    public Date getTimeStamp() {
        return new Date(_builder._timeStamp.getTime());
    }

    @Override
    public String toString() {
        return _builder.toString();
    }

    public static class Builder implements Serializable {
        private static final long serialVersionUID = 12L;
        /**
         * @serial
         */
        private String _answer = "";
        /**
         * @serial
         */
        private BufferedImage _img;
        /**
         * @serial
         */
        private BufferedImage _bg;
        /**
         * @serial
         */
        private Date _timeStamp;

        private boolean _addBorder = false;

        public Builder(int width, int height) {
            _img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        /**
         * Add a background using the default {@link BackgroundProducer} (a {@link TransparentBackgroundProducer}).
         *
         * @return builder
         */
        public Builder addBackground() {
            return addBackground(new TransparentBackgroundProducer());
        }

        /**
         * Add a background using the given {@link BackgroundProducer}.
         *
         * @param bgProd bgProd
         * @return builder
         */
        public Builder addBackground(BackgroundProducer bgProd) {
            _bg = bgProd.getBackground(_img.getWidth(), _img.getHeight());

            return this;
        }

        /**
         * Generate the answer to the CAPTCHA using the {@link DefaultTextProducer}.
         *
         * @return Builder
         */
        public Builder addText() {
            return addText(new DefaultTextProducer());
        }

        /**
         * Generate the answer to the CAPTCHA using the given
         * {@link TextProducer}.
         *
         * @param txtProd txtProd
         * @return Builder
         */
        public Builder addText(TextProducer txtProd) {
            return addText(txtProd, new DefaultWordRenderer());
        }

        /**
         * Generate the answer to the CAPTCHA using the default
         * {@link TextProducer}, and render it to the image using the given
         * {@link WordRenderer}.
         *
         * @param wRenderer wRenderer
         * @return Builder
         */
        public Builder addText(WordRenderer wRenderer) {
            return addText(new DefaultTextProducer(), wRenderer);
        }

        /**
         * Generate the answer to the CAPTCHA using the given
         * {@link TextProducer}, and render it to the image using the given
         * {@link WordRenderer}.
         *
         * @param txtProd   txtProd
         * @param wRenderer wRenderer
         * @return Builder
         */
        public Builder addText(TextProducer txtProd, WordRenderer wRenderer) {
            _answer += txtProd.getText();
            wRenderer.render(_answer, _img);

            return this;
        }

        /**
         * Add noise using the default {@link NoiseProducer} (a {@link CurvedLineNoiseProducer}).
         *
         * @return Builder
         */
        public Builder addNoise() {
            return this.addNoise(new CurvedLineNoiseProducer());
        }

        /**
         * Add noise using the given NoiseProducer.
         *
         * @param nProd nProd
         * @return Builder
         */
        public Builder addNoise(NoiseProducer nProd) {
            nProd.makeNoise(_img);
            return this;
        }

        /**
         * Gimp the image using the default {@link GimpyRenderer} (a {@link RippleGimpyRenderer}).
         *
         * @return Builder
         */
        public Builder gimp() {
            return gimp(new RippleGimpyRenderer());
        }

        /**
         * Gimp the image using the given {@link GimpyRenderer}.
         *
         * @param gimpy gimpy
         * @return Builder
         */
        public Builder gimp(GimpyRenderer gimpy) {
            gimpy.gimp(_img);
            return this;
        }

        /**
         * Draw a single-pixel wide black border around the image.
         *
         * @return Builder
         */
        public Builder addBorder() {
            _addBorder = true;

            return this;
        }

        /**
         * Build the CAPTCHA. This method should always be called, and should always
         * be called last.
         *
         * @return The constructed CAPTCHA.
         */
        public Captcha build() {
            if (_bg == null) {
                _bg = new TransparentBackgroundProducer().getBackground(_img.getWidth(), _img.getHeight());
            }

            // Paint the main image over the background
            Graphics2D g = _bg.createGraphics();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g.drawImage(_img, null, null);

            if (_addBorder) {
                int width = _img.getWidth();
                int height = _img.getHeight();

                g.setColor(Color.BLACK);
                g.drawLine(0, 0, 0, width);
                g.drawLine(0, 0, width, 0);
                g.drawLine(0, height - 1, width, height - 1);
                g.drawLine(width - 1, height - 1, width - 1, 0);
            }

            _img = _bg;

            _timeStamp = new Date();

            return new Captcha(this);
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("[Answer: ");
            sb.append(_answer);
            sb.append("][Timestamp: ");
            sb.append(_timeStamp);
            sb.append("][Image: ");
            sb.append(_img);
            sb.append("]");

            return sb.toString();
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(_answer);
            out.writeObject(_timeStamp);
            ImageIO.write(_img, "png", ImageIO.createImageOutputStream(out));
        }

        private void readObject(ObjectInputStream in) throws IOException,
                ClassNotFoundException {
            _answer = (String) in.readObject();
            _timeStamp = (Date) in.readObject();
            _img = ImageIO.read(ImageIO.createImageInputStream(in));
        }
    }
}
