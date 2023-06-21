package test.graphic;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.ToolContext;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.PickerTool;
import graphic.model.tools.Toolbox;
import graphic.view.CanvaComponent;
import graphic.view.ToolInternalFrame;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static junit.framework.TestCase.assertEquals;

public class TestPickerTool {
    private ColorModel colorModel;
    private Toolbox toolbox;
    private ToolContext tc;

    @Before
    public void setup() {
        this.colorModel = new ColorModel();
        ColorController colorController = new ColorController(this.colorModel);
        this.toolbox = new Toolbox();
        PickerTool pickerTool = new PickerTool(colorController);
        this.toolbox.addObserver(pickerTool);
        this.toolbox.addTool(pickerTool);
        this.toolbox.setActiveTool(1);

        ToolInternalFrame tif = new ToolInternalFrame(this.toolbox, colorController, this.colorModel);
        Canva canva = new Canva();
        canva.setBufferedImage(new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB));
        canva.setGraphics2D((Graphics2D) canva.getBufferedImage().getGraphics());
        canva.getGraphics2D().setPaint(Color.WHITE);
        canva.getGraphics2D().fillRect(0, 0, 500, 500);
        canva.getGraphics2D().setPaint(Color.PINK);
        canva.getGraphics2D().drawLine(20, 20, 25, 25);
        canva.getGraphics2D().setPaint(Color.RED);
        canva.getGraphics2D().drawLine(50, 50, 80, 80);
        CanvaController canvaController = new CanvaController(canva);
        CanvaComponent canvaComponent = new CanvaComponent(this.toolbox, canvaController, null, null);
        this.tc = new ToolContext();
        this.tc.setCanva(canva);
        this.tc.setCanvaComponent(canvaComponent);
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