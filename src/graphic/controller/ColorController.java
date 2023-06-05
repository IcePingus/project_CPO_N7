package graphic.controller;

import graphic.model.tools.ColorModel;

import java.awt.*;
import java.util.Observable;

public class ColorController {

    private final ColorModel model;

    public ColorController(ColorModel model) {
        this.model = model;
    }

    public void setPrimaryColor(Color color) {
        this.model.setPrimaryColor(color);
    }

    public void setSecondaryColor(Color color) {
        this.model.setSecondaryColor(color);
    }

    public Color getPrimaryColor() {
        return this.model.getPrimaryColor();
    }

    public Color getSecondaryColor() {
        return this.model.getSecondaryColor();
    }

}
