package graphic.model.tools;

import graphic.model.ToolContext;

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
     * @param context          the application context
     */
    void execute(ToolContext context);
}
