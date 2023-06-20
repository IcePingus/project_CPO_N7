package test.graphic;

import graphic.model.ToolContext;
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
    private ToolContext tc;

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
        this.tc = new ToolContext();
        this.tc.setCanva(this.canva);
    }

    @Test
    public void testEraseLineSquare() throws IOException {
        this.tc.setOldX(40);
        this.tc.setOldY(90);
        this.tc.setCurrentX(200);
        this.tc.setCurrentY(200);
        this.tc.setSize(30);
        this.tc.setSquare(true);
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/rubberTool/TestLineSquare.png");
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testEraseLineRound() {
        this.tc.setOldX(40);
        this.tc.setOldY(90);
        this.tc.setCurrentX(200);
        this.tc.setCurrentY(200);
        this.tc.setSize(30);
        this.tc.setSquare(false);
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/rubberTool/TestLineRound.png");
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testErasePointSquare() {
        this.tc.setOldX(90);
        this.tc.setOldY(90);
        this.tc.setCurrentX(90);
        this.tc.setCurrentY(90);
        this.tc.setSize(30);
        this.tc.setSquare(true);
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/rubberTool/TestErasePointSquare.png");
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testErasePointRound() {
        this.tc.setOldX(90);
        this.tc.setOldY(90);
        this.tc.setCurrentX(90);
        this.tc.setCurrentY(90);
        this.tc.setSize(30);
        this.tc.setSquare(false);
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/rubberTool/TestErasePointRound.png");
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }
}
