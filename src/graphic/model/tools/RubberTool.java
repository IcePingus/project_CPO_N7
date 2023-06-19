package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * The RubberTool class represents a rubber tool in a graphics application.
 * It implements the ToolCommand interface and provides functionality for erasing drawings on the canvas.
 *
 * @author Team 3
 */
public class RubberTool implements ToolCommand {
    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;
    private final boolean hasShapeSelection;

    /**
     * Constructs a RubberTool object.
     * It initializes the name, image, and sets the tool properties.
     */
    public RubberTool() {
        this.name = "Rubber";
        this.image = new ImageIcon(getClass().getResource("/assets/images/rubber.png"));
        this.isResizable = true;
        this.isSquareRoundShape = true;
        this.hasShapeSelection = false;
    }

    /**
     * Returns the name of the rubber tool.
     *
     * @return the name of the tool
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the image icon representing the rubber tool.
     *
     * @return the image icon of the tool
     */
    @Override
    public Icon getImage() {
        return this.image;
    }

    /**
     * Returns whether the rubber tool is resizable.
     *
     * @return true if the tool is resizable, false otherwise
     */
    @Override
    public boolean getIsResizable() {
        return this.isResizable;
    }

    /**
     * Returns whether the rubber tool is square or round in shape.
     *
     * @return true if the tool is square or round in shape, false otherwise
     */
    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    /**
     * Returns whether the rubber tool requires shape selection.
     *
     * @return true if the tool requires shape selection, false otherwise
     */
    @Override
    public boolean getHasShapeSelection() {
        return this.hasShapeSelection;
    }

    /**
     * Executes the rubber tool operation.
     * It erases the drawings on the canvas by drawing white lines or rectangles over them.
     *
     * @param oldX          the x-coordinate of the initial point
     * @param oldY          the y-coordinate of the initial point
     * @param currentX      the current x-coordinate
     * @param currentY      the current y-coordinate
     * @param bufferedImage the buffered image
     * @param graphics2D    the graphics context
     * @param click         the click event
     * @param size          the tool size
     * @param square        the flag indicating whether the shape should be square
     * @param isFirstPoint  the flag indicating whether it is the first point
     * @param canva         the canvas component
     */
    @Override
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint, JComponent canva) {
        // Définir la méthode de dessin à supprimer la couleur
        graphics2D.setComposite(AlphaComposite.Clear);

        // Gommer le point actuel en fonction de la taille
        if (square) {
            graphics2D.fillRect(oldX - size / 2, oldY - size / 2, size, size);
        } else {
            graphics2D.fillOval(oldX - size / 2, oldY - size / 2, size, size);
        }

        // Définir la distance à parcourir pour chaque dimension, la direction et l'erreur (algorithme de Bresenham)
        int distanceX = Math.abs(currentX - oldX);
        int distanceY = Math.abs(currentY - oldY);
        int directionX = oldX < currentX ? 1 : -1;
        int directionY = oldY < currentY ? 1 : -1;
        int erreur = distanceX - distanceY;
        int erreur2;

        // Parcourir les points entre le dernier point enregistré et le point actuel et gommer les points en fonction de la taille
        while (oldX != currentX || oldY != currentY) {

            if (square) {
                graphics2D.fillRect(oldX - size / 2, oldY - size / 2, size, size);
            } else {
                graphics2D.fillOval(oldX - size / 2, oldY - size / 2, size, size);
            }

            // Calculer l'erreur lors du parcours des points pour bien suivre le chemin
            erreur2 = 2 * erreur;
            if (erreur2 > -distanceY) {
                erreur -= distanceY;
                oldX += directionX;
            }
            if (erreur2 < distanceX) {
                erreur += distanceX;
                oldY += directionY;
            }
        }
    }

    /**
     * Updates the rubber tool based on changes in the observed object.
     *
     * @param o   the observed object
     * @param arg the argument passed by the observed object
     */
    @Override
    public void update(Observable o, Object arg) {
    }
}