package graphic.model.color;

import java.awt.*;
import java.util.Observable;

public class ColorModel extends Observable {

    private Color primaryColor = Color.BLACK;
    private Color secondaryColor = Color.WHITE;

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

}
