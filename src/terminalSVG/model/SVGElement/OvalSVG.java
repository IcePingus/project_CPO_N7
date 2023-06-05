package terminalSVG.model.SVGElement;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OvalSVG implements SVGElement {

    private Point center;
    private double width;

    private double height;
    private boolean isFill;
    private Color FillColor;
    private Color strokeColor;
    private String elementName;
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

    public OvalSVG() {
    }

    public OvalSVG(String elementName,double x, double y, double width, double height, boolean isFill, Color fillColor, Color strokeColor) {
        this.center = new Point(x,y);
        this.height = height;
        this.width = width;
        this.isFill = isFill;
        FillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    @Override
    public String Help() {
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
