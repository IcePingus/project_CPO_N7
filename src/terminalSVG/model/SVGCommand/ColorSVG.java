package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;
import terminalSVG.model.parser.Parser;

import java.awt.*;
import java.util.Map;

/**
 * The type Color svg.
 */
public class ColorSVG implements SVGCommand {

    private Color color;

    /**
     * Instantiates a new Color svg.
     *
     * @param instruction the instruction strokeColor
     */
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

    /**
     * Parse command.
     *
     * @param instruction the instruction to parse
     * @param elements    the elements of the instruction
     * @throws IllegalArgumentException the illegal argument exception
     */
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