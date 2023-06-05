package graphic.model.tools;

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

    public void setIsPrimaryColor(boolean isPrimaryColor) {
        this.isPrimaryColor = isPrimaryColor;
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

}
