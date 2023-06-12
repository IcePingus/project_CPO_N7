package graphic.model.color;

import java.awt.*;
import java.util.Observable;

public class ColorModel extends Observable {

    private Color primaryColor = Color.BLACK;
    private Color secondaryColor = Color.WHITE;
    private boolean isPrimaryColor = true;

    public void setPrimaryColor(Color color) {
        this.primaryColor = color;
        this.setChanged();
        this.notifyObservers();
    }

    public void setSecondaryColor(Color color) {
        this.secondaryColor = color;
        this.setChanged();
        this.notifyObservers();
    }

    public Color getPrimaryColor() {
        return this.primaryColor;
    }
    public Color getSecondaryColor() {
        return this.secondaryColor;
    }

    public boolean getIsPrimaryColor() {
        return this.isPrimaryColor;
    }

    public boolean setIsPrimaryColor(boolean isPrimaryColor) {
        return this.isPrimaryColor = isPrimaryColor;
    }

}
