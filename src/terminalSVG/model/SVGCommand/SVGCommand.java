package terminalSVG.model.SVGCommand;

import org.apache.batik.svggen.SVGGraphics2D;

public interface SVGCommand {

    String getName();
    String getHelp();
    void execute(SVGGraphics2D graphics2D);
}
