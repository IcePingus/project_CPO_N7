package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.List;
import java.util.Map;


/**
 * The command to create a Rectangle.
 *
 * @author Team 3
 */
public class RectangleSVG extends DrawShapeAction {
    private Point point;
    private double sideLength1;
    private double sideLength2;
    private static final Integer COORDS_LIST_SIZE = 4;
    private static final String COMMAND_NAME = "rectangle";
    private final String description = ("\n" + "Rectangle : Création d'un rectangle"
            + "\n" + "commande : rectangle <nom> <coordonnéesX> <coordonnéesY> <largeur> <hauteur> [-s contour] [-f remplissage]"
            + "\n" + " X / Y : coordonnées de la forme"
            + "\n" + "largeur / hauteur : largeur & hauteur de la forme"
            + "\n" + "contour : couleur de contour du rectangle"
            + "\n" + "remplissage : couleur de remplissage du rectangle"
            + "\n" + "Exemple :"
            + "\n" + "----------------------------------------------"
    );

    /**
     * Instantiates a new Rectangle svg.
     *
     * @param name        the name of the rectangle
     * @param coords      the coordinates of the point left upper corner
     * @param isFill      is the rectangle filled or not
     * @param strokeColor the stroke color of the rectangle
     * @param fillColor   the fill color of the rectangle
     */
    public RectangleSVG(String name, List<Double> coords, boolean isFill, Color strokeColor, Color fillColor) {
        super(name, isFill, strokeColor, fillColor);
        assert coords.size() == COORDS_LIST_SIZE;
        this.point = new Point(coords.get(0), coords.get(1));
        this.sideLength1 = coords.get(2);
        this.sideLength2 = coords.get(3);
    }

    public RectangleSVG(){
    }

    public void draw(SVGPreview svgPreview) {
        svgPreview.getSVGGraphics().setColor(getStrokeColor());

        int x = (int) Math.round(point.getX());
        int y = (int) Math.round(point.getY());
        int s1 = (int) Math.round(sideLength1);
        int s2 = (int) Math.round(sideLength2);

        // Dessiner le contour du carré
        svgPreview.getSVGGraphics().drawRect(x, y, s1, s2);

        if (isFill) {
            // Remplir le carré
            svgPreview.getSVGGraphics().setColor(fillColor);
            svgPreview.getSVGGraphics().fillRect(x, y, s1, s2);
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
        this.point.translater(dx, dy);
    }

    @Override
    public void resize(Map<String, Object> sizes) {
        Double w = 0.0;
        Double h = 0.0;
        if (sizes.containsKey("newWidth")) {
            w = (Double) sizes.get("newWidth");
        }
        if (sizes.containsKey("newHeight")) {
            h = (Double) sizes.get("newHeight");
        }

        if (w < 0.0 || h < 0.0) {
            throw new IllegalArgumentException("Width and height must be non-negative");
        }

        this.setSideLength1(w);
        this.setSideLength2(h);
    }


    public String getHelp() {
        return this.description;
    }

    /**
     * Gets point of the rectangle.
     *
     * @return the point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Sets point of the rectangle.
     *
     * @param point the point
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * Gets side length 1.
     *
     * @return the side length 1
     */
    public double getSideLength1() {
        return sideLength1;
    }

    /**
     * Sets side length 1.
     *
     * @param sideLength1 the side length 1
     */
    public void setSideLength1(double sideLength1) {
        if (sideLength1 == 0.0) {
            sideLength1 = this.getSideLength1();
        }
        this.sideLength1 = sideLength1;
    }

    /**
     * Gets side length 2.
     *
     * @return the side length 2
     */
    public double getSideLength2() {
        return sideLength2;
    }

    /**
     * Sets side length 2.
     *
     * @param sideLength2 the side length 2
     */
    public void setSideLength2(double sideLength2) {
        if (sideLength2 == 0.0) {
            sideLength2 = this.getSideLength2();
        }
        this.sideLength2 = sideLength2;
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