package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;

/**
 * The type Clear svg.
 */
public class ClearSVG implements SVGCommand {
    /**
     * Instantiates a new Clear svg.
     *
     * @param instruction the instruction of clear
     */
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
    public void execute(SVGPreview svgPreview, String shapeName) {
        svgPreview.clearShapeList();
    }

    /**
     * Parse command.
     *
     * @param instruction the instruction to parse
     * @param elements    the elements of the instruction
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
       if(!(elements.length == 1)){
            throw new IllegalArgumentException("Ne pas préciser d'arguments");
        }
    }
}