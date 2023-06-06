package terminalSVG.model.SVGElement;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.List;

public class OvalSVG implements SVGElement {

    private Point center;
    private double width;
    private double height;
    private boolean isFill;
    private Color FillColor;
    private Color strokeColor;
    private String elementName;
    private static final Integer CoordsListSize = 4;
    private final String commandName = "oval";

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public boolean isFill() {
        return isFill;
    }

    public void setFill(boolean fill) {
        isFill = fill;
    }

    public Color getFillColor() {
        return FillColor;
    }

    public void setFillColor(Color fillColor) {
        FillColor = fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getCommandName() {
        return commandName;
    }

    public static Integer getCoordsListSize() {
        return CoordsListSize;
    }

    public OvalSVG() {
    }

    public OvalSVG(String name, List<Double> coords, boolean isFill, Color fillColor, Color strokeColor) {
        this.elementName = name;
        this.center = new Point(coords.get(0),coords.get(1));
        this.width = coords.get(2);
        this.height = coords.get(3);
        this.isFill = isFill;
        FillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    @Override
    public String getHelp() {
        return "Ceci est l'aide de CercleSVG.";
    }

    @Override
    public void draw(SVGGraphics2D svgform) {
        // Dessiner le cercle avec le SVGGraphics2D
        svgform.setColor(getStrokeColor());

        // Dessiner le contour du cercle
        svgform.drawOval((int)center.getX(), (int)center.getY(), (int)width, (int)height);

        if (isFill) {
            // Remplir le cercle
            svgform.setColor(getFillColor());
            svgform.fillOval((int)center.getX(), (int)center.getY(), (int)width, (int)height);
        }
    }

}
