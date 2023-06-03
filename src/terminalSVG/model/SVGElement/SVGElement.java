package terminalSVG.model.SVGElement;

import org.apache.batik.svggen.SVGGraphics2D;

public interface SVGElement {
	
	 boolean fill = false;
	
     String Help();
 
 void Dessiner(SVGGraphics2D svgform);
}