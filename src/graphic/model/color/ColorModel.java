package graphic.model.color;

import java.awt.*;
import java.util.Observable;

/**
 * The ColorModel class represents a model for handling primary and secondary colors in a graphics application.
 * It extends the Observable class to notify observers of any changes in the color values.
 *
 * @author Team 3
 */
public class ColorModel extends Observable {

    private Color primaryColor = Color.BLACK;
    private Color secondaryColor = Color.WHITE;
    private boolean isPrimaryColor = true;


    /**
     * Returns the primary color.
     *
     * @return the primary color
     */
    public Color getPrimaryColor() {
        return this.primaryColor;
    }

    /**
     * Returns the secondary color.
     *
     * @return the secondary color
     */
    public Color getSecondaryColor() {
        return this.secondaryColor;
    }

    /**
     * Returns the flag indicating whether the primary color is currently active.
     *
     * @return true if the primary color is active, false otherwise
     */
    public boolean getIsPrimaryColor() {
        return this.isPrimaryColor;
    }

    /**
     * Sets the primary color.
     *
     * @param color the new primary color
     */
    public void setPrimaryColor(Color color) {
        this.primaryColor = color;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the secondary color.
     *
     * @param color the new secondary color
     */
    public void setSecondaryColor(Color color) {
        this.secondaryColor = color;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the flag indicating whether the primary color is active.
     *
     * @param isPrimaryColor the new value for the flag
     * @return the updated flag value
     */
    public boolean setIsPrimaryColor(boolean isPrimaryColor) {
        return this.isPrimaryColor = isPrimaryColor;
    }
}