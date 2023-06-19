package test.graphic;

import graphic.controller.ColorController;
import graphic.model.ToolContext;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.PickerTool;
import graphic.model.tools.Toolbox;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import javax.tools.Tool;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.assertEquals;

public class TestPickerTool {
    private Canva canva;
    private ColorController colorController;
    private ColorModel colorModel;
    private Toolbox toolbox;
    private ToolContext tc;

    @Before
    public void setup() {
        this.colorModel = new ColorModel();
        this.colorController = new ColorController(this.colorModel);
        this.toolbox = new Toolbox();

        PickerTool pickerTool = new PickerTool(this.colorController);
        this.toolbox.addObserver(pickerTool);

        this.toolbox.addTool(pickerTool);
        ToolInternalFrame tif = new ToolInternalFrame(this.toolbox, this.colorController, this.colorModel);
        this.canva = new Canva(this.toolbox, null, null);
        this.canva.setBufferedImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB));
        this.canva.setG2((Graphics2D) this.canva.getBufferedImage().getGraphics());
        this.canva.getG2().setPaint(Color.WHITE);
        this.canva.getG2().fillRect(0, 0, 500, 500);
        this.canva.getG2().setPaint(Color.PINK);
        this.canva.getG2().drawLine(20, 20, 25, 25);
        this.canva.getG2().setPaint(Color.RED);
        this.canva.getG2().drawLine(50, 50, 80, 80);
        this.canva.repaint();
        this.toolbox.setActiveTool(1);
        this.tc = new ToolContext();
        this.tc.setCanva(this.canva);
    }

    @Test
    public void testUpdatePrimaryColor() {
        this.tc.setOldX(20);
        this.tc.setOldY(20);
        this.tc.setCurrentX(20);
        this.tc.setCurrentY(20);
        this.tc.setClick(MouseEvent.BUTTON1_DOWN_MASK);
        this.colorModel.setIsPrimaryColor(false);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(Color.PINK, this.colorModel.getPrimaryColor());
        assertEquals(true, this.colorModel.getIsPrimaryColor());
    }

    @Test
    public void testUpdateSecondaryColor() {
        this.tc.setOldX(50);
        this.tc.setOldY(50);
        this.tc.setCurrentX(50);
        this.tc.setCurrentY(50);
        this.tc.setClick(MouseEvent.BUTTON3_DOWN_MASK);
        this.colorModel.setIsPrimaryColor(true);
        this.toolbox.getActiveTool().execute(this.tc);
        assertEquals(Color.RED, this.colorModel.getSecondaryColor());
        assertEquals(false, this.colorModel.getIsPrimaryColor());
    }
}