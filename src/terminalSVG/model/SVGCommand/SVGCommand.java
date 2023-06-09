package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

public interface SVGCommand {

    String getName();
    String getHelp();
    void execute(SVGPreview svgPreview, String shapeName);
}
