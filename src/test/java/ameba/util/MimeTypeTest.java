package ameba.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author icode
 */
public class MimeTypeTest {
    @Test
    public void getMimeType() {
        assertEquals(MimeType.getByFilename("main.js"), "application/javascript");
    }
}
