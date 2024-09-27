package test.graphic;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.ToolContext;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.Toolbox;
import graphic.view.CanvaComponent;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.assertEquals;

public class TestPencilTool {

    private Toolbox toolbox;
    private ColorController colorController;
    private ToolContext tc;

    @Before
    public void setup() {
        ColorModel colorModel = new ColorModel();
        this.colorController = new ColorController(colorModel);
        this.toolbox = new Toolbox();
        ToolInternalFrame tif = new ToolInternalFrame(this.toolbox, this.colorController, colorModel);
        Canva canva = new Canva();
        canva.setBufferedImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB));
        canva.setGraphics2D((Graphics2D) canva.getBufferedImage().getGraphics());
        canva.getGraphics2D().setPaint(Color.WHITE);
        canva.getGraphics2D().fillRect(0, 0, 500, 500);
        CanvaController canvaController = new CanvaController(canva);
        CanvaComponent canvaComponent = new CanvaComponent(this.toolbox, canvaController, null, null);
        this.tc = new ToolContext();
        this.tc.setCanva(canva);
        this.tc.setCanvaComponent(canvaComponent);
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
        assertEquals(ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage), true);
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
        assertEquals(ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage), true);
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
        assertEquals(ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage), true);
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
        assertEquals(ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage), true);
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
        assertEquals(ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage), true);
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
        assertEquals(ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage), true);
    }
}