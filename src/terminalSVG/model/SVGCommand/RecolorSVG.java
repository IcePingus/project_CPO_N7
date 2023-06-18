package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;
import terminalSVG.model.parser.Parser;

import java.awt.*;
import java.util.Map;

/**
 * The command Recolor svg.
 *
 * @author Team 3
 */
public class RecolorSVG implements SVGCommand {

    private Color sColor;
    private Color fColor;

    /**
     * Instantiates a new Recolor svg.
     *
     * @param instruction the instruction of recolor
     */
    public RecolorSVG(Map<String, Object> instruction) {
        this.sColor =  (Color) instruction.get("strokeColor");
        this.fColor =  (Color) instruction.get("fillColor");
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
        svgPreview.setNewColorShape(sColor, fColor,shapeName);
    }

    /**
     * Parse command.
     *
     * @param instruction the instruction to parse
     * @param elements    the elements of the instruction
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {

        if (elements.length < 3) {
            throw new IllegalArgumentException("not enough arguments");
        }
        else if (elements.length > 6) {
            throw new IllegalArgumentException("You have too many argument");
        }
        for (int i = 0; i < elements.length; i++) {
            String element = elements[i].trim();

            if (element.equals("-s")) {
                if(!(i + 1 < elements.length)) {
                    throw new IllegalArgumentException("need argument after option -s");
                }

                instruction.put("strokeColor",Parser.convertStringToColor(elements[i + 1].trim()));

            } else if (element.equals("-f")) {
                if(!(i + 1 < elements.length)) {
                    throw new IllegalArgumentException("need argument after option -f");
                }

                instruction.put("fillColor",Parser.convertStringToColor(elements[i + 1].trim()));
            }
        }
        instruction.put("elementName", elements[1].trim());
    }
}