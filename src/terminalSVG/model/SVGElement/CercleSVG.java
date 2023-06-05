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

public class CercleSVG implements SVGElement {

    private Point centre;
    private double largeur;
    private double hauteur;
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



    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public CercleSVG(String name ,double x, double y, double largeur, double hauteur, boolean fill, Color cstroke, Color cfill) {
        this.centre = new Point(x,y);
        this.largeur = largeur;
        this.hauteur = hauteur;
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

        // int x = (int) Math.round(centre.getX() - largeur);
        // int y = (int) Math.round(centre.getY() - largeur);
        //int diametre = (int) Math.round(largeur * 2);

        // Dessiner le contour du cercle
        svgform.drawOval((int)centre.getX(), (int)centre.getY(), (int)largeur, (int)hauteur);

        if (fill) {
            // Remplir le cercle
            svgform.setColor(getColorfill());
            svgform.fillOval((int)centre.getX(), (int)centre.getY(), (int)largeur, (int)hauteur);
        }
    }

}
