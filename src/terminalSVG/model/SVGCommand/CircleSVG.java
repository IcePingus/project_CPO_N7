package terminalSVG.model.SVGCommand;

import org.apache.batik.svggen.SVGGraphics2D;

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

    public CircleSVG() {
    }
    public CircleSVG(String n, List<Double> coords, boolean f, Color cStroke, Color cFill) {
        super(n,f,cStroke,cFill);
        assert coords.size() == COORDS_LIST_SIZE;
        this.center = new Point(coords.get(0), coords.get(1));
        this.radius = coords.get(2);
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

    public String getHelp() {
        return this.description;
    }

    public void execute(SVGGraphics2D graphics2D) {
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
