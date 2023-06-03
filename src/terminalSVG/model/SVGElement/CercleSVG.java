package terminalSVG.model.SVGElement;

import org.apache.batik.svggen.SVGGraphics2D;

import java.awt.*;

public class CercleSVG implements SVGElement {

    private Point centre;
    private double rayon;
    private boolean fill;
    Color colorfill = null ;
    Color colorstroke;
    private String name;
    
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
	

    public Point getCentre() {
		return centre;
	}

	public void setCentre(Point centre) {
		this.centre = centre;
	}

	public double getRayon() {
		return rayon;
	}

	public void setRayon(double rayon) {
		this.rayon = rayon;
	}

	public CercleSVG(String name ,double x, double y, double rayon, boolean fill, Color cstroke, Color cfill) {
        this.centre = new Point(x,y);
        this.rayon = rayon;
        this.fill = fill;
        this.colorfill = cfill;
        this.colorstroke = cstroke;
        this.name = name;
    }

    @Override
    public String Help() {
        return "Ceci est l'aide de CercleSVG.";
    }

    @Override
    public void Dessiner(SVGGraphics2D svgform) {
        // Dessiner le cercle avec le SVGGraphics2D
        svgform.setColor(getColorstroke());

        int x = (int) Math.round(centre.getX() - rayon);
        int y = (int) Math.round(centre.getY() - rayon);
        int diametre = (int) Math.round(rayon * 2);

        // Dessiner le contour du cercle
        svgform.drawOval(x, y, diametre, diametre);

        if (fill) {
            // Remplir le cercle
        	svgform.setColor(getColorfill());
            svgform.fillOval(x, y, diametre, diametre);
        }
    }

}
