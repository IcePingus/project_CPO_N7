package Test.terminal;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGDocument;

import terminalSVG.model.SVGPreview;
import terminalSVG.model.SVGCommand.RectangleSVG;




public class TestRectangleSVG {
	List<Double> coordc1 , coordc2;
	RectangleSVG r1;
	RectangleSVG r2;
	Color cFillr1, cStroker1, cFillr2, cStroker2;
	boolean fillr1 , fillr2;
	String namer1, namer2;
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
		
		coordc1.add(63.0);
		coordc1.add(89.0);
		coordc1.add(98.0);
		coordc1.add(120.0);
		
		coordc2.add(437.0);
		coordc2.add(253.0);
		coordc2.add(67.0);
		coordc2.add(35.0);
		
		cFillr1 = Color.BLUE;
		cStroker1 = Color.RED;
		cStroker2 = Color.GREEN;
		
		namer1 = "toto";
		namer2= "trotro";
		fillr1 = true;
		fillr2 = false;
		
		svgpcomp1.getSVGGraphics().setColor(Color.RED);
		svgpcomp1.getSVGGraphics().drawRect(63, 89, 98, 120);
		svgpcomp1.getSVGGraphics().setColor(Color.BLUE);
		svgpcomp1.getSVGGraphics().fillRect(63, 89, 98, 120);
		strSVGcomp1 = svgpcomp1.getSVGCode();
		
		svgpcomp2.getSVGGraphics().setColor(Color.GREEN);
		svgpcomp2.getSVGGraphics().drawRect(437, 253, 67, 35);
		strSVGcomp2 = svgpcomp2.getSVGCode();

		r1 = new RectangleSVG(namer1,coordc1, fillr1, cStroker1, cFillr1 );	
		r2 = new RectangleSVG(namer2,coordc2, fillr2, cStroker2, cFillr2 );
		
	}
	
	@Test public void testColor() {
		assertEquals("la couleur de contour n'est pas bonne !", Color.RED , r1.getStrokeColor());
		assertEquals("la couleur de remplissage n'est pas bonne !", Color.BLUE , r1.getFillColor());
		assertEquals("le fill n'est pas activé alors que fill a true", true , r1.isFill());
		assertEquals("la couleur de contour n'est pas bonne !", Color.GREEN , r2.getStrokeColor());
		assertEquals("la couleur de remplissage n'est pas bonne !", null , r2.getFillColor());
		assertEquals("le fill est activé alors que fill est a false", false , r2.isFill());
	}
	
	@Test public void testCote() {
		assertEquals("Le cote n'est pas bon !", 98.0 , r1.getSideLength1(), EPSILON);
		assertEquals("Le cote n'est pas bon !", 120.0 , r1.getSideLength2(), EPSILON);
		assertEquals("Le cote n'est pas bon !!", 67.0 , r2.getSideLength1(), EPSILON);
		assertEquals("Le cote n'est pas bon !!", 35.0 , r2.getSideLength2(), EPSILON);
	}
	
	@Test public void testName() {
		assertEquals("Le rayon n'est pas bon !", "toto" , r1.getName());
		assertEquals("Le rayon n'est pas bon !", "trotro" , r2.getName());
	}
	
	@Test public void testCoordonnees() {
		assertEquals("Le rayon n'est pas bon !", 63.0 , r1.getPoint().getX(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 89.0 , r1.getPoint().getY(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 437.0 , r2.getPoint().getX(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 253.0 , r2.getPoint().getY(), EPSILON);
	}
	
	@Test public void testDraw() {
		r1.draw(svgp);	
		strSVG1 = svgp.getSVGCode(); 
	    assertEquals("Le code SVG n'est pas identitique !", strSVGcomp1.equals(strSVG1), true);
	    		
	    r2.draw(svgp2);	
	    strSVG2 = svgp2.getSVGCode();
	    assertEquals("Le code SVG n'est pas identitique !", strSVGcomp2.equals(strSVG2), true);
	    
	}
	

}
