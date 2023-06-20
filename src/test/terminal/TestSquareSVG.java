package test.terminal;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import terminalSVG.model.SVGPreview;
import terminalSVG.model.SVGCommand.SquareSVG;



public class TestSquareSVG {
	List<Double> coordc1 , coordc2;
	SquareSVG s1;
	SquareSVG s2;
	Color cFills1, cStrokes1, cFills2, cStrokes2;
	boolean fills1 , fills2;
	String names1, names2;
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
		
		coordc2.add(437.0);
		coordc2.add(253.0);
		coordc2.add(67.0);
		
		cFills1 = Color.BLUE;
		cStrokes1 = Color.RED;
		cStrokes2 = Color.GREEN;
		
		names1 = "toto";
		names2= "trotro";
		fills1 = true;
		fills2 = false;
		
		svgpcomp1.getSVGGraphics().setColor(Color.RED);
		svgpcomp1.getSVGGraphics().drawRect(63, 89, 98, 98);
		svgpcomp1.getSVGGraphics().setColor(Color.BLUE);
		svgpcomp1.getSVGGraphics().fillRect(63, 89, 98, 98);
		strSVGcomp1 = svgpcomp1.getSVGCode();
		
		svgpcomp2.getSVGGraphics().setColor(Color.GREEN);
		svgpcomp2.getSVGGraphics().drawRect(437, 253, 67, 67);
		strSVGcomp2 = svgpcomp2.getSVGCode();

		s1 = new SquareSVG(names1,coordc1, fills1, cStrokes1, cFills1 );	
		s2 = new SquareSVG(names2,coordc2, fills2, cStrokes2, cFills2 );
		
	}
	
	@Test public void testColor() {
		assertEquals("la couleur de contour n'est pas bonne !", Color.RED , s1.getStrokeColor());
		assertEquals("la couleur de remplissage n'est pas bonne !", Color.BLUE , s1.getFillColor());
		assertEquals("le fill n'est pas activé alors que fill a true", true , s1.isFill());
		assertEquals("la couleur de contour n'est pas bonne !", Color.GREEN , s2.getStrokeColor());
		assertEquals("la couleur de remplissage n'est pas bonne !", null , s2.getFillColor());
		assertEquals("le fill est activé alors que fill est a false", false , s2.isFill());
	}
	
	@Test public void testCote() {
		assertEquals("Le cote n'est pas bon !", 98.0 , s1.getSideLength(), EPSILON);
		assertEquals("Le cote n'est pas bon !", 67.0 , s2.getSideLength(), EPSILON);
	}
	
	@Test public void testName() {
		assertEquals("Le nom n'est pas bon !", "toto".equals(s1.getName()) ,true );
		assertEquals("Le nom n'est pas bon !", "trotro".equals(s2.getName()) , true);
	}
	
	@Test public void testCoordonnees() {
		assertEquals("Le rayon n'est pas bon !", 63.0 , s1.getPoint().getX(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 89.0 , s1.getPoint().getY(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 437.0 , s2.getPoint().getX(), EPSILON);
		assertEquals("Le rayon n'est pas bon !", 253.0 , s2.getPoint().getY(), EPSILON);
	}
	
	@Test public void testDraw() {
		s1.draw(svgp);	
		strSVG1 = svgp.getSVGCode(); 
	    assertEquals("Le code SVG n'est pas identitique !", strSVGcomp1.equals(strSVG1), true);
	    		
	    s2.draw(svgp2);	
	    strSVG2 = svgp2.getSVGCode();
	    assertEquals("Le code SVG n'est pas identitique !", strSVGcomp2.equals(strSVG2), true);
	    
	}
	
	//CRÉÉ UNE FORME SVG DANS LE SETUP ET COMPARER CETTE CRÉÉ PAR DRAW DE CircleSVG

}
