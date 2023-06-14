package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.List;

public class CircleSVG extends DrawShapeAction {
    private Point center;
    private double radius;
    private final Integer COORDS_LIST_SIZE = 3;
    private static final String COMMAND_NAME = "circle";
    private final String description = ("\n" + "Utilisation Cercle : "
            + "\n\t" + "circle <nom> <coordonnéesX> <coordonnéesY> <rayon>"
            + "\n\t\t" + "coordonnées X et Y : coordonnées du point en haut "
            + "\n\t\t" + "rayon : rayon du cercle"
            + "\n"
            + "\n\t" + "Exemple :"
            + "\n\t" + "	java allumettes.Jouer Xavier@humain "
            + "Ordinateur@naif"
            + "\n"
    );

    public CircleSVG(String n, List<Double> coords, boolean f, Color cStroke, Color cFill) {
        super(n, f, cStroke, cFill);
        assert coords.size() == COORDS_LIST_SIZE;
        this.center = new Point(coords.get(0), coords.get(1));
        this.radius = coords.get(2);
    }

    public void draw(SVGPreview svgPreview) {
        // Dessiner le cercle avec le SVGGraphics2D
        svgPreview.getSVGGraphics().setColor(getStrokeColor());

        int x = (int) Math.round(center.getX() - radius);
        int y = (int) Math.round(center.getY() - radius);
        int diametre = (int) Math.round(radius * 2);

        // Dessiner le contour du cercle
        svgPreview.getSVGGraphics().drawOval(x, y, diametre, diametre);

        if (isFill) {
            // Remplir le cercle
            svgPreview.getSVGGraphics().setColor(this.fillColor);
            svgPreview.getSVGGraphics().fillOval(x, y, diametre, diametre);
        }
    }

    @Override
    public void translate(Double dx, Double dy) {
        if(dx == null){
            dx = 0.0;
        }
        if(dy == null){
            dy = 0.0;
        }
        this.center.translater(dx, dy);
    }

    public String getHelp() {
        return this.description;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public static String getCommandName() {
        return COMMAND_NAME;
    }

    public Integer getCoordsListSize() {
        return COORDS_LIST_SIZE;
    }

}
