package test.graphic;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.ToolContext;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.HighlighterTool;
import graphic.model.tools.Toolbox;
import graphic.view.CanvaComponent;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestHighlighterTool {

    private ColorController colorController;
    private Toolbox toolbox;
    private ToolContext tc;

    @Before
    public void setup() {
        ColorModel colorModel = new ColorModel();
        this.colorController = new ColorController(colorModel);
        this.toolbox = new Toolbox();
        HighlighterTool highlighterTool = new HighlighterTool();
        this.toolbox.addObserver(highlighterTool);

        this.toolbox.addTool(highlighterTool);
        this.toolbox.setActiveTool(1);
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
        this.tc.setSize(30);
        this.tc.setClick(MouseEvent.BUTTON1_DOWN_MASK);
    }

    @Test
    public void testHighlighterLine() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/highlighterTool/TestLine.png");
        this.tc.setOldX(40);
        this.tc.setOldY(90);
        this.tc.setCurrentX(200);
        this.tc.setCurrentY(200);
        this.tc.setSquare(true);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }

    @Test
    public void testHighlighterPoint() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/highlighterTool/TestPoint.png");
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.tc.setSquare(true);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }

    @Test
    public void testChangePrimaryColor() throws IOException {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/highlighterTool/TestChangePrimaryColor.png");
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.tc.setSquare(false);
        this.colorController.setPrimaryColor(Color.BLUE);
        this.toolbox.getActiveTool().execute(this.tc);
        this.colorController.setPrimaryColor(Color.RED);
        this.tc.setOldX(24);
        this.tc.setOldY(83);
        this.tc.setCurrentX(100);
        this.tc.setCurrentY(223);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }

    @Test
    public void testChangeSecondaryColor() throws IOException {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/highlighterTool/TestChangeSecondaryColor.png");
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
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
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }
}