package test.graphic;

import graphic.controller.ColorController;
import graphic.model.ToolContext;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.BucketTool;
import graphic.model.tools.Toolbox;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestBucketTool {
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
        BucketTool bucketTool = new BucketTool();
        this.toolbox.addObserver(bucketTool);

        this.toolbox.addTool(bucketTool);
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
    public void testFillCanva() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/bucketTool/TestFillCanva.png");
        this.toolbox.setActiveTool(1);
        this.colorModel.setPrimaryColor(Color.MAGENTA);
        this.toolbox.getActiveTool().execute(this.tc);
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testFillShape() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/bucketTool/TestFillShape.png");
        this.canva.getG2().setPaint(Color.BLACK);
        this.canva.getG2().drawRect(200, 200, 20, 20);
        this.toolbox.setActiveTool(1);
        this.colorModel.setPrimaryColor(Color.ORANGE);
        this.tc.setOldX(210);
        this.tc.setOldY(210);
        this.tc.setCurrentX(210);
        this.tc.setCurrentY(210);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testFillSecondaryColor() throws IOException {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/bucketTool/TestFillSecondaryColor.png");
        this.toolbox.setActiveTool(1);
        this.colorModel.setIsPrimaryColor(false);
        this.colorModel.setSecondaryColor(Color.ORANGE);
        this.colorModel.setPrimaryColor(Color.YELLOW);
        this.tc.setOldX(210);
        this.tc.setOldY(210);
        this.tc.setCurrentX(210);
        this.tc.setCurrentY(210);
        this.tc.setClick(MouseEvent.BUTTON3_DOWN_MASK);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }
}