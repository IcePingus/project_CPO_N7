package test.graphic;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.ToolContext;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.TextTool;
import graphic.model.tools.Toolbox;
import graphic.view.CanvaComponent;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.assertEquals;

public class TestTextTool {
    private ColorController colorController;
    private Toolbox toolbox;
    private TextTool textTool;
    private ToolContext tc;

    @Before
    public void setup() {
        ColorModel colorModel = new ColorModel();
        this.colorController = new ColorController(colorModel);
        this.toolbox = new Toolbox();
        this.textTool = new TextTool();
        this.toolbox.addObserver(this.textTool);

        this.toolbox.addTool(this.textTool);
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
    public void testWrite() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/textTool/TestWrite.png");
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.tc.setSize(40);
        this.toolbox.getActiveTool().execute(this.tc);
        KeyEvent keyPressEventS = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        KeyEvent keyPressEventA = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        KeyEvent keyPressEventL = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_L, 'L');
        KeyEvent keyPressEventU = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_U, 'U');
        KeyEvent keyPressEventT = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_T, 'T');
        KeyEvent keyPressEventSPACE = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, ' ');

        KeyEvent keyPressEventB = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_B, 'B');
        KeyEvent keyPressEventC = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_C, 'C');
        KeyEvent keyPressEventK = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_K, 'K');
        KeyEvent keyPressEventE = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_E, 'E');

        this.textTool.keyTyped(keyPressEventS);
        this.textTool.keyTyped(keyPressEventA);
        this.textTool.keyTyped(keyPressEventL);
        this.textTool.keyTyped(keyPressEventU);
        this.textTool.keyTyped(keyPressEventT);
        this.textTool.keyTyped(keyPressEventSPACE);
        this.textTool.keyTyped(keyPressEventB);
        this.textTool.keyTyped(keyPressEventU);
        this.textTool.keyTyped(keyPressEventC);
        this.textTool.keyTyped(keyPressEventK);
        this.textTool.keyTyped(keyPressEventE);
        this.textTool.keyTyped(keyPressEventT);

        KeyEvent keyPressEvent = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        this.textTool.keyTyped(keyPressEvent);
            assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }

    @Test
    public void testPrimaryColor() {
        this.colorController.setPrimaryColor(Color.BLUE);
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/textTool/TestPrimaryColor.png");
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.tc.setSize(40);
        this.toolbox.getActiveTool().execute(this.tc);
        KeyEvent keyPressEventS = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        this.textTool.keyTyped(keyPressEventS);
        this.textTool.keyTyped(keyPressEventS);
        this.textTool.keyTyped(keyPressEventS);

        KeyEvent keyPressEvent = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        this.textTool.keyTyped(keyPressEvent);
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }

    @Test
    public void testSecondaryColor() {
        this.colorController.setSecondaryColor(Color.ORANGE);
        this.colorController.setPrimaryColor(Color.BLUE);
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/textTool/TestSecondaryColor.png");
        this.tc.setOldX(40);
        this.tc.setOldY(40);
        this.tc.setCurrentX(40);
        this.tc.setCurrentY(40);
        this.tc.setSize(40);
        this.tc.setClick(MouseEvent.BUTTON3_DOWN_MASK);
        this.toolbox.getActiveTool().execute(this.tc);
        KeyEvent keyPressEventS = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        this.textTool.keyTyped(keyPressEventS);
        this.textTool.keyTyped(keyPressEventS);
        this.textTool.keyTyped(keyPressEventS);

        KeyEvent keyPressEvent = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        this.textTool.keyTyped(keyPressEvent);
        assertEquals(true, ImageComparator.areImagesSimilar(this.tc.getCanva().getBufferedImage(), expectedImage));
    }
}
