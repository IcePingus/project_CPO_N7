package test.graphic;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestLoadImage {

    private BufferedImage image1;
    private BufferedImage image2;

    @Before
    public void setup() {
        this.image1 = LoadImage.loadImage("src/test/graphic/imageTest/textTool/TestWrite.png");
    }

    @Test
    public void testLoadImage() {
        assertNotNull(this.image1);
    }

    @Test
    public void testNullImage() {
        assertNull(this.image2);
    }

    @Test(expected = RuntimeException.class)
    public void testIOException() {
        this.image2 = LoadImage.loadImage("inexistantFile.png");
    }
}
