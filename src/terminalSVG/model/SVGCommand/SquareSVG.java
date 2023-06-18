package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * This class represents an SVG command for drawing a square shape.
 *
 * @author Team 3
 */
public class SquareSVG extends DrawShapeAction {
    private Point point;
    private double sideLength;
    private static final Integer COORDS_LIST_SIZE = 3;
    private static final String COMMAND_NAME = "square";
    private final String description = ("\n" + "Utilisation Carré : "
    );

    /**
     * Constructs a SquareSVG object with the specified parameters.
     *
     * @param name        The name of the square.
     * @param coords      The coordinates of the top-left point and the side length.
     * @param isFill      Determines if the square should be filled.
     * @param strokeColor The color of the stroke.
     * @param fillColor   The color of the fill.
     */
    public SquareSVG(String name, List<Double> coords, boolean isFill, Color strokeColor, Color fillColor) {
        super(name, isFill, strokeColor, fillColor);
        assert coords.size() == COORDS_LIST_SIZE;
        this.point = new Point(coords.get(0), coords.get(1));
        this.sideLength = coords.get(2);
    }

    /**
     * Draws the square on the SVGPreview object.
     *
     * @param svgPreview The SVGPreview object on which to draw the square.
     */
    public void draw(SVGPreview svgPreview) {
        svgPreview.getSVGGraphics().setColor(strokeColor);

        int x = (int) Math.round(point.getX());
        int y = (int) Math.round(point.getY());
        int length = (int) Math.round(sideLength);

        // Dessiner le contour du carré
        svgPreview.getSVGGraphics().drawRect(x, y, length, length);

        if (isFill) {
            // Remplir le carré
            svgPreview.getSVGGraphics().setColor(fillColor);
            svgPreview.getSVGGraphics().fillRect(x, y, length, length);
        }
    }

    /**
     * Translates the square by the specified displacement values.
     *
     * @param dx The displacement value in the x-direction.
     * @param dy The displacement value in the y-direction.
     */
    @Override
    public void translate(Double dx, Double dy) {
        if (dx == null) {
            dx = 0.0;
        }
        if (dy == null) {
            dy = 0.0;
        }
        this.point.translater(dx, dy);
    }

    @Override
    public void resize(Map<String, Object> sizes) {
        Double l = 0.0;

        if (sizes.containsKey("newWidth") && !sizes.containsKey("newHeight")) {
            l = (Double) sizes.get("newWidth");
        } else if (!sizes.containsKey("newWidth") && sizes.containsKey("newHeight")) {
            l = (Double) sizes.get("newHeight");
        } else {
            throw new IllegalArgumentException("Either newWidth or newHeight should be provided, but not both");
        }

        if (l < 0.0) {
            throw new IllegalArgumentException("Width or height must be non-negative");
        }
        this.setSideLength(l);
    }

    /**
     * Gets the help information for using the square command.
     *
     * @return The help information for the square command.
     */

    public String getHelp() {
        return this.description;
    }

    /**
     * Gets the point representing the top-left corner of the square.
     *
     * @return The point representing the top-left corner of the square.
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Sets the point representing the top-left corner of the square.
     *
     * @param point The point representing the top-left corner of the square.
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * Gets the side length of the square.
     *
     * @return The side length of the square.
     */
    public double getSideLength() {
        return sideLength;
    }

    /**
     * Sets the side length of the square.
     *
     * @param sideLength The side length of the square.
     */
    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    /**
     * Gets the size of the coordinates list for a square command.
     *
     * @return The size of the coordinates list for a square command.
     */
    public static Integer getCoordsListSize() {
        return COORDS_LIST_SIZE;
    }

    /**
     * Gets the command name for a square command.
     *
     * @return The command name for a square command.
     */
    public static String getCommandName() {
        return COMMAND_NAME;
    }
}