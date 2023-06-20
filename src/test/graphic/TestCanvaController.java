package test.graphic;

import graphic.controller.CanvaController;
import graphic.model.canva.Canva;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TestCanvaController {

    private Canva canva;
    private CanvaController canvaController;

    @Before
    public void setup() {
        this.canva = new Canva(null, new JLabel(), new JLabel());
        this.canva.setBufferedImage(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        this.canva.setG2((Graphics2D) this.canva.getBufferedImage().getGraphics());
        this.canva.getG2().setPaint(Color.BLACK);
        this.canva.getG2().fillRect(0, 0, 64, 64);
        this.canva.getG2().setPaint(Color.WHITE);
        this.canva.getG2().drawLine(10, 10, 20, 20);
        this.canva.getG2().setPaint(Color.BLUE);
        this.canva.getG2().drawLine(20, 20, 40, 20);
        this.canvaController = new CanvaController(this.canva);
    }

    @Test
    public void testGetWidth() {
        assertEquals(64, this.canvaController.getCanvaWidth());
    }

    @Test
    public void testGetHeight() {
        assertEquals(64, this.canvaController.getCanvaHeight());
    }

    @Test
    public void testFlipVertical() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestFlipVertical.png");
        this.canvaController.flipImageVertical();
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testFlipHorizontal() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestFlipHorizontal.png");
        this.canvaController.flipImageHorizontal();
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testBlackAndWhite() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestBlackWhite.png");
        this.canvaController.blackAndWhiteTransform();
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testResize() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestResize.png");
        this.canvaController.resizeCanva(128, 64);
        assertEquals(ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage), true);
        assertEquals(128, this.canvaController.getCanvaWidth());
    }

    @Test
    public void exportTest() {
        File file = new File("src/test/graphic/imageTest/canvaController/TestExport.png");
        if (file != null) {
            file.delete();
        }
        this.canvaController.export("src/test/graphic/imageTest/canvaController/TestExport.png");
        assertNotNull(LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestExport.png"));
        if (file != null) {
            file.delete();
        }
    }

    @Test
    public void testImport() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestImportImage.png");
        this.canvaController.importFile(new File("src/test/graphic/imageTest/canvaController/TestImportImage.png"));
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testPaste() throws IOException {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestPasteImage.png");
        this.canvaController.pasteImage(ImageIO.read(new File("src/test/graphic/imageTest/canvaController/PasteImage.png")));
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testUndo() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestPasteImage.png");
        this.canva.setBufferedImage(LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestPasteImage.png"));
        this.canva.setBufferedImage(LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestResize.png"));
        this.canvaController.undo();
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }

    @Test
    public void testRedo() {
        BufferedImage expectedImage = LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestResize.png");
        this.canva.setBufferedImage(LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestPasteImage.png"));
        this.canva.setBufferedImage(LoadImage.loadImage("src/test/graphic/imageTest/canvaController/TestResize.png"));
        this.canvaController.undo();
        this.canvaController.redo();
        assertEquals(true, ImageComparator.areImagesSimilar(this.canva.getBufferedImage(), expectedImage));
    }
}