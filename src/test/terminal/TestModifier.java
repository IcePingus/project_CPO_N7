package test.terminal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import terminalSVG.model.SVGPreview;
import terminalSVG.model.SVGCommand.CircleSVG;
import terminalSVG.model.SVGCommand.ClearSVG;
import terminalSVG.model.SVGCommand.EraseSVG;
import terminalSVG.model.SVGCommand.RenameSVG;

public class TestModifier {
	private Map<String, Object> elementList, elementList2, elementList3;
	ClearSVG c1;
	EraseSVG e1, e2;
	RenameSVG r1;
	List<Double> coordc1;
	CircleSVG csvg1, csvg2, csvg3, csvg4;
	SVGPreview svgP, svgP2, svgP3, svgP4;
	
	@Before public void setUp() {
		c1 = new ClearSVG();
		
		
		coordc1 = new ArrayList<>();
		
		coordc1.add(100.0);
		coordc1.add(150.0);
		coordc1.add(50.0);
		
		csvg1 = new CircleSVG("toto",coordc1, true, Color.BLUE, Color.RED );
		csvg2 = new CircleSVG("trotro",coordc1, true, Color.GREEN, Color.YELLOW );
		
		csvg3 = new CircleSVG("titi",coordc1, true, Color.BLUE, Color.RED );
		csvg4 = new CircleSVG("tutu",coordc1, true, Color.GREEN, Color.YELLOW );
		
		svgP = new SVGPreview();
		svgP2 = new SVGPreview();
		svgP3 = new SVGPreview();
		svgP4 = new SVGPreview();
		
		svgP.addElement("test", csvg1);
		svgP.addElement("test2", csvg2);
		
		svgP2.addElement("toto", csvg3);
		svgP2.addElement("titi", csvg2);
		
		svgP4.addElement("toto", csvg1);
		svgP4.addElement("tutu", csvg4);
		
		elementList = new Hashtable<>();
		elementList2 = new Hashtable<>();
		elementList3 = new Hashtable<>();
		
		elementList.put("elementName", "titi");
		elementList2.put("elementName", "trotrotro");
		elementList3.put("elementNewName", "toto1");
		
		e1 = new EraseSVG(elementList);
		e2 = new EraseSVG(elementList2);
		
		r1 = new RenameSVG(elementList3);
		
		
	}
	
	@Test public void TestClearExecute() {
		c1.execute(svgP, null);
		assertTrue(svgP.getShapeList().isEmpty());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestClearExecuteException() {
		c1.execute(svgP3, null);	
	}
	
	@Test public void TestEraseExecute() {
		e1.execute(svgP2, null);
		assertFalse(svgP2.getShapeList().containsKey("titi"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestEraseExecuteException() {
		e2.execute(svgP2, null);	
		assertTrue(svgP2.getShapeList().containsKey("toto1"));
	}
	
	@Test public void TestRenameExecute() {
		r1.execute(svgP4, "tutu");
		assertFalse(svgP2.getShapeList().containsKey("tutu"));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestRenameExecuteException() {
		r1.execute(svgP2, "absent");	
	}

}
