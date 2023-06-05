package terminalSVG.model.SVGElement;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.List;

public class CircleSVG implements SVGElement {

    private Point center;
    private double radius;
    private boolean isFill;
    private Color FillColor;
    private Color strokeColor;
    private String elementName;
    private static final Integer CoordsListSize = 3;
    private final String commandName = "circle";

    public Color getFillColor() {
        return this.FillColor;
    }

    public void setFillColor(Color fillColor) {
        this.FillColor = fillColor;
    }

    public Color getStrokeColor() {
        return this.strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Point getCenter() {
        return this.center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String newName) {
        this.elementName = newName;
    }

    public String getCommandName() {
        return this.commandName;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isFill() {
        return isFill;
    }

    public void setFill(boolean fill) {
        isFill = fill;
    }

    public static Integer getCoordsListSize() {
        return CoordsListSize;
    }

    public CircleSVG() {
    }

    public CircleSVG(String n, List<Double> coords, boolean f, Color cStroke, Color cFill) {
            this.center = new Point(coords.get(0), coords.get(1));
            this.radius = coords.get(2);
            this.isFill = f;
            this.FillColor = cFill;
            this.strokeColor = cStroke;
            this.elementName = n;
    }

    @Override
    public String getHelp() {
        return ("\n" + "Utilisation Cercle : "
                + "\n\t" + "circle <nom> <coordonnéesX> <coordonnéesY> <rayon>"
                + "\n\t\t" + "coordonnées X et Y : coordonnées du point en haut "
                + "\n\t\t" + "rayon : rayon du cercle"
                + "\n"
                + "\n\t" + "Exemple :"
                + "\n\t" + "	java allumettes.Jouer Xavier@humain "
                + "Ordinateur@naif"
                + "\n"
        );
    }

    @Override
    public void draw(SVGGraphics2D graphics2D) {
        // Dessiner le cercle avec le SVGGraphics2D
        graphics2D.setColor(getStrokeColor());

        int x = (int) Math.round(center.getX() - radius);
        int y = (int) Math.round(center.getY() - radius);
        int diametre = (int) Math.round(radius * 2);

        // Dessiner le contour du cercle
        graphics2D.drawOval(x, y, diametre, diametre);

        if (isFill) {
            // Remplir le cercle
            graphics2D.setColor(getFillColor());
            graphics2D.fillOval(x, y, diametre, diametre);
        }
    }

}
