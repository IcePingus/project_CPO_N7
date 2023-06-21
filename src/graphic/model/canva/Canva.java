package graphic.model.canva;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Canva extends Observable {
    private final List<BufferedImage> imageStates;
    private int currentIndex;
    private Graphics2D graphics2D;

    public Canva() {
        this.imageStates = new ArrayList<>();
        this.currentIndex = 0;
    }

    /**
     * Returns the list of image states.
     *
     * @return the list of image states
     */
    public List<BufferedImage> getImageStates() {
        return imageStates;
    }

    /**
     * Returns the current index of the buffered image.
     *
     * @return the current index
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Returns the Graphics2D object associated with the canvas.
     *
     * @return the Graphics2D object
     */
    public Graphics2D getGraphics2D() {
        return this.graphics2D;
    }

    /**
     * Returns the current BufferedImage.
     *
     * @return the current BufferedImage
     */
    public BufferedImage getBufferedImage() {
        return this.imageStates.get(currentIndex);
    }

    /**
     * Sets the current index of the buffered image.
     *
     * @param currentIndex the current index
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        // Modifier le label sur la taille de la toile
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the specified Graphics2D object as the current graphics object.
     *
     * @param graphics2D the Graphics2D object
     */
    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the specified buffered image as the current image state and updates the graphics object.
     *
     * @param bufferedImage the new buffered image
     */
    public void setBufferedImage(BufferedImage bufferedImage) {
        // Vérifie la taille de la liste d'image
        if (this.imageStates.size() == 0) {
            this.imageStates.add(bufferedImage);
        } else {
            // Créer la bufferedImage en fonction de l'index courant
            this.currentIndex++;
            this.imageStates.add(this.currentIndex, bufferedImage);
            if (this.imageStates.size() > this.currentIndex + 1) {
                this.imageStates.subList(this.currentIndex + 1, this.imageStates.size()).clear();
            }
            //this.canvaSizeLabel.setText(this.getBufferedImage().getWidth() + "x" + this.getBufferedImage().getHeight());
            // Récupérer la nouvelle bufferedImage
            this.graphics2D = (Graphics2D) bufferedImage.getGraphics();
            this.setChanged();
            this.notifyObservers();
        }
    }

    /**
     * Creates a copy of the specified BufferedImage.
     *
     * @param source the BufferedImage to copy
     * @return the copied BufferedImage
     */
    private BufferedImage copyBufferedImage(BufferedImage source) {
        // Copier la bufferedImage
        ColorModel cm = source.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = source.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * Creates a new BufferedImage based on the current state and sets it as the active image.
     *
     * @return the new BufferedImage
     */
    public BufferedImage nextBufferedImage() {
        // Créer une nouvelle bufferedImage et la définir comme actif
        BufferedImage newImage = copyBufferedImage(this.imageStates.get(this.currentIndex));
        this.setBufferedImage(newImage);
        return newImage;
    }
}