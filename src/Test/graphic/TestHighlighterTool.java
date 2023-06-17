package Test.graphic;

import graphic.controller.ColorController;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.HighlighterTool;
import graphic.model.tools.Toolbox;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestHighlighterTool {

    private Canva canva;
    private ColorController colorController;
    private ColorModel colorModel;
    private Toolbox toolbox;
    private ToolInternalFrame tif;

    @Before
    public void setup() {
        this.colorModel = new ColorModel();
        this.colorController = new ColorController(this.colorModel);
        this.toolbox = new Toolbox();
        HighlighterTool highlighterTool = new HighlighterTool();
        this.toolbox.addObserver(highlighterTool);

        this.toolbox.addTool(highlighterTool);
        this.toolbox.setActiveTool(1);
        this.tif = new ToolInternalFrame(this.toolbox, this.colorController, this.colorModel);
        this.canva = new Canva(this.toolbox, null, null);
        this.canva.setBufferedImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB));
        this.canva.setG2((Graphics2D) this.canva.getBufferedImage().getGraphics());
        this.canva.getG2().setPaint(Color.WHITE);
        this.canva.getG2().fillRect(0, 0, 500, 500);
        this.canva.repaint();
    }

    @Test
    public void testHighlighterLine() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/highlighterTool/TestLine.png");
        this.toolbox.getActiveTool().execute(40, 90, 200, 200, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, true, true, null);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testHighlighterPoint() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/highlighterTool/TestPoint.png");
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, true, true, null);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testChangePrimaryColor() throws IOException {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/highlighterTool/TestChangePrimaryColor.png");
        this.colorController.setPrimaryColor(Color.BLUE);
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, false, true, null);
        this.colorController.setPrimaryColor(Color.RED);
        this.toolbox.getActiveTool().execute(24, 83, 100, 223, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, false, true, null);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testChangeSecondaryColor() throws IOException {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/highlighterTool/TestChangeSecondaryColor.png");
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON3_DOWN_MASK, 30, false, true, null);

        this.colorController.setSecondaryColor(Color.PINK);
        this.colorController.setIsPrimaryColor(false);
        this.toolbox.getActiveTool().execute(80, 80, 80, 80, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON3_DOWN_MASK, 30, false, true, null);

        this.colorController.setSecondaryColor(Color.RED);
        this.toolbox.getActiveTool().execute(24, 83, 100, 223, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON3_DOWN_MASK, 30, false, true, null);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }
}
