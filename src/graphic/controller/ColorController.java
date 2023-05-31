package graphic.controller;

import java.awt.*;
import java.util.Observable;

public class ColorController extends Observable {

    private Color activeColor = Color.BLACK;

    public void setActiveColor(Color color) {
        this.activeColor = color;
        this.setChanged();
        this.notifyObservers();
    }

    public Color getActiveColor() {
        return this.activeColor;
    }

}
