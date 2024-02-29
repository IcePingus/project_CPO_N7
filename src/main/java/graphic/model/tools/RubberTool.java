package graphic.model.tools;

import graphic.model.ToolContext;

import javax.swing.*;
import java.awt.*;
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
        this.image = new ImageIcon(getClass().getResource("/images/rubber.png"));
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
     * @param context          the application context
     */
    @Override
    public void execute(ToolContext context) {
        // Définir la méthode de dessin à supprimer la couleur
        context.getCanva().getGraphics2D().setComposite(AlphaComposite.Clear);

        // Gommer le point actuel en fonction de la taille
        if (context.isSquare()) {
            context.getCanva().getGraphics2D().fillRect(context.getOldX() - context.getSize() / 2, context.getOldY() - context.getSize() / 2, context.getSize(), context.getSize());
        } else {
            context.getCanva().getGraphics2D().fillOval(context.getOldX() - context.getSize() / 2, context.getOldY() - context.getSize() / 2, context.getSize(), context.getSize());
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
                context.getCanva().getGraphics2D().fillRect(context.getOldX() - context.getSize() / 2, context.getOldY() - context.getSize() / 2, context.getSize(), context.getSize());
            } else {
                context.getCanva().getGraphics2D().fillOval(context.getOldX() - context.getSize() / 2, context.getOldY() - context.getSize() / 2, context.getSize(), context.getSize());
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