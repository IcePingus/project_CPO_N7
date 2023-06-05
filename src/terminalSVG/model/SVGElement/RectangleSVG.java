package terminalSVG.model.SVGElement;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;


public class RectangleSVG implements SVGElement {
    private Point point;
    private double sideLength1;
    private double sideLength2;
    private boolean isFill;
    private Color fillColor;
    private Color strokeColor;
    private String name;
    private final String commandName = "rectangle";

    public Point getPoint() {
        return point;
    }

    public double getSideLength1() {
        return sideLength1;
    }

    public double getSideLength2() {
        return sideLength2;
    }

    public boolean isFill() {
        return isFill;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setSideLength1(double sideLength1) {
        this.sideLength1 = sideLength1;
    }

    public void setSideLength2(double sideLength2) {
        this.sideLength2 = sideLength2;
    }

    public void setFill(boolean fill) {
        isFill = fill;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public RectangleSVG(){
    }

    public RectangleSVG(String name, double x, double y, double sideLength1, double sideLength2, boolean f, Color cFill, Color cStroke) {
        point = new Point(x,y);
        this.sideLength1 = sideLength1;
        this.sideLength2 = sideLength2;
        this.isFill = f;
        this.fillColor = cFill;
        this.strokeColor = cStroke;
        this.name = name;
    }

    @Override
    public void draw(SVGGraphics2D g2d) {
        g2d.setColor(getStrokeColor());

        int x = (int) Math.round(point.getX());
        int y = (int) Math.round(point.getY());
        int s1 = (int) Math.round(sideLength1);
        int s2 = (int) Math.round(sideLength2);

        // Dessiner le contour du carré
        g2d.drawRect(x, y, s1, s2);

        if (isFill) {
            // Remplir le carré
            g2d.setColor(fillColor);
            g2d.fillRect(x, y, s1, s2);
        }
    }

    @Override
    public String Help() {
        // TODO Auto-generated method stub
        return null;
    }
}
