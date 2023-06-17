package graphic.model.tools;

import graphic.controller.ColorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;


/**
 * The PickerTool class represents a color picker tool in a graphics application.
 * It implements the ToolCommand interface and provides functionality for selecting colors from the canvas.
 *
 * @author Team 3
 */
public class PickerTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;
    private final boolean hasShapeSelection;
    private final ColorController colorController;

    /**
     * Constructs a PickerTool object with the specified color controller.
     * It initializes the name, image, and sets the tool properties.
     *
     * @param colorController the color controller
     */
    public PickerTool(ColorController colorController) {
        this.name = "Picker";
        this.image = new ImageIcon(getClass().getResource("/assets/images/picker.png"));
        this.isResizable = false;
        this.isSquareRoundShape = false;
        this.hasShapeSelection = false;
        this.colorController = colorController;
    }

    /**
     * Returns the name of the color picker tool.
     *
     * @return the name of the tool
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the image icon representing the color picker tool.
     *
     * @return the image icon of the tool
     */
    @Override
    public Icon getImage() {
        return this.image;
    }

    /**
     * Returns whether the color picker tool is resizable.
     *
     * @return true if the tool is resizable, false otherwise
     */
    @Override
    public boolean getIsResizable() {
        return this.isResizable;
    }

    /**
     * Returns whether the color picker tool is square or round in shape.
     *
     * @return true if the tool is square or round in shape, false otherwise
     */
    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    /**
     * Returns whether the color picker tool requires shape selection.
     *
     * @return true if the tool requires shape selection, false otherwise
     */
    @Override
    public boolean getHasShapeSelection() {
        return this.hasShapeSelection;
    }

    /**
     * Executes the color picker tool operation.
     * It retrieves the color of the pixel at the current position on the canvas and sets it as either the primary or secondary color
     * based on the click event. The color controller is used to update the selected colors.
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
        Color color = new Color(bufferedImage.getRGB(currentX, currentY));
        if (click == InputEvent.BUTTON1_DOWN_MASK) {
            this.colorController.setPrimaryColor(color);
            this.colorController.setIsPrimaryColor(true);
        } else if (click == InputEvent.BUTTON3_DOWN_MASK) {
            this.colorController.setIsPrimaryColor(false);
            this.colorController.setSecondaryColor(color);
        }
    }

    /**
     * Updates the picker tool based on changes in the color model.
     *
     * @param o   the observed object
     * @param arg the argument passed by the observed object
     */
    @Override
    public void update(Observable o, Object arg) { }
}