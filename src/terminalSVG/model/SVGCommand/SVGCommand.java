package terminalSVG.model.SVGCommand;

import org.apache.batik.svggen.SVGGraphics2D;
import terminalSVG.model.SVGPreview;

public interface SVGCommand {

    String getName();
    String getHelp();
    void execute(SVGPreview svgPreview);
}
