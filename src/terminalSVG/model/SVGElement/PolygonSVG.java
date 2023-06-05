package terminalSVG.model.SVGElement;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PolygonSVG {
    private List<Point> points;
    private boolean fill;
    private Color colorfill = null ;
    private Color colorstroke;
    private String name;
	private final String commandName = "polygon";

    public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	public Color getColorfill() {
		return colorfill;
	}
	public void setColorfill(Color colorfill) {
		this.colorfill = colorfill;
	}
	public Color getColorstroke() {
		return colorstroke;
	}
	public void setColorstroke(Color colorstroke) {
		this.colorstroke = colorstroke;
	}
	public String getCommandName() {
		return this.commandName;
	}

	public PolygonSVG() {
	}

	public PolygonSVG(String name, boolean fill, Color cstroke, Color cfill, List<Double> coordinates) {
	    if (coordinates.size() % 2 != 0) {
	        throw new IllegalArgumentException("Le nombre de coordonnées doit être pair.");
	    }
	    this.name = name;
	    points = new ArrayList<>();
	    this.fill = fill;

	    for (int i = 0; i < coordinates.size(); i += 2) {
	        double x = coordinates.get(i);
	        double y = coordinates.get(i + 1);
	        points.add(new Point(x, y));
	    }
	    
	    this.colorstroke = cstroke;
	    this.colorfill = cfill;
	}


    public void draw(SVGGraphics2D g2d) {
    	
    	g2d.setColor(getColorstroke());

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
        g2d.setColor(getColorstroke());
        g2d.draw(polygon);

        if (fill) {
            // Remplir le polygone avec une couleur spécifique
        	g2d.setColor(getColorfill());
            g2d.fill(polygon);
        }

       
    }

}
