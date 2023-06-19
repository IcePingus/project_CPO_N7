package test.graphic;

import graphic.controller.ColorController;
import graphic.model.ToolContext;
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
    private ToolContext tc;

    @Before
    public void setup() {
        this.colorModel = new ColorModel();
        this.colorController = new ColorController(this.colorModel);
        this.toolbox = new Toolbox();
        this.tif = new ToolInternalFrame(this.toolbox, this.colorController, this.colorModel);
        this.canva = new Canva(this.toolbox, null, null);
        this.canva.setBufferedImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB));
        this.canva.setG2((Graphics2D) this.canva.getBufferedImage().getGraphics());
        this.canva.getG2().setPaint(Color.WHITE);
        this.canva.getG2().fillRect(0, 0, 500, 500);
        this.canva.repaint();
        this.tc = new ToolContext();
        this.tc.setCanva(this.canva);
        this.tc.setClick(MouseEvent.BUTTON1_DOWN_MASK);
    }

    @Test
    public void testPencilLineSquare() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/pencilTool/TestLineSquare.png");
        this.tc.setOldX(40);
        this.tc.setOldY(90);
        this.tc.setCurrentX(200);
        this.tc.setCurrentY(200);
        this.tc.setSize(30);
        this.tc.setSquare(true);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testPencilLineRound() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/pencilTool/TestLineRound.png");
        this.tc.setOldX(40);
        this.tc.setOldY(90);
        this.tc.setCurrentX(200);
        this.tc.setCurrentY(200);
        this.tc.setSize(30);
        this.tc.setSquare(false);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testPencilPointSquare() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/pencilTool/TestPointSquare.png");
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.tc.setSize(30);
        this.tc.setSquare(true);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testPencilPointRound() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/pencilTool/TestPointRound.png");
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.tc.setSize(30);
        this.tc.setSquare(false);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testChangePrimaryColor() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/pencilTool/TestChangePrimaryColor.png");
        this.colorController.setPrimaryColor(Color.BLUE);
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.tc.setSize(30);
        this.tc.setSquare(false);
        this.toolbox.getActiveTool().execute(this.tc);
        this.colorController.setPrimaryColor(Color.RED);
        this.tc.setOldX(24);
        this.tc.setOldY(83);
        this.tc.setCurrentX(100);
        this.tc.setCurrentY(223);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }

    @Test
    public void testChangeSecondaryColor() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/pencilTool/TestChangeSecondaryColor.png");
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.tc.setSize(30);
        this.tc.setSquare(false);
        this.tc.setClick(MouseEvent.BUTTON3_DOWN_MASK);
        this.toolbox.getActiveTool().execute(this.tc);

        this.colorController.setSecondaryColor(Color.PINK);
        this.colorController.setIsPrimaryColor(false);
        this.tc.setOldX(80);
        this.tc.setOldY(80);
        this.tc.setCurrentX(80);
        this.tc.setCurrentY(80);
        this.toolbox.getActiveTool().execute(this.tc);

        this.colorController.setSecondaryColor(Color.RED);

        this.tc.setOldX(24);
        this.tc.setOldY(83);
        this.tc.setCurrentX(100);
        this.tc.setCurrentY(223);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
    }
}