package test.terminal;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import terminalSVG.model.SVGPreview;
import terminalSVG.model.SVGCommand.CircleSVG;



public class TestCircleSVG {
	List<Double> coordc1 , coordc2;
	CircleSVG c1, c2;
	Color cFillc1, cStrokec1, cFillc2, cStrokec2;
	boolean fillc1 , fillc2;
	String namec1, namec2;
	SVGPreview svgp, svgp2, svgpcomp1,svgpcomp2;
	String strSVG1, strSVG2, strSVGcomp1,strSVGcomp2;
	
	
	// précision pour les comparaisons réelle
	public final static double EPSILON = 0.001;
	
	@Before public void setUp() {
		svgp = new SVGPreview();
		svgp2 = new SVGPreview();
		
		svgpcomp1 = new SVGPreview();
		svgpcomp2 = new SVGPreview();
		
		
		coordc1 = new ArrayList<>();
		coordc2 = new ArrayList<>();
		
		coordc1.add(100.0);
		coordc1.add(150.0);
		coordc1.add(50.0);
		
		coordc2.add(175.0);
		coordc2.add(253.0);
		coordc2.add(78.0);
		cFillc1 = Color.BLUE;
		cStrokec1 = Color.RED;
		cStrokec2 = Color.GREEN;
		
		namec1 = "toto";
		namec2= "trotro";
		fillc1 = true;
		fillc2 = false;
		
		svgpcomp1.getSVGGraphics().setColor(Color.RED);
		svgpcomp1.getSVGGraphics().drawOval(100, 150, 50, 50);
		svgpcomp1.getSVGGraphics().setColor(Color.BLUE);
		svgpcomp1.getSVGGraphics().fillOval(100, 150, 50, 50);
		strSVGcomp1 = svgpcomp1.getSVGCode();
		
		svgpcomp2.getSVGGraphics().setColor(Color.GREEN);
		svgpcomp2.getSVGGraphics().drawOval(175, 253, 78, 78);
		strSVGcomp2 = svgpcomp2.getSVGCode();

		c1 = new CircleSVG(namec1,coordc1, fillc1, cStrokec1, cFillc1 );	
		c2 = new CircleSVG(namec2,coordc2, fillc2, cStrokec2, cFillc2 );
		
	}
	
	@Test public void testColor() {
		assertEquals("la couleur de contour n'est pas bonne !", Color.RED , c1.getStrokeColor());
		assertEquals("la couleur de remplissage n'est pas bonne !", Color.BLUE , c1.getFillColor());
		assertEquals("le fill n'est pas activé alors que fill a true", true , c1.isFill());
		assertEquals("la couleur de contour n'est pas bonne !", Color.GREEN , c2.getStrokeColor());
		assertEquals("la couleur de remplissage n'est pas bonne !", null , c2.getFillColor());
		assertEquals("le fill est activé alors que fill est a false", false , c2.isFill());
	}
	
	@Test public void testRayon() {
		assertEquals("Le rayon n'est pas bon !", 50.0 , c1.getRadius(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 78.0 , c2.getRadius(), EPSILON);
	}
	
	@Test public void testName() {
		assertEquals("Le nom n'est pas bon !", "toto".equals(c1.getName()) ,true );
		assertEquals("Le nom n'est pas bon ! !", "trotro".equals(c2.getName()) , true);
	}
	
	@Test public void testCoordonnees() {
		assertEquals("Le rayon n'est pas bon !", 100.0 , c1.getCenter().getX(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 150.0 , c1.getCenter().getY(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 175.0 , c2.getCenter().getX(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 253.0 , c2.getCenter().getY(), EPSILON);
	}
	
	@Test public void testDraw() {
		c1.draw(svgp);	
		strSVG1 = svgp.getSVGCode(); 
		 assertEquals("Le code SVG n'est pas identitique !", strSVGcomp1.equals(strSVG1), true);
	    		
	    c2.draw(svgp2);	
	    strSVG2 = svgp2.getSVGCode();
	    assertEquals("Le code SVG n'est pas identitique !", strSVGcomp2.equals(strSVG2), true);
	    
	}

}
