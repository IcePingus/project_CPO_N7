package test.graphic;

import graphic.exception.nullImageException;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.assertEquals;

public class TestImageComparator {

    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage image3;
    private BufferedImage image4;

    @Before
    public void setup() {
        this.image1 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics1 = (Graphics2D) image1.getGraphics();
        graphics1.drawLine(10, 10, 20, 20);

        this.image2 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2 = (Graphics2D) image2.getGraphics();
        graphics2.drawLine(10, 10, 20, 20);

        this.image3 = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics3 = (Graphics2D) image3.getGraphics();
        graphics3.drawLine(20, 20, 60, 60);
    }

    @Test
    public void testSameFile() {
        assertEquals(true, ImageComparator.areImagesSimilar(this.image1, this.image1));
    }

    @Test
    public void testSameImage() {
        assertEquals(true, ImageComparator.areImagesSimilar(this.image1, this.image2));
    }

    @Test
    public void testDifferentImage() {
        assertEquals(false, ImageComparator.areImagesSimilar(this.image1, this.image3));
    }

    @Test(expected = nullImageException.class)
    public void testNullImageException() {
        ImageComparator.areImagesSimilar(this.image4, this.image3);
        ImageComparator.areImagesSimilar(this.image3, this.image4);
        ImageComparator.areImagesSimilar(this.image4, this.image4);
    }
}
