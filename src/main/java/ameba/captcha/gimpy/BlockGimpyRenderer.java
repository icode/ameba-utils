package ameba.captcha.gimpy;

import com.jhlabs.image.BlockFilter;

import java.awt.image.BufferedImage;

/**
 * <p>BlockGimpyRenderer class.</p>
 *
 * @author icode
 * @version $Id: $Id
 */
public class BlockGimpyRenderer implements GimpyRenderer {

    private static final int DEF_BLOCK_SIZE = 3;
    private final int _blockSize;

    /**
     * <p>Constructor for BlockGimpyRenderer.</p>
     */
    public BlockGimpyRenderer() {
        this(DEF_BLOCK_SIZE);
    }

    /**
     * <p>Constructor for BlockGimpyRenderer.</p>
     *
     * @param blockSize a int.
     */
    public BlockGimpyRenderer(int blockSize) {
        _blockSize = blockSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void gimp(BufferedImage image) {
        BlockFilter filter = new BlockFilter();
        filter.setBlockSize(_blockSize);
        filter.filter(image, image);
    }
}
