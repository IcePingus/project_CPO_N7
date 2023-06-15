package Test.graphic;

import graphic.controller.ColorController;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.Toolbox;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.assertEquals;

public class TestPencilTool {

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
        this.tif = new ToolInternalFrame(this.toolbox, this.colorController, this.colorModel);
        this.canva = new Canva(this.toolbox);
        this.canva.setBufferedImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB));
        this.canva.setG2((Graphics2D) this.canva.getBufferedImage().getGraphics());
        this.canva.getG2().setPaint(Color.WHITE);
        this.canva.getG2().fillRect(0, 0, 500, 500);
        this.canva.repaint();
    }

    @Test
    public void testPencilLineSquare() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/pencilTool/TestLineSquare.png");
        this.toolbox.getActiveTool().execute(40, 90, 200, 200, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, true, true, null);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testPencilLineRound() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/pencilTool/TestLineRound.png");
        this.toolbox.getActiveTool().execute(40, 90, 200, 200, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, false, true, null);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testPencilPointSquare() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/pencilTool/TestPointSquare.png");
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, true, true, null);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testPencilPointRound() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/pencilTool/TestPointRound.png");
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, false, true, null);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testChangePrimaryColor() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/pencilTool/TestChangePrimaryColor.png");
        this.colorController.setPrimaryColor(Color.BLUE);
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, false, true, null);
        this.colorController.setPrimaryColor(Color.RED);
        this.toolbox.getActiveTool().execute(24, 83, 100, 223, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, false, true, null);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testChangeSecondaryColor() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/pencilTool/TestChangeSecondaryColor.png");
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON3_DOWN_MASK, 30, false, true, null);

        this.colorController.setSecondaryColor(Color.PINK);
        this.colorController.setIsPrimaryColor(false);
        this.toolbox.getActiveTool().execute(80, 80, 80, 80, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON3_DOWN_MASK, 30, false, true, null);

        this.colorController.setSecondaryColor(Color.RED);
        this.toolbox.getActiveTool().execute(24, 83, 100, 223, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON3_DOWN_MASK, 30, false, true, null);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }
}
