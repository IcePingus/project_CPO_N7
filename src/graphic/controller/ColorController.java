package graphic.controller;

import graphic.model.color.ColorModel;

import java.awt.*;

/**
 * The ColorController class is responsible for controlling the colors in the application.
 *
 * @author Team 3
 */
public class ColorController {

    private final ColorModel model;

    /**
     * Constructs a ColorController with the specified ColorModel.
     *
     * @param model the ColorModel to associate with the ColorController
     */
    public ColorController(ColorModel model) {
        this.model = model;
    }

    /**
     * Sets the primary color in the ColorModel.
     *
     * @param color the new primary color
     */
    public void setPrimaryColor(Color color) {
        this.model.setPrimaryColor(color);
    }

    /**
     * Sets the secondary color in the ColorModel.
     *
     * @param color the new secondary color
     */
    public void setSecondaryColor(Color color) {
        this.model.setSecondaryColor(color);
    }

    /**
     * Retrieves the primary color from the ColorModel.
     *
     * @return the primary color
     */
    public Color getPrimaryColor() {
        return this.model.getPrimaryColor();
    }

    /**
     * Retrieves the secondary color from the ColorModel.
     *
     * @return the secondary color
     */
    public Color getSecondaryColor() {
        return this.model.getSecondaryColor();
    }

    /**
     * Retrieves the flag indicating whether the current color is the primary color from the ColorModel.
     *
     * @return true if the current color is the primary color, false otherwise
     */
    public boolean getIsPrimaryColor() {
        return this.model.getIsPrimaryColor();
    }

    /**
     * Sets the flag indicating whether the current color is the primary color in the ColorModel.
     *
     * @param isPrimaryColor true if the current color is the primary color, false otherwise
     */
    public void setIsPrimaryColor(boolean isPrimaryColor) {
        this.model.setIsPrimaryColor(isPrimaryColor);
    }
}