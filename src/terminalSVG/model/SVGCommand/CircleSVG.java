package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * The type Circle svg.
 *
 * @author Team 3
 */
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
            + "\n\t" + ""
            + ""
            + "\n"
    );

    /**
     * Instantiates a new Circle svg.
     *
     * @param name    the name of the circle
     * @param coords  the coordinates of the center of the circle
     * @param isFill  is the circle filled or not
     * @param cStroke the c stroke
     * @param cFill   the c fill
     */
    public CircleSVG(String name, List<Double> coords, boolean isFill, Color cStroke, Color cFill) {
        super(name, isFill, cStroke, cFill);
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
        if (dx == null) {
            dx = 0.0;
        }
        if (dy == null) {
            dy = 0.0;
        }
        this.center.translater(dx, dy);
    }

    @Override
    public void resize(Map<String, Object> sizes) {
        Double r = 0.0;

        if (sizes.containsKey("newWidth") && !sizes.containsKey("newHeight")) {
            r = (Double) sizes.get("newWidth");
        } else if (!sizes.containsKey("newWidth") && sizes.containsKey("newHeight")) {
            r = (Double) sizes.get("newHeight");
        } else {
            throw new IllegalArgumentException("Either newWidth or newHeight should be provided, but not both");
        }

        if (r < 0.0) {
            throw new IllegalArgumentException("Width or height must be non-negative");
        }
        this.setRadius(r);
    }

    public String getHelp() {
        return this.description;
    }

    /**
     * Gets center.
     *
     * @return the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Sets center.
     *
     * @param center the center
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets radius.
     *
     * @param radius the radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Gets command name.
     *
     * @return the command name
     */
    public static String getCommandName() {
        return COMMAND_NAME;
    }

    /**
     * Gets coords list size.
     *
     * @return the coords list size
     */
    public Integer getCoordsListSize() {
        return COORDS_LIST_SIZE;
    }

}