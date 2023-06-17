package Test.graphic;

import graphic.controller.ColorController;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.TextTool;
import graphic.model.tools.Toolbox;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.assertEquals;

public class TestTextTool {
    private Canva canva;
    private ColorController colorController;
    private ColorModel colorModel;
    private Toolbox toolbox;
    private TextTool textTool;
    private ToolInternalFrame tif;

    @Before
    public void setup() {
        this.colorModel = new ColorModel();
        this.colorController = new ColorController(this.colorModel);
        this.toolbox = new Toolbox();
        this.textTool = new TextTool();
        this.toolbox.addObserver(this.textTool);

        this.toolbox.addTool(this.textTool);
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
    public void testWrite() {
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/textTool/TestWrite.png");
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 40, false, true, this.canva);
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
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testPrimaryColor() {
        this.colorModel.setPrimaryColor(Color.BLUE);
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/textTool/TestPrimaryColor.png");
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 40, false, true, this.canva);
        KeyEvent keyPressEventS = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        this.textTool.keyTyped(keyPressEventS);
        this.textTool.keyTyped(keyPressEventS);
        this.textTool.keyTyped(keyPressEventS);

        KeyEvent keyPressEvent = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        this.textTool.keyTyped(keyPressEvent);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testSecondaryColor() {
        this.colorModel.setSecondaryColor(Color.ORANGE);
        this.colorModel.setPrimaryColor(Color.BLUE);
        BufferedImage expectedImage = LoadImage.loadImage("src/Test/graphic/ImageTest/textTool/TestSecondaryColor.png");
        this.toolbox.getActiveTool().execute(40, 40, 40, 40, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON3_DOWN_MASK, 40, false, true, this.canva);
        KeyEvent keyPressEventS = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        this.textTool.keyTyped(keyPressEventS);
        this.textTool.keyTyped(keyPressEventS);
        this.textTool.keyTyped(keyPressEventS);

        KeyEvent keyPressEvent = new KeyEvent(this.textTool.getJtextField(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        this.textTool.keyTyped(keyPressEvent);
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }
}
