package Test.graphic;

import graphic.controller.ColorController;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.PickerTool;
import graphic.model.tools.Toolbox;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.assertEquals;

public class TestPickerTool {
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

        PickerTool pickerTool = new PickerTool(this.colorController);
        this.toolbox.addObserver(pickerTool);

        this.toolbox.addTool(pickerTool);
        this.tif = new ToolInternalFrame(this.toolbox, this.colorController, this.colorModel);
        this.canva = new Canva(this.toolbox, null, null);
        this.canva.setBufferedImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB));
        this.canva.setG2((Graphics2D) this.canva.getBufferedImage().getGraphics());
        this.canva.getG2().setPaint(Color.WHITE);
        this.canva.getG2().fillRect(0, 0, 500, 500);
        this.canva.getG2().setPaint(Color.PINK);
        this.canva.getG2().drawLine(20, 20, 25, 25);
        this.canva.getG2().setPaint(Color.RED);
        this.canva.getG2().drawLine(50, 50, 80, 80);
        this.canva.repaint();
        this.toolbox.setActiveTool(1);
    }

    @Test
    public void testUpdatePrimaryColor() {
        this.colorModel.setIsPrimaryColor(false);
        this.toolbox.getActiveTool().execute(20, 20, 20, 20, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON1_DOWN_MASK, 30, false, true, null);
        assertEquals(Color.PINK, this.colorModel.getPrimaryColor());
        assertEquals(true, this.colorModel.getIsPrimaryColor());
    }

    @Test
    public void testUpdateSecondaryColor() {
        this.colorModel.setIsPrimaryColor(true);
        this.toolbox.getActiveTool().execute(50, 50, 50, 50, this.canva.getBufferedImage(), this.canva.getG2(), MouseEvent.BUTTON3_DOWN_MASK, 30, false, true, null);
        assertEquals(Color.RED, this.colorModel.getSecondaryColor());
        assertEquals(false, this.colorModel.getIsPrimaryColor());
    }
}
