package terminalSVG.model.SVGElement;

import org.apache.batik.svggen.SVGGraphics2D;

public interface SVGElement {

    String getHelp();
    void draw(SVGGraphics2D graphics2D);
}
