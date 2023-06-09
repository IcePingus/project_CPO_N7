package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PolygonSVG extends DrawShapeAction {
    private List<Point> points;
	private final Integer COORDS_LIST_SIZE = 4;

	private static final String COMMAND_NAME = "polygon";
	private final String description = ("\n" + "Utilisation Polygon : "
	);

	public PolygonSVG() {
	}
	public PolygonSVG(String name, List<Double> coords, boolean isFill, Color strokeColor, Color fillColor) {
		super(name, isFill, strokeColor, fillColor);
		assert coords.size() >= COORDS_LIST_SIZE;
		assert (coords.size()% 2 != 0);

		points = new ArrayList<>();

		for (int i = 0; i < coords.size(); i += 2) {
			double x = coords.get(i);
			double y = coords.get(i + 1);
			points.add(new Point(x, y));
		}
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
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
	public void draw(SVGPreview svgPreview) {
		svgPreview.getSVGGraphics().setColor(getStrokeColor());

		// Créer les tableaux de coordonnées x et y
		int[] xArray = new int[points.size()];
		int[] yArray = new int[points.size()];

		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			xArray[i] = (int) point.getX();
			yArray[i] = (int) point.getY();
		}

		// Créer le polygone avec les coordonnées
		Polygon polygon = new Polygon(xArray, yArray, points.size());

		// Dessiner le polygone avec le SVGGraphics2D
		svgPreview.getSVGGraphics().setColor(getStrokeColor());
		svgPreview.getSVGGraphics().draw(polygon);

		if (isFill) {
			// Remplir le polygone avec une couleur spécifique
			svgPreview.getSVGGraphics().setColor(getFillColor());
			svgPreview.getSVGGraphics().fill(polygon);
		}
	}
}
