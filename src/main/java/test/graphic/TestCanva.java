package test.graphic;

import graphic.model.canva.Canva;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.*;

public class TestCanva {

    Canva canva;

    @Before
    public void setup() {
        this.canva = new Canva();
    }

    @Test
    public void testGraphics2D() {
        assertNull(this.canva.getGraphics2D());
        Graphics2D graphics2D = (Graphics2D) new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB).getGraphics();
        this.canva.setGraphics2D(graphics2D);
        assertEquals(graphics2D ,this.canva.getGraphics2D());
    }

    @Test
    public void testCurrentIndex() {
        assertEquals(0, this.canva.getCurrentIndex());
        this.canva.setCurrentIndex(3);
        assertEquals(3, this.canva.getCurrentIndex());
    }

    @Test
    public void testGetImageStates() {
        assertEquals(0, this.canva.getImageStates().size());
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        this.canva.setBufferedImage(bufferedImage);
        assertEquals(1, this.canva.getImageStates().size());
        assertEquals(bufferedImage, this.canva.getImageStates().get(0));
    }

    @Test
    public void testBufferedImage() {
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        this.canva.setBufferedImage(bufferedImage);
        assertEquals(bufferedImage, this.canva.getBufferedImage());
        this.canva.nextBufferedImage();
        assertEquals(2, this.canva.getImageStates().size());
        assertNotSame(bufferedImage, this.canva.getBufferedImage());
    }

}
