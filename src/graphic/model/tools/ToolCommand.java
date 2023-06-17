package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observer;

/**
 * The ToolCommand interface defines the contract for a tool command in a graphics application.
 * It extends the Observer interface to receive updates from the observable color model.
 *
 * @author Team 3
 */
public interface ToolCommand extends Observer {

    /**
     * Returns the name of the tool.
     *
     * @return the name of the tool
     */
    String getName();

    /**
     * Returns the icon representing the tool.
     *
     * @return the icon representing the tool
     */
    Icon getImage();

    /**
     * Returns a flag indicating whether the tool is resizable.
     *
     * @return true if the tool is resizable, false otherwise
     */
    boolean getIsResizable();

    /**
     * Returns a flag indicating whether the tool creates square or round shapes.
     *
     * @return true if the tool creates square or round shapes, false otherwise
     */
    boolean getIsSquareRoundShape();

    /**
     * Returns a flag indicating whether the tool requires shape selection.
     *
     * @return true if the tool requires shape selection, false otherwise
     */
    boolean getHasShapeSelection();

    /**
     * Executes the tool command with the specified parameters.
     *
     * @param oldX          the x-coordinate of the previous position
     * @param oldY          the y-coordinate of the previous position
     * @param currentX      the current x-coordinate
     * @param currentY      the current y-coordinate
     * @param bufferedImage the buffered image to draw on
     * @param graphics2D    the graphics context for drawing
     * @param click         the mouse click event modifiers
     * @param size          the size of the tool
     * @param square        a flag indicating whether the shape should be square
     * @param isFirstPoint  a flag indicating whether it is the first point of the shape
     * @param canva         the JComponent representing the canvas
     */
    void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint, JComponent canva);
}
