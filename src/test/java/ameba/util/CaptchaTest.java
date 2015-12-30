package ameba.util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author icode
 */
public class CaptchaTest {

    @Test
    public void genCaptcha() throws IOException {
        Images.Captcha captcha = Images.captcha().addNoise("#00000").addNoise("#16AD85").addNoise("#16AD85");
        System.out.println("captcha text: " + captcha.getText());
        IOUtils.write(captcha, new File("target/test-classes/captcha.png"));
    }

}
