package graphic.controller;

import com.nitido.utils.toaster.Toaster;
import graphic.exception.BadFormatException;
import graphic.exception.ClipboardVoidException;
import graphic.model.CropTypes;
import graphic.model.canva.Canva;
import core.SelectionPanel;

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
        // Initialiser la notification
        Toaster toasterManager = new Toaster();
        try {
            ImageIO.write(this.canva.getBufferedImage(), "png", new File(path));
            // Afficher la notification à l'utilisateur
            toasterManager.showToaster("Image exported!");
        } catch (IOException e) {
            // Afficher la notification d'erreur
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
        // Initialiser la notification
        Toaster toasterManager = new Toaster();
        // Instancier un explorateur de fichier et vérifier si l'utilisateur crée un fichier avec une extension png ou jpg
        JFileChooser jfc = new JFileChooser();
        int retVal = jfc.showSaveDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            String absolutePath = f.getAbsolutePath();
            if (!f.toString().endsWith(".jpg") && !f.toString().endsWith(".png")) {
                if (f.toString().contains(".")) {
                    // Afficher le message d'erreur et lever une exception
                    toasterManager.showToaster("Bad format! You can only export png or jpg image!");
                    throw new BadFormatException("Bad format!");
                } else {
                    absolutePath += ".png";
                }
            }
            // Exporter l'image
            export(absolutePath);
        }
    }

    /**
     * Flips the Canva image horizontally.
     */
    public void flipImageHorizontal() {
        BufferedImage newImage = this.canva.getBufferedImage();
        // Applique des transformations à la bufferedImage
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
        // Applique des transformations à la bufferedImage
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
        // Afficher un JOptionPane permettant de quitter le projet en laissant le choix à l'utilisateur de sauvegarder ou non
        String[] options = new String[]{"Yes and save the image", "Yes without saving", "No"};
        int resultOptionPane = JOptionPane.showOptionDialog(null, "Do you really want to quit ?", "Exit",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        if (resultOptionPane == 0) {
            // Choisir le lieu où exporter l'image
            this.chooseExportPath();
        }
        if (resultOptionPane == 0 || resultOptionPane == 1) {
            // Afficher la SelectionPanel
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
        // Vérifier si le tableau d'image contient au moins 2 bufferedImage et l'index actuel est supérieur au premier index (0)
        if (this.canva.getImageStates().size() > 1 && this.canva.getCurrentIndex() > 0) {
            // Affiche la bufferedImage à l'index précédent
            this.canva.setCurrentIndex(this.canva.getCurrentIndex() - 1);
            this.canva.setGraphics2D((Graphics2D) this.canva.getImageStates().get(this.canva.getCurrentIndex()).getGraphics());
        }
    }

    /**
     * Redoes the previously undone action on the Canva.
     */
    public void redo() {
        // Vérifier si l'index actuel n'est pas le dernier du tableau de bufferedImage
        if (this.canva.getCurrentIndex() < this.canva.getImageStates().size() - 1) {
            // Affiche la bufferedImage à l'index suivant
            this.canva.setCurrentIndex(this.canva.getCurrentIndex() + 1);
            this.canva.setGraphics2D((Graphics2D) this.canva.getImageStates().get(this.canva.getCurrentIndex()).getGraphics());
        }
    }

    /**
     * Applies the black and white transformation to the Canva image.
     */
    public void blackAndWhiteTransform() {
        // Instancie une nouvelle bufferedImage
        BufferedImage newImage = this.canva.nextBufferedImage();
        // Modifie la bufferedImage avec uniquement des niveaux de gris
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(newImage, newImage);
    }

    /**
     * Resizes the Canva to the specified width and height.
     *
     * @param width  the new width of the Canva
     * @param height the new height of the Canva
     */
    public void resizeCanva(int width, int height) {
        // Instancie une bufferedImage avec les nouvelles dimensions
        BufferedImage resizedImage = new BufferedImage(width, height, this.canva.getBufferedImage().getType());
        this.canva.setGraphics2D(resizedImage.createGraphics());
        this.canva.getGraphics2D().drawImage(this.canva.getBufferedImage(), 0, 0, width, height, null);
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
        // Initialiser une nouvelle image de la taille souhaitée
        BufferedImage resizedImage = new BufferedImage(width, height, this.canva.getBufferedImage().getType());
        // Remplir le fond de la nouvelle image
        this.canva.setGraphics2D(resizedImage.createGraphics());
        this.canva.getGraphics2D().setColor(Color.WHITE);
        this.canva.getGraphics2D().fillRect(0, 0, width, height);
        // Définir les coordonnées où dessiner l'ancienne image
        int x = 0;
        int y = 0;
        switch (horizontalAlign) {
            case MIDDLE -> x = (width - this.canva.getBufferedImage().getWidth()) / 2;
            case RIGHT -> x = (width - this.canva.getBufferedImage().getWidth());
        }
        switch (verticalAlign) {
            case MIDDLE -> y = (height - this.canva.getBufferedImage().getHeight()) / 2;
            case BOTTOM -> y = height - this.canva.getBufferedImage().getHeight();
        }
        // Dessiner l'ancienne image
        this.canva.getGraphics2D().drawImage(this.canva.getBufferedImage(), x, y, this.canva.getBufferedImage().getWidth(), this.canva.getBufferedImage().getHeight(), null);
        this.canva.setBufferedImage(resizedImage);
    }

    /**
     * Prompts the user to choose an image file to import and imports it into the Canva.
     *
     * @param frame the JFrame instance associated with the application
     */
    public void chooseImportPath(JFrame frame) {
        String filename = File.separator + "tmp";
        // Afficher l'explorateur de fichier
        JFileChooser fileChooser = new JFileChooser(new File(filename));

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Image");
        // Accepter uniquement les fichiers d'extension png
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
        // Importer le fichier
        importFile(fileChooser.getSelectedFile());
    }

    /**
     * Imports the specified file into the Canva.
     *
     * @param file the file to import
     */
    public void importFile(File file) {
        try {
            // Modifier la bufferedImage avec le fichier donnée en paramètre
            this.canva.setBufferedImage(ImageIO.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the Canva, setting it to a blank state.
     */
    public void clear() {
        // Passer à la bufferedImage suivante
        this.canva.nextBufferedImage();
        // Remplir la toile en blanc
        this.canva.getGraphics2D().setPaint(Color.WHITE);
        this.canva.getGraphics2D().fillRect(0, 0, this.getCanvaWidth(), this.getCanvaHeight());
    }

    /**
     * Pastes the specified image onto the Canva.
     *
     * @param clipboardImage the image to paste
     */
    public void pasteImage(BufferedImage clipboardImage) {
        // Dessiner l'image copiée sur le canva
        this.canva.getGraphics2D().drawImage(clipboardImage, 0, 0, null);
    }

    /**
     * Copies the image from the clipboard to the Canva.
     * Resizes the Canva if necessary to accommodate the clipboard image.
     *
     * @throws ClipboardVoidException if the clipboard does not contain an image
     */
    public void clipboardToBufferedImage() throws ClipboardVoidException {
        try {
            // Vérifie ce qui est copié dans le presse-papiers
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {

                // Crée l'image en fonction de ce qui est copié dans le presse-papiers
                BufferedImage clipboardImage = (BufferedImage) transferable.getTransferData(DataFlavor.imageFlavor);
                // Ajuste la taille de la toile en fonction de l'image copié
                if (clipboardImage.getWidth() > this.canva.getBufferedImage().getWidth() || clipboardImage.getHeight() > this.canva.getBufferedImage().getHeight()) {
                    cropCanva(clipboardImage.getWidth(), clipboardImage.getHeight(), CropTypes.LEFT, CropTypes.TOP);
                    // Coller l'image copiée
                    pasteImage(clipboardImage);
                } else {
                    // Coller l'image copiée
                    pasteImage(clipboardImage);
                }
                //this.canva.repaint();
            } else {
                // Presse-papiers vide, initialiser et afficher une notification d'erreur et lever une exception
                Toaster toasterManager = new Toaster();
                toasterManager.showToaster("The clipboard doesn't contain an image!");
                throw new ClipboardVoidException("Clipboard without image");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Canva getCanva() {
        return this.canva;
    }


}