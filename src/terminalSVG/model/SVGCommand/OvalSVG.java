package terminalSVG.model.SVGCommand;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.List;

public class OvalSVG extends DrawShapeAction {

    private Point center;
    private double width;
    private double height;
    private final Integer COORDS_LIST_SIZE = 4;

    private static final String COMMAND_NAME = "oval";
    private final String description = ("\n" + "Utilisation Oval : "
    );

    public OvalSVG() {
    }
    public OvalSVG(String name, List<Double> coords, boolean isFill, Color strokeColor, Color fillColor) {
        super(name, isFill, strokeColor, fillColor);
        assert coords.size() == COORDS_LIST_SIZE;
        this.center = new Point(coords.get(0),coords.get(1));
        this.width = coords.get(2);
        this.height = coords.get(3);
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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

    @Override
    public void execute(SVGGraphics2D graphics2D) {
        // Dessiner le cercle avec le SVGGraphics2D
        graphics2D.setColor(getStrokeColor());

        // Dessiner le contour du cercle
        graphics2D.drawOval((int)center.getX(), (int)center.getY(), (int)width, (int)height);

        if (isFill) {
            // Remplir le cercle
            graphics2D.setColor(getFillColor());
            graphics2D.fillOval((int)center.getX(), (int)center.getY(), (int)width, (int)height);
        }
    }
}
