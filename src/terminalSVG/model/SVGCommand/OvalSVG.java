package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

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

    public OvalSVG(String name, List<Double> coords, boolean isFill, Color strokeColor, Color fillColor) {
        super(name, isFill, strokeColor, fillColor);
        assert coords.size() == COORDS_LIST_SIZE;
        this.center = new Point(coords.get(0),coords.get(1));
        this.width = coords.get(2);
        this.height = coords.get(3);
    }

    public void draw(SVGPreview svgPreview) {
        // Dessiner le cercle avec le SVGGraphics2D
        svgPreview.getSVGGraphics().setColor(getStrokeColor());

        // Dessiner le contour du cercle
        svgPreview.getSVGGraphics().drawOval((int)center.getX(), (int)center.getY(), (int)width, (int)height);

        if (isFill) {
            // Remplir le cercle
            svgPreview.getSVGGraphics().setColor(this.fillColor);
            svgPreview.getSVGGraphics().fillOval((int)center.getX(), (int)center.getY(), (int)width, (int)height);
        }
    }

    @Override
    public void translateX(Double dx) {
        this.center.translater(dx,0);
    }

    @Override
    public void translateY(Double dy) {
        this.center.translater(0, dy);
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
}
