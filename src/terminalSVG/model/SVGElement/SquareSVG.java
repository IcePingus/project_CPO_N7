package terminalSVG.model.SVGElement;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;

public class SquareSVG implements SVGElement {
    private Point point;
    private double sideLength;
    private boolean isFill;
    private Color fillColor;
    private Color strokeColor;
    private String name;
	private final String commandName = "square";

    public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public double getSideLength() {
		return sideLength;
	}
	public void setSideLength(double sideLength) {
		this.sideLength = sideLength;
	}
	public boolean isFill() {
		return isFill;
	}
	public void setFill(boolean fill) {
		this.isFill = fill;
	}
	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	public Color getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCommandName() {
		return this.commandName;
	}

	public SquareSVG(){
	}

	public SquareSVG(String n, double x, double y, double sideLength, boolean f, Color cStroke, Color cFill) {
        point = new Point(x,y);
        this.sideLength = sideLength;
		this.isFill = f;
		this.fillColor = cFill;
		this.strokeColor = cStroke;
		this.name = n;
    }

    @Override
    public void draw(SVGGraphics2D graphics2D) {
        graphics2D.setColor(strokeColor);

        int x = (int) Math.round(point.getX());
        int y = (int) Math.round(point.getY());
        int length = (int) Math.round(sideLength);

        // Dessiner le contour du carré
        graphics2D.drawRect(x, y, length, length);

        if (isFill) {
            // Remplir le carré
            graphics2D.setColor(fillColor);
            graphics2D.fillRect(x, y, length, length);
        }
    }

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return null;
	}
}
