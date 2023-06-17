package Test.graphic;

import graphic.model.canva.Canva;
import graphic.model.tools.RubberTool;
import graphic.model.tools.Toolbox;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestRubberTool {
    private Canva canva;
    private Toolbox toolbox;

    @Before
    public void setup() {
        this.toolbox = new Toolbox();
        this.canva = new Canva(this.toolbox, null, null);
        this.canva.setBufferedImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB));
        this.canva.setG2((Graphics2D) this.canva.getBufferedImage().getGraphics());
        this.canva.getG2().setPaint(Color.RED);
        this.canva.getG2().fillRect(0, 0, 500, 500);
        this.canva.repaint();
        this.toolbox.addTool(new RubberTool());
        this.toolbox.setActiveTool(1);
    }

    @Test
    public void testEraseLineSquare() throws IOException {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/rubberTool/TestLineSquare.png");
        this.toolbox.getActiveTool().execute(40, 90, 200, 200, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, true, true, null);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testEraseLineRound() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/rubberTool/TestLineRound.png");
        this.toolbox.getActiveTool().execute(40, 90, 200, 200, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, false, true, null);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testErasePointSquare() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/rubberTool/TestErasePointSquare.png");
        this.toolbox.getActiveTool().execute(90, 90, 90, 90, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, true, true, null);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testErasePointRound() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/rubberTool/TestErasePointRound.png");
        this.toolbox.getActiveTool().execute(90, 90, 90, 90, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, false, true, null);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }
}
