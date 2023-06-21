package test.graphic;

import graphic.controller.CanvaController;
import graphic.model.ToolContext;
import graphic.model.canva.Canva;
import graphic.model.tools.BucketTool;
import graphic.model.tools.RubberTool;
import graphic.model.tools.Toolbox;
import graphic.view.CanvaComponent;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestRubberTool {
    private Toolbox toolbox;
    private ToolContext tc;

    @Before
    public void setup() {
        this.toolbox = new Toolbox();
        RubberTool rubberTool = new RubberTool();
        this.toolbox.addObserver(rubberTool);
        this.toolbox.addTool(rubberTool);
        this.toolbox.setActiveTool(1);
        Canva canva = new Canva();
        canva.setBufferedImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB));
        canva.setGraphics2D((Graphics2D) canva.getBufferedImage().getGraphics());
        canva.getGraphics2D().setPaint(Color.RED);
        canva.getGraphics2D().fillRect(0, 0, 500, 500);
        CanvaController canvaController = new CanvaController(canva);
        CanvaComponent canvaComponent = new CanvaComponent(this.toolbox, canvaController, null, null);
        this.tc = new ToolContext();
        this.tc.setCanva(canva);
        this.tc.setCanvaComponent(canvaComponent);
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
        ImageIO.write(this.tc.getCanva().getBufferedImage(), "png", new File("pute.png"));
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
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
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
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
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
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
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }
}
