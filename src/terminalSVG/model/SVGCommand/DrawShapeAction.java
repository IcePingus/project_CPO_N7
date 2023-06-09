package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;

public abstract class DrawShapeAction implements SVGCommand {

    protected String name;
    protected boolean isFill;
    protected Color strokeColor;
    protected Color fillColor;

    public DrawShapeAction(String name, boolean isFill, Color strokeColor, Color fillColor) {
        this.name = name;
        this.isFill = isFill;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    // Implémentez les méthodes de l'interface SVGCommand
    @Override
    public String getName() {
        return name;
    }
    @Override
    public abstract String getHelp();
    @Override
    public void execute(SVGPreview svgPreview, String elementName) {
        svgPreview.addElement(elementName,this);
    }

    public abstract void draw(SVGPreview svgPreview);

    public void setName(String newName) {
        this.name = newName;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public boolean isFill() {
        return isFill;
    }

    public void setFill(boolean fill) {
        isFill = fill;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
}
