package test.graphic;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.ToolContext;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.BucketTool;
import graphic.model.tools.Toolbox;
import graphic.view.CanvaComponent;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.assertEquals;

public class TestBucketTool {
    private ColorController colorController;
    private Toolbox toolbox;
    private ToolContext tc;

    @Before
    public void setup() {
        ColorModel colorModel = new ColorModel();
        this.colorController = new ColorController(colorModel);
        this.toolbox = new Toolbox();
        BucketTool bucketTool = new BucketTool();
        this.toolbox.addObserver(bucketTool);
        this.toolbox.addTool(bucketTool);
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
    }

    @Test
    public void testFillCanva() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/bucketTool/TestFillCanva.png");
        this.colorController.setPrimaryColor(Color.MAGENTA);
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }

    @Test
    public void testFillShape() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/bucketTool/TestFillShape.png");
        this.tc.getCanva().getGraphics2D().setPaint(Color.BLACK);
        this.tc.getCanva().getGraphics2D().drawRect(200, 200, 20, 20);
        this.colorController.setPrimaryColor(Color.ORANGE);
        this.tc.setOldX(210);
        this.tc.setOldY(210);
        this.tc.setCurrentX(210);
        this.tc.setCurrentY(210);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }

    @Test
    public void testFillSecondaryColor() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/bucketTool/TestFillSecondaryColor.png");
        this.colorController.setIsPrimaryColor(false);
        this.colorController.setSecondaryColor(Color.ORANGE);
        this.colorController.setPrimaryColor(Color.YELLOW);
        this.tc.setOldX(210);
        this.tc.setOldY(210);
        this.tc.setCurrentX(210);
        this.tc.setCurrentY(210);
        this.tc.setClick(MouseEvent.BUTTON3_DOWN_MASK);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }
}