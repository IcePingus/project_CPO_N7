package graphic.model.tools;

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
    private final boolean isResizable;
    private final boolean isSquareRoundShape;

    /**
     * Constructs a new `MoveTool` instance.
     */
    public MoveTool() {
        this.name = "Move";
        this.image = new ImageIcon(getClass().getResource("/assets/images/move.png"));
        this.isResizable = false;
        this.isSquareRoundShape = false;
    }

    @Override
    public String getName() {
        /**
         * Gets the name of the tool.
         *
         * @return the name of the tool
         */
        return this.name;
    }

    @Override
    public Icon getImage() {
        /**
         * Gets the image icon representing the tool.
         *
         * @return the image icon representing the tool
         */
        return this.image;
    }

    @Override
    public boolean getIsResizable() {
        /**
         * Checks if the tool allows resizing of objects.
         *
         * @return `true` if the tool allows resizing, `false` otherwise
         */
        return this.isResizable;
    }

    @Override
    public boolean getIsSquareRoundShape() {
        /**
         * Checks if the tool creates square or round shapes.
         *
         * @return `true` if the tool creates square or round shapes, `false` otherwise
         */
        return this.isSquareRoundShape;
    }

    @Override
    public boolean getHasShapeSelection() {
        /**
         * Checks if the tool requires shape selection before execution.
         *
         * @return `true` if the tool requires shape selection, `false` otherwise
         */
        return false;
    }

    @Override
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint, JComponent jComponent) {
        /**
         * Executes the tool command with the given parameters.
         *
         * @param oldX the previous x-coordinate
         * @param oldY the previous y-coordinate
         * @param currentX the current x-coordinate
         * @param currentY the current y-coordinate
         * @param bufferedImage the buffered image
         * @param graphics2D the graphics context
         * @param click the number of clicks
         * @param size the size of the shape
         * @param square specifies whether the shape is square
         * @param isFirstPoint specifies whether it is the first point of the shape
         * @param jComponent the Swing component
         */
        int mouseDeltaX = currentX - oldX;
        int mouseDeltaY = currentY - oldY;

        int sensitivity = 5;
        double deltaX = (double) mouseDeltaX / sensitivity;
        double deltaY = (double) mouseDeltaY / sensitivity;

        int newX = jComponent.getLocation().x + (int) deltaX;
        int newY = jComponent.getLocation().y + (int) deltaY;

        jComponent.setLocation(newX, newY);

        jComponent.repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}