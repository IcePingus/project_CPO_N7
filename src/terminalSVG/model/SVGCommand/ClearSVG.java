package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.List;
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
    public void execute(SVGPreview svgPreview) {
        svgPreview.clear();
    }
    public static void parseCommand(Map<String, Object> instruction, String[] elements) {
        if(elements.length == 1){
            instruction.put("elementAction", elements[0].trim());
        }
    }
}