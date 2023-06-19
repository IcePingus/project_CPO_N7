package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.ArrayList;
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
    private final List<String> description = new ArrayList<>(List.of(
            "Carré : Création d'un carré",
            "commande : square <nom> <coordonnéesX> <coordonnéesY> <longueur> [-s contour] [-f remplissage]",
            " X / Y : coordonnées de la forme",
            "longueur : longueur du côté",
            "contour : couleur de contour du carré",
            "remplissage : couleur de remplissage du carré",
            "Exemple :",
            "----------------------------------------------"
    ));


    /**
     * Constructs a SquareSVG object with the specified parameters.
     *
     * @param name        The name of the square.
     * @param coords      The coordinates of the top-left point and the side length.
     * @param isFill      Determines if the square should be filled.
     * @param strokeColor The color of the stroke.
     * @param fillColor   The color of the fill.
     */

    public SquareSVG(Instruction instruction) {
        super(instruction.getName(), instruction.isFilled(), instruction.getStrokeColor(), instruction.getFillColor());
        assert instruction.getCoords().size() == COORDS_LIST_SIZE;
        this.point = new Point(instruction.getCoords().get(0), instruction.getCoords().get(1));
        this.sideLength = instruction.getCoords().get(2);
    }

    public SquareSVG() {
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
    public void resize(Double newWidth, Double newHeight) {
        if (newWidth != null && newHeight != null) {
            throw new IllegalArgumentException("Either newWidth or newHeight should be provided, but not both");
        }

        Double l = newWidth != null ? newWidth : newHeight;
        if (l == null || l < 0.0) {
            throw new IllegalArgumentException("Width or height must be non-negative");
        }

        this.setSideLength(l);
    }


    /**
     * Gets the help information for using the square command.
     *
     * @return The help information for the square command.
     */

    public List<String> getHelp() {
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