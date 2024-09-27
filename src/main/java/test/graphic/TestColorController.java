package test.graphic;

import graphic.controller.ColorController;
import graphic.model.color.ColorModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertEquals;

public class TestColorController {

    ColorController colorController;

    @Before
    public void setup() {
        ColorModel colorModel = new ColorModel();
        this.colorController = new ColorController(colorModel);
    }

    @Test
    public void testGetPrimaryColor() {
        assertEquals(Color.BLACK ,this.colorController.getPrimaryColor());
    }
    @Test
    public void testGetSecondaryColor() {
        assertEquals(Color.WHITE ,this.colorController.getSecondaryColor());
    }

    @Test
    public void testGetIsPrimaryColor() {
        assertEquals(true ,this.colorController.getIsPrimaryColor());
    }
    @Test
    public void testSetPrimaryColor() {
        this.colorController.setPrimaryColor(Color.RED);
        assertEquals(Color.RED ,this.colorController.getPrimaryColor());
    }
    @Test
    public void testSetSecondaryColor() {
        this.colorController.setSecondaryColor(Color.GREEN);
        assertEquals(Color.GREEN ,this.colorController.getSecondaryColor());
    }

    @Test
    public void testSetIsPrimaryColor() {
        this.colorController.setIsPrimaryColor(false);
        assertEquals(false ,this.colorController.getIsPrimaryColor());
    }
}
