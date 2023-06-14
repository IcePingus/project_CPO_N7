package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.List;

public class SquareSVG extends DrawShapeAction {
    private Point point;
    private double sideLength;
	private static final Integer COORDS_LIST_SIZE = 3;
	private static final String COMMAND_NAME = "square";
	private final String description = ("\n" + "Utilisation Carré : "
	);

	public SquareSVG(String name,List<Double> coords, boolean isFill, Color strokeColor, Color fillColor) {
		super(name, isFill, strokeColor, fillColor);
		assert coords.size() == COORDS_LIST_SIZE;
		this.point = new Point(coords.get(0),coords.get(1));
		this.sideLength = coords.get(2);
	}

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

	@Override
	public void translate(Double dx, Double dy) {
		if(dx == null){
			dx = 0.0;
		}
		if(dy == null){
			dy = 0.0;
		}
		this.point.translater(dx,dy);
	}

	public String getHelp() {
		return this.description;
	}

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

	public static Integer getCoordsListSize() {
		return COORDS_LIST_SIZE;
	}

	public static String getCommandName() {
		return COMMAND_NAME;
	}
}
