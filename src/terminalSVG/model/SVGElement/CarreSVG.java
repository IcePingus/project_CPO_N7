package terminalSVG.model.SVGElement;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CarreSVG implements SVGElement {

    private Point point;
    private double sideLength;
    private boolean fill;
    private Color colorFill;
    private Color colorStroke;
    private String name;
    
    

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
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public Color getColorFill() {
		return colorFill;
	}

	public void setColorFill(Color colorFill) {
		this.colorFill = colorFill;
	}

	public Color getColorStroke() {
		return colorStroke;
	}

	public void setColorStroke(Color colorStroke) {
		this.colorStroke = colorStroke;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CarreSVG(String name, double x, double y, double sideLength, boolean fill, Color colorFill, Color colorStroke) {
        point = new Point(x,y);
        this.sideLength = sideLength;
        this.fill = fill;
        this.colorFill = colorFill;
        this.colorStroke = colorStroke;
        this.name = name;
    }

    @Override
    public void Dessiner(SVGGraphics2D g2d) {
        g2d.setColor(colorStroke);

        int x = (int) Math.round(point.getX());
        int y = (int) Math.round(point.getY());
        int length = (int) Math.round(sideLength);

        // Dessiner le contour du carré
        g2d.drawRect(x, y, length, length);

        if (fill) {
            // Remplir le carré
            g2d.setColor(colorFill);
            g2d.fillRect(x, y, length, length);
        }
    }

	@Override
	public String Help() {
		// TODO Auto-generated method stub
		return null;
	}
}
