package graphic.controller;

import com.nitido.utils.toaster.Toaster;
import graphic.exception.BadFormatException;
import graphic.exception.ClipboardVoidException;
import graphic.model.CropTypes;
import graphic.model.canva.Canva;
import graphic.view.SelectionPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

/**
 * The CanvaController class is responsible for controlling the actions and behavior of the Canva.
 *
 * @author Team 3
 */
public class CanvaController {

    /**
     * The Canva instance associated with the controller.
     */
    private final Canva canva;

    /**
     * Constructs a CanvaController with the specified Canva.
     *
     * @param canva the Canva instance to associate with the controller
     */
    public CanvaController(Canva canva) {
        this.canva = canva;
    }

    /**
     * Returns the width of the Canva.
     *
     * @return the width of the Canva
     */
    public int getCanvaWidth() {
        return this.canva.getBufferedImage().getWidth();
    }

    /**
     * Returns the height of the Canva.
     *
     * @return the height of the Canva
     */
    public int getCanvaHeight() {
        return this.canva.getBufferedImage().getHeight();
    }

    /**
     * Exports the Canva image to the specified file path.
     *
     * @param path the file path to export the Canva image to
     */
    public void export(String path) {
        Toaster toasterManager = new Toaster();
        try {
            ImageIO.write(this.canva.getBufferedImage(), "png", new File(path));
            toasterManager.showToaster("Image exported!");
        } catch (IOException e) {
            toasterManager.showToaster("There was an error while attempting to save the image.");
            e.printStackTrace();
        }
    }

    /**
     * Prompts the user to choose the export path and exports the Canva image.
     * Displays a toaster message if the chosen file format is not supported.
     *
     * @throws BadFormatException if the chosen file format is not supported
     */
    public void chooseExportPath() throws BadFormatException {
        Toaster toasterManager = new Toaster();
        JFileChooser jfc = new JFileChooser();
        int retVal = jfc.showSaveDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            String absolutePath = f.getAbsolutePath();
            if (!f.toString().endsWith(".jpg") && !f.toString().endsWith(".png")) {
                if (f.toString().contains(".")) {
                    toasterManager.showToaster("Bad format! You can only export png or jpg image!");
                    throw new BadFormatException("Bad format!");
                } else {
                    absolutePath += ".png";
                }
            }
            export(absolutePath);
        }
    }

    /**
     * Flips the Canva image horizontally.
     */
    public void flipImageHorizontal() {
        BufferedImage newImage = this.canva.getBufferedImage();
        AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
        affineTransform.translate(-newImage.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.canva.setBufferedImage(op.filter(newImage, null));
    }

    /**
     * Flips the Canva image vertically.
     */
    public void flipImageVertical() {
        BufferedImage newImage = this.canva.getBufferedImage();
        AffineTransform affineTransform = AffineTransform.getScaleInstance(1, -1);
        affineTransform.translate(0, -newImage.getHeight());
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.canva.setBufferedImage(op.filter(newImage, null));
    }

    /**
     * Quits the application. Prompts the user to save the image before quitting.
     *
     * @param frame the JFrame instance associated with the application
     */
    public void quit(JFrame frame) {
        String[] options = new String[]{"Yes and save the image", "Yes without saving", "No"};
        int resultOptionPane = JOptionPane.showOptionDialog(null, "Do you really want to quit ?", "Exit",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (resultOptionPane == 0) {
            this.chooseExportPath();
        }
        if (resultOptionPane == 0 || resultOptionPane == 1) {
            frame.setContentPane(new SelectionPanel(frame));
            frame.setJMenuBar(null);
            frame.validate();
            frame.repaint();
        }
    }

    /**
     * Undoes the previous action on the Canva.
     */
    public void undo() {
        if (this.canva.getImageStates().size() > 1 && this.canva.getCurrentIndex() > 0) {
            this.canva.setCurrentIndex(this.canva.getCurrentIndex() - 1);
            this.canva.setG2((Graphics2D) this.canva.getImageStates().get(this.canva.getCurrentIndex()).getGraphics());
            this.canva.repaint();
        }
    }

    /**
     * Redoes the previously undone action on the Canva.
     */
    public void redo() {
        if (this.canva.getCurrentIndex() < this.canva.getImageStates().size() - 1) {
            this.canva.setCurrentIndex(this.canva.getCurrentIndex() + 1);
            this.canva.setG2((Graphics2D) this.canva.getImageStates().get(this.canva.getCurrentIndex()).getGraphics());
            this.canva.repaint();
        }
    }

    /**
     * Applies the black and white transformation to the Canva image.
     */
    public void blackAndWhiteTransform() {
        BufferedImage newImage = this.canva.nextBufferedImage();
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(newImage, newImage);
        this.canva.repaint();
    }

    /**
     * Resizes the Canva to the specified width and height.
     *
     * @param width  the new width of the Canva
     * @param height the new height of the Canva
     */
    public void resizeCanva(int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, this.canva.getBufferedImage().getType());
        this.canva.setG2(resizedImage.createGraphics());
        this.canva.getG2().drawImage(this.canva.getBufferedImage(), 0, 0, width, height, null);
        this.canva.setBufferedImage(resizedImage);
    }

    /**
     * Crops the Canva to the specified width and height and with the position of crop.
     *
     * @param width  the new width of the Canva
     * @param height the new height of the Canva
     * @param horizontalAlign crop in the horizontal side
     * @param verticalAlign crop in the vertical side
     */
    public void cropCanva(int width, int height, CropTypes horizontalAlign, CropTypes verticalAlign) {
        BufferedImage resizedImage = new BufferedImage(width, height, this.canva.getBufferedImage().getType());
        this.canva.setG2(resizedImage.createGraphics());
        this.canva.getG2().setColor(Color.WHITE);
        this.canva.getG2().fillRect(0, 0, width, height);
        int x = 0;
        int y = 0;
        switch (horizontalAlign) {
            case LEFT -> x = 0;
            case MIDDLE -> x = (width - this.canva.getBufferedImage().getWidth()) / 2;
            case RIGHT -> x = (width - this.canva.getBufferedImage().getWidth());
        }
        switch (verticalAlign) {
            case TOP -> y = 0;
            case MIDDLE -> y = (height - this.canva.getBufferedImage().getHeight()) / 2;
            case BOTTOM -> y = height - this.canva.getBufferedImage().getHeight();
        }
        this.canva.getG2().drawImage(this.canva.getBufferedImage(), x, y, this.canva.getBufferedImage().getWidth(), this.canva.getBufferedImage().getHeight(), null);
        this.canva.setBufferedImage(resizedImage);
    }

    /**
     * Prompts the user to choose an image file to import and imports it into the Canva.
     *
     * @param frame the JFrame instance associated with the application
     */
    public void chooseImportPath(JFrame frame) {
        String filename = File.separator + "tmp";
        JFileChooser fileChooser = new JFileChooser(new File(filename));

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Image");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return(file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.isDirectory());
            }

            @Override
            public String getDescription() {
                return null;
            }
        });

        fileChooser.showOpenDialog(frame);
        importFile(fileChooser.getSelectedFile());
    }

    /**
     * Imports the specified file into the Canva.
     *
     * @param file the file to import
     */
    public void importFile(File file) {
        try {
            this.canva.setBufferedImage(ImageIO.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the Canva, setting it to a blank state.
     */
    public void clear() {
        this.canva.nextBufferedImage();
        this.canva.getG2().setPaint(Color.WHITE);
        this.canva.getG2().fillRect(0, 0, this.canva.getWidth(), this.canva.getHeight());
        this.canva.repaint();
    }

    /**
     * Pastes the specified image onto the Canva.
     *
     * @param clipboardImage the image to paste
     */
    public void pasteImage(BufferedImage clipboardImage) {
        this.canva.getG2().drawImage(clipboardImage, 0, 0, null);
    }

    /**
     * Copies the image from the clipboard to the Canva.
     * Resizes the Canva if necessary to accommodate the clipboard image.
     *
     * @throws ClipboardVoidException if the clipboard does not contain an image
     */
    public void clipboardToBufferedImage() throws ClipboardVoidException {
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                BufferedImage clipboardImage = (BufferedImage) transferable.getTransferData(DataFlavor.imageFlavor);
                if (clipboardImage.getWidth() > this.canva.getBufferedImage().getWidth() || clipboardImage.getHeight() > this.canva.getBufferedImage().getHeight()) {
                    resizeCanva(clipboardImage.getWidth(), clipboardImage.getHeight());
                    pasteImage(clipboardImage);
                } else {
                    pasteImage(clipboardImage);
                }
                this.canva.repaint();
            } else {
                Toaster toasterManager = new Toaster();
                toasterManager.showToaster("The clipboard doesn't contain an image!");
                throw new ClipboardVoidException("Clipboard without image");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}