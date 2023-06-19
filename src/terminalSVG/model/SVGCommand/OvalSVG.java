package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Oval svg.
 *
 * @author Team 3
 */
public class OvalSVG extends DrawShapeAction {

    private Point center;
    private double width;
    private double height;
    private final Integer COORDS_LIST_SIZE = 4;

    private static final String COMMAND_NAME = "oval";

    private final List<String> description = new ArrayList<>(List.of(
            "Oval : Création d'un oval",
            "commande : oval <nom> <coordonnéesX> <coordonnéesY> <largeur> <hauteur> [-s contour] [-f remplissage]",
            " X / Y : coordonnées de la forme",
            "largeur / hauteur : largeur & hauteur de la forme",
            "contour : couleur de contour du oval",
            "remplissage : couleur de remplissage du oval",
            "Exemple :",
            "----------------------------------------------"
    ));

    /**
     * Instantiates a new Oval svg.
     *
     * @param name        the name of the oval
     * @param coords      the coordinates of the center of the oval
     * @param isFill      is the oval filled
     * @param strokeColor the stroke color of the oval
     * @param fillColor   the fill color of the oval
     */

    public OvalSVG(Instruction instruction) {
        super(instruction.getName(), instruction.isFilled(), instruction.getStrokeColor(), instruction.getFillColor());
        assert instruction.getCoords().size() == COORDS_LIST_SIZE;
        this.center = new Point(instruction.getCoords().get(0), instruction.getCoords().get(1));
        this.width = instruction.getCoords().get(2);
        this.height = instruction.getCoords().get(3);
    }

    public OvalSVG() {
    }


    public void draw(SVGPreview svgPreview) {
        // Dessiner le cercle avec le SVGGraphics2D
        svgPreview.getSVGGraphics().setColor(getStrokeColor());

        // Dessiner le contour du cercle
        svgPreview.getSVGGraphics().drawOval((int) center.getX(), (int) center.getY(), (int) width, (int) height);

        if (isFill) {
            // Remplir le cercle
            svgPreview.getSVGGraphics().setColor(this.fillColor);
            svgPreview.getSVGGraphics().fillOval((int) center.getX(), (int) center.getY(), (int) width, (int) height);
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
    public void resize(Double newWidth, Double newHeight) {
        Double w = newWidth != null ? newWidth : 0.0;
        Double h = newHeight != null ? newHeight : 0.0;

        if (w < 0.0 || h < 0.0) {
            throw new IllegalArgumentException("Width and height must be non-negative");
        }

        this.setWidth(w);
        this.setHeight(h);
    }

    public List<String> getHelp() {
        return this.description;
    }

    /**
     * Gets center of the oval.
     *
     * @return the center of the oval
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
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(double width) {
        if (width == 0.0) {
            width = this.getWidth();
        }
        this.width = width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(double height) {
        if (height == 0.0) {
            height = this.getHeight();
        }
        this.height = height;
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
