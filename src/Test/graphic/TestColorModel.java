package Test.graphic;

import graphic.model.color.ColorModel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertEquals;

public class TestColorModel {

    ColorModel colorModel;

    @Before
    public void setup() {
        this.colorModel = new ColorModel();
    }

    @Test
    public void testGetPrimaryColor() {
        assertEquals(Color.BLACK ,this.colorModel.getPrimaryColor());
    }
    @Test
    public void testGetSecondaryColor() {
        assertEquals(Color.WHITE ,this.colorModel.getSecondaryColor());
    }

    @Test
    public void testGetIsPrimaryColor() {
        assertEquals(true ,this.colorModel.getIsPrimaryColor());
    }
    @Test
    public void testSetPrimaryColor() {
        this.colorModel.setPrimaryColor(Color.RED);
        assertEquals(Color.RED ,this.colorModel.getPrimaryColor());
    }
    @Test
    public void testSetSecondaryColor() {
        this.colorModel.setSecondaryColor(Color.GREEN);
        assertEquals(Color.GREEN ,this.colorModel.getSecondaryColor());
    }

    @Test
    public void testSetIsPrimaryColor() {
        this.colorModel.setIsPrimaryColor(false);
        assertEquals(false ,this.colorModel.getIsPrimaryColor());
    }
}
