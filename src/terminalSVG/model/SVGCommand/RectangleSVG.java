package terminalSVG.model.SVGCommand;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.List;


public class RectangleSVG extends DrawShapeAction {
    private Point point;
    private double sideLength1;
    private double sideLength2;
    private static final Integer COORDS_LIST_SIZE = 4;

    private static final String COMMAND_NAME = "rectangle";
    private final String description = ("\n" + "Utilisation Rectangle : "
    );

    public RectangleSVG() {
    }

    public RectangleSVG(String name, List<Double> coords, boolean isFill, Color strokeColor, Color fillColor) {
        super(name, isFill, strokeColor, fillColor);
        assert coords.size() == COORDS_LIST_SIZE;
        this.point = new Point(coords.get(0),coords.get(1));
        this.sideLength1 = coords.get(2);
        this.sideLength2 = coords.get(3);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public double getSideLength1() {
        return sideLength1;
    }

    public void setSideLength1(double sideLength1) {
        this.sideLength1 = sideLength1;
    }

    public double getSideLength2() {
        return sideLength2;
    }

    public void setSideLength2(double sideLength2) {
        this.sideLength2 = sideLength2;
    }

    public static String getCommandName() {
        return COMMAND_NAME;
    }
    public Integer getCoordsListSize() {
        return COORDS_LIST_SIZE;
    }
    @Override
    public String getHelp() {
        return this.description;
    }

    @Override
    public void execute(SVGGraphics2D graphics2D) {
        graphics2D.setColor(getStrokeColor());

        int x = (int) Math.round(point.getX());
        int y = (int) Math.round(point.getY());
        int s1 = (int) Math.round(sideLength1);
        int s2 = (int) Math.round(sideLength2);

        // Dessiner le contour du carré
        graphics2D.drawRect(x, y, s1, s2);

        if (isFill) {
            // Remplir le carré
            graphics2D.setColor(fillColor);
            graphics2D.fillRect(x, y, s1, s2);
        }
    }
}