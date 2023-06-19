package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Polygon svg.
 *
 * @author Team 3
 */
public class PolygonSVG extends DrawShapeAction {
    private List<Point> points;
	private final Integer COORDS_LIST_SIZE = 4;
	private static final String COMMAND_NAME = "polygon";
	private final String description = ("\n" + "Polygone : Création d'un polygone"
			+ "\n" + "commande : polygon <nom> <coordonnéesX>* <coordonnéesY>* [-s contour] [-f remplissage]"
			+ "\n" + " X / Y : coordonnées des points de la forme"
			+ "\n" + "largeur / hauteur : largeur & hauteur de la forme"
			+ "\n" + "contour : couleur de contour du polygone"
			+ "\n" + "remplissage : couleur de remplissage du polygone"
			+ "\n" + "Exemple :"
			+ "\n" + "----------------------------------------------"
	);

	/**
	 * Instantiates a new Polygon svg.
	 *
	 * @param name        the name of the polygon
	 * @param coords      the coordinates of the points of the polygon
	 * @param isFill      is the polygon is filled or not
	 * @param strokeColor the stroke color of the polygon
	 * @param fillColor   the fill color of the polygon
	 */
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

	public PolygonSVG(){
	}

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
			svgPreview.getSVGGraphics().setColor(this.fillColor);
			svgPreview.getSVGGraphics().fill(polygon);
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
		for(Point point : points){
			point.translater(dx,dy);
		}
	}

	@Override
	public void resize(Map<String, Object> sizes) {
		throw new IllegalArgumentException("Resize impossible pour un polygone");
	}

	public String getHelp() {
		return this.description;
	}

	/**
	 * Gets the points of the polygon.
	 *
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * Sets points of the polygon.
	 *
	 * @param points the points
	 */
	public void setPoints(List<Point> points) {
		this.points = points;
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