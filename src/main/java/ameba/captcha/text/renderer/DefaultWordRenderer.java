package ameba.captcha.text.renderer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Renders the answer onto the image.
 *
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * request
 */
public class DefaultWordRenderer implements WordRenderer {

    private static final Random RAND = new SecureRandom();
    private static final List<Color> DEFAULT_COLORS = new ArrayList<>();
    private static final List<Font> DEFAULT_FONTS = new ArrayList<>();
    // The text will be rendered 25%/5% of the image height/width from the X and Y axes
    private static final double YOFFSET = 0.25;
    private static final double XOFFSET = 0.05;

    static {
        DEFAULT_COLORS.add(Color.BLACK);
        DEFAULT_FONTS.add(new Font("Arial", Font.BOLD, 40));
        DEFAULT_FONTS.add(new Font("Courier", Font.BOLD, 40));
    }

    private final List<Color> _colors = new ArrayList<>();
    private final List<Font> _fonts = new ArrayList<>();

    /**
     * Use the default color (black) and fonts (Arial and Courier).
     */
    public DefaultWordRenderer() {
        this(DEFAULT_COLORS, DEFAULT_FONTS);
    }

    /**
     * Build a <code>WordRenderer</code> using the given <code>Color</code>s and
     * <code>Font</code>s.
     *
     * @param colors colors
     * @param fonts fonts
     */
    public DefaultWordRenderer(List<Color> colors, List<Font> fonts) {
        _colors.addAll(colors);
        _fonts.addAll(fonts);
    }

    /**
     * {@inheritDoc}
     *
     * Render a word onto a BufferedImage.
     */
    @Override
    public void render(final String word, BufferedImage image) {
        Graphics2D g = image.createGraphics();

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        g.setRenderingHints(hints);

        FontRenderContext frc = g.getFontRenderContext();
        int xBaseline = (int) Math.round(image.getWidth() * XOFFSET);
        int yBaseline = image.getHeight() - (int) Math.round(image.getHeight() * YOFFSET);

        char[] chars = new char[1];
        for (char c : word.toCharArray()) {
            chars[0] = c;

            g.setColor(_colors.get(RAND.nextInt(_colors.size())));

            int choiceFont = RAND.nextInt(_fonts.size());
            Font font = _fonts.get(choiceFont);
            g.setFont(font);

            GlyphVector gv = font.createGlyphVector(frc, chars);
            g.drawChars(chars, 0, chars.length, xBaseline, yBaseline);

            int width = (int) gv.getVisualBounds().getWidth();
            xBaseline = xBaseline + width;
        }
    }
}
