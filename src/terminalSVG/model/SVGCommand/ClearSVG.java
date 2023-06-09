package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;

public class ClearSVG implements SVGCommand {
    public ClearSVG(Map<String, Object> instruction) {
    }

    @Override
    public String getName() {
        return null;
    }
    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public void execute(SVGPreview svgPreview, String elementName) {
        svgPreview.clearShapeList();
    }
    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
       if(!(elements.length == 1)){
            throw new IllegalArgumentException("Ne pas préciser d'arguments");
        }
    }
}