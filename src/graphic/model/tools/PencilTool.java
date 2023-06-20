package graphic.model.tools;

import graphic.model.ToolContext;
import graphic.model.color.ColorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * The PencilTool class represents a pencil tool in a graphics application.
 * It implements the ToolCommand interface and provides functionality for drawing with a pencil.
 *
 * @author Team 3
 */
public class PencilTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;
    private final boolean hasShapeSelection;
    private Color primaryColor;
    private Color secondaryColor;

    /**
     * Constructs a PencilTool object with default settings.
     * It initializes the name, image, and sets the tool properties.
     * The primary color and secondary color are initially set to black and white, respectively.
     */
    public PencilTool() {
        this.name = "Pencil";
        this.image = new ImageIcon(getClass().getResource("/assets/images/pencil.png"));
        this.isResizable = true;
        this.isSquareRoundShape = true;
        this.hasShapeSelection = false;
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
    }

    /**
     * Returns the name of the pencil tool.
     *
     * @return the name of the tool
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the image icon representing the pencil tool.
     *
     * @return the image icon of the tool
     */
    @Override
    public Icon getImage() {
        return this.image;
    }

    /**
     * Returns whether the pencil tool is resizable.
     *
     * @return true if the tool is resizable, false otherwise
     */
    @Override
    public boolean getIsResizable() {
        return this.isResizable;
    }

    /**
     * Returns whether the pencil tool is square or round in shape.
     *
     * @return true if the tool is square or round in shape, false otherwise
     */
    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    /**
     * Returns whether the pencil tool requires shape selection.
     *
     * @return true if the tool requires shape selection, false otherwise
     */
    @Override
    public boolean getHasShapeSelection() {
        return this.hasShapeSelection;
    }

    /**
     * Executes the pencil tool operation.
     * It draws a line with the selected color based on the click event and the colors set in the color model.
     * The line can be drawn with a square or round shape depending on the tool size.
     *
     * @param context          the application context
     */
    @Override
    public void execute(ToolContext context) {
        // Récupérer la couleur en fonction du type de clic
        Color color = null;
        if (context.getClick() == InputEvent.BUTTON1_DOWN_MASK) {
            color = primaryColor;
        } else if (context.getClick() == InputEvent.BUTTON3_DOWN_MASK) {
            color = secondaryColor;
        }
        if (color != null) {
            // Définir la couleur avec laquelle dessiner
            context.getCanva().getG2().setPaint(color);

            // Dessiner le point actuel en fonction de la taille
            if (context.isSquare()) {
                context.getCanva().getG2().fillRect(context.getOldX() - context.getSize() / 2, context.getOldY() - context.getSize() / 2, context.getSize(), context.getSize());
            } else {
                context.getCanva().getG2().fillOval(context.getOldX() - context.getSize() / 2, context.getOldY() - context.getSize() / 2, context.getSize(), context.getSize());
            }

            // Définir la distance à parcourir pour chaque dimension, la direction et l'erreur (algorithme de Bresenham)
            int distanceX = Math.abs(context.getCurrentX() - context.getOldX());
            int distanceY = Math.abs(context.getCurrentY() - context.getOldY());
            int directionX = context.getOldX() < context.getCurrentX() ? 1 : -1;
            int directionY = context.getOldY() < context.getCurrentY() ? 1 : -1;
            int erreur = distanceX - distanceY;
            int erreur2;

            // Parcourir les points entre le dernier point enregistré et le point actuel et gommer les points en fonction de la taille
            while (context.getOldX() != context.getCurrentX() || context.getOldY() != context.getCurrentY()) {

                if (context.isSquare()) {
                    context.getCanva().getG2().fillRect(context.getOldX() - context.getSize() / 2, context.getOldY() - context.getSize() / 2, context.getSize(), context.getSize());
                } else {
                    context.getCanva().getG2().fillOval(context.getOldX() - context.getSize() / 2, context.getOldY() - context.getSize() / 2, context.getSize(), context.getSize());
                }

                // Calculer l'erreur lors du parcours des points pour bien suivre le chemin
                erreur2 = 2 * erreur;
                if (erreur2 > -distanceY) {
                    erreur -= distanceY;
                    context.setOldX(context.getOldX() + directionX);
                }
                if (erreur2 < distanceX) {
                    erreur += distanceX;
                    context.setOldY(context.getOldY() + directionY);
                }
            }
        }
    }

    /**
     * Updates the pencil tool based on changes in the color model.
     *
     * @param o   the observed object
     * @param arg the argument passed by the observed object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorModel) {
            this.primaryColor = ((ColorModel) o).getPrimaryColor();
            this.secondaryColor = ((ColorModel) o).getSecondaryColor();
        }
    }
}