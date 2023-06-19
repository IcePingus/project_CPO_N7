package test.terminal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import terminalSVG.model.Instruction;
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
	Instruction i1, i2, i3, i4, i5;
	
	@Before public void setUp() {
		c1 = new ClearSVG();
		
		
		coordc1 = new ArrayList<>();
		
		coordc1.add(100.0);
		coordc1.add(150.0);
		coordc1.add(50.0);

		i1 = new Instruction("circle","toto",Color.BLUE, Color.RED);
		i1.setCoords(coordc1);
		i2 = new Instruction("circle","trotro",Color.GREEN, Color.YELLOW );
		i2.setCoords(coordc1);

		csvg1 = new CircleSVG(i1);
		csvg2 = new CircleSVG(i2);

		csvg3 = new CircleSVG(i1);
		csvg4 = new CircleSVG(i2);
		
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

		i3 = new Instruction("erase","titi");
		i4 = new Instruction("erase","trotrotro");
		i5 = new Instruction("rename","toto1");
		i5.setOldName("tutu");
		i5.setName("toto1");

		e1 = new EraseSVG(i3);
		e2 = new EraseSVG(i4);
		r1 = new RenameSVG(i5);
		
		
	}
	
	@Test public void TestClearExecute() {
		c1.execute(svgP);
		assertTrue(svgP.getShapeList().isEmpty());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestClearExecuteException() {
		c1.execute(svgP3);
	}
	
	@Test public void TestEraseExecute() {
		e1.execute(svgP2);
		assertFalse(svgP2.getShapeList().containsKey("titi"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestEraseExecuteException() {
		e2.execute(svgP2);
		assertTrue(svgP2.getShapeList().containsKey("toto1"));
	}
	
	@Test public void TestRenameExecute() {
		r1.execute(svgP4);
		assertFalse(svgP4.getShapeList().containsKey("tutu"));
		assertTrue(svgP4.getShapeList().containsKey("toto1"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestRenameExecuteException() {
		r1.execute(svgP2);
	}

}
