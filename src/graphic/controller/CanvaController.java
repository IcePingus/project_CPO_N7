package graphic.controller;

import com.nitido.utils.toaster.Toaster;
import graphic.model.tools.Toolbox;
import graphic.model.canva.Canva;
import graphic.view.SelectionPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class CanvaController implements Observer {

    private final Canva canva;

    public CanvaController(Canva canva) {
        this.canva = canva;
    }

    public int getCanvaWidth() {
        return this.canva.getWidth();
    }

    public int getCanvaHeight() {
        return this.canva.getHeight();
    }

    public void exportPNG() {
        Toaster toasterManager = new Toaster();
        try {
            toasterManager.showToaster("Image exported !");
            ImageIO.write(this.canva.getBufferedImage(), "png", new File("export/image.png"));
        } catch (IOException e) {
            toasterManager.showToaster("There was an error while attempting to save the image.");
            e.printStackTrace();
        }
    }

    public void flipImageHorizontal() {
        AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
        affineTransform.translate(-this.canva.getBufferedImage().getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.canva.setBufferedImage(op.filter(this.canva.getBufferedImage(), null));
    }

    public void flipImageVertical() {
        AffineTransform affineTransform = AffineTransform.getScaleInstance(1, -1);
        affineTransform.translate(0, -this.canva.getBufferedImage().getHeight());
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.canva.setBufferedImage(op.filter(this.canva.getBufferedImage(), null));
    }

    public void quit(JFrame frame) {
        String[] options = new String[] {"Yes and save the image", "Yes without saving", "No"};
        int resultOptionPane = JOptionPane.showOptionDialog(null, "Do you really want to quit ?", "Exit",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (resultOptionPane == 0) {
            this.exportPNG();
        }
        if (resultOptionPane == 0 || resultOptionPane == 1) {
            frame.setContentPane(new SelectionPanel(frame));
            frame.validate();
            frame.repaint();
        }
    }

    public void blackAndWhiteTransform() {
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(this.canva.getBufferedImage(), this.canva.getBufferedImage());
        this.canva.repaint();
    }

    public void resizeCanva(int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, this.canva.getBufferedImage().getType());
        this.canva.setG2(resizedImage.createGraphics());
        this.canva.getG2().drawImage(this.canva.getBufferedImage(), 0, 0, width, height, null);
        this.canva.setBufferedImage(resizedImage);
    }

    public void importImage(JFrame frame) {
        String filename = File.separator+"tmp";
        JFileChooser fileChooser = new JFileChooser(new File(filename));

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Image");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return null;
            }
        });

        fileChooser.showOpenDialog(frame);
        try {
            this.canva.setBufferedImage(ImageIO.read(fileChooser.getSelectedFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        this.canva.getG2().setPaint(Color.WHITE);
        this.canva.getG2().fillRect(0, 0, this.canva.getWidth(), this.canva.getHeight());
        this.canva.repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Toolbox) {
            this.canva.setToolbox((Toolbox) o);
            this.canva.setMouseMotionListener();
        }
    }

}
