package terminalSVG.model;

import java.awt.*;
import java.util.List;

public class Instruction {

    private final String action;
    private String name;
    private String oldName;
    private Color strokeColor;
    private boolean isFilled;
    private Color fillColor;
    private List<Double> coords;
    private Double Width;
    private Double Height;
    private Double dx;
    private Double dy;

    public Instruction(String action) {
        this.action = action;
    }

    public Instruction(String action, String name) {
        this.action = action;
        this.name = name;
    }

    public Instruction(String action, String name, Color color, boolean isStrokeColor) {
        this(action, name);
        if (isStrokeColor) {
            this.strokeColor = color;
        } else {
            this.fillColor = color;
            if(this.fillColor != null) {
                this.isFilled = true;
            }
        }
    }

    public Instruction(String action, String name, Color strokeColor, Color fillColor) {
        this(action, name, strokeColor, true);
        this.fillColor = fillColor;
        if(this.fillColor != null) {
            this.isFilled = true;
        }
    }

    public Instruction(String action, String name, List<Double> coords, Color strokeColor, Color fillColor) {
        this(action, name, strokeColor, fillColor);
        this.coords = coords;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public List<Double> getCoords() {
        return coords;
    }

    public void setCoords(List<Double> coords) {
        this.coords = coords;
    }

    public String getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setFillColor(Color fillColor) {
        this.setFilled(true);
        this.fillColor = fillColor;
    }

    public Double getWidth() {
        return Width;
    }

    public void setWidth(Double width) {
        Width = width;
    }

    public Double getHeight() {
        return Height;
    }

    public void setHeight(Double height) {
        Height = height;
    }

    public Double getDx() {
        return dx;
    }

    public void setDx(Double dx) {
        this.dx = dx;
    }

    public Double getDy() {
        return dy;
    }

    public void setDy(Double dy) {
        this.dy = dy;
    }

    @Override
    public String toString() {
        return getAction() + " " + getName() + " " + getOldName() + " " + getCoords() + " " + getStrokeColor() + " " + isFilled() + " " +getFillColor()
                + " " + getWidth() + " " + getHeight();
    }
}
