package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;
import terminalSVG.model.parser.Parser;

import java.awt.*;
import java.util.Map;

public class ColorSVG implements SVGCommand {

    private Color color;

    public ColorSVG(Map<String, Object> instruction) {
        this.color = (Color) instruction.get("strokeColor");
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
    public void execute(SVGPreview svgPreview, String shapeName) {
        svgPreview.setDefaultColor(color);
    }

    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
        if (!(elements.length == 2)) {
            throw new IllegalArgumentException("Préciser une nouvelle couleur");
        }
        try {
            instruction.put("strokeColor",Parser.convertStringToColor(elements[1].trim()));
        } catch (IllegalArgumentException e){
            throw e;
        }

    }
}