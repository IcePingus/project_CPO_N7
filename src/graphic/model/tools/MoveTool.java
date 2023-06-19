package graphic.model.tools;

import graphic.model.ToolContext;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * The MoveTool class represents a tool for moving the canva.
 *
 * @author Team 3
 */
public class MoveTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private boolean isResizable;
    private boolean isSquareRoundShape;

    /**
     * Constructs a new MoveTool instance for move the canva.
     */
    public MoveTool() {
        this.name = "Move";
        this.image = new ImageIcon(getClass().getResource("/assets/images/move.png"));
    }

    /**
     * Gets the name of the tool.
     *
     * @return the name of the tool
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the image icon representing the tool.
     *
     * @return the image icon representing the tool
     */
    @Override
    public Icon getImage() {
        return this.image;
    }

    /**
     * Checks if the tool allows resizing of objects.
     *
     * @return true if the tool allows resizing, false otherwise
     */
    @Override
    public boolean getIsResizable() {
        return this.isResizable;
    }

    /**
     * Checks if the tool creates square or round shapes.
     *
     * @return true if the tool creates square or round shapes, false otherwise
     */
    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    /**
     * Checks if the tool requires shape selection before execution.
     *
     * @return true if the tool requires shape selection, false otherwise
     */
    @Override
    public boolean getHasShapeSelection() {
        return false;
    }

    /**
     * Executes the tool command with the given parameters.
     *
     * @param context the application context
     */
    @Override
    public void execute(ToolContext context) {
        // La différence entre la position actuelle et l'ancienne position
        int mouseDeltaX = context.getCurrentX() - context.getOldX();
        int mouseDeltaY = context.getCurrentY() - context.getOldY();

        // Modification de la différence en fonction de la sensibilité
        int sensitivity = 5;
        double deltaX = (double) mouseDeltaX / sensitivity;
        double deltaY = (double) mouseDeltaY / sensitivity;

        // Modifier des positions du composant
        int newX = context.getCanva().getLocation().x + (int) deltaX;
        int newY = context.getCanva().getLocation().y + (int) deltaY;

        context.getCanva().setLocation(newX, newY);
        context.getCanva().repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}