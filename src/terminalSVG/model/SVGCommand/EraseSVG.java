package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;

/**
 * The type Erase svg.
 */
public class EraseSVG implements SVGCommand {

    private String eltName;

    /**
     * Instantiates a new Erase svg.
     *
     * @param instruction the instruction of erase
     */
    public EraseSVG(Map<String, Object> instruction) {
        this.eltName = (String) instruction.get("elementName");
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
        svgPreview.delElement(eltName);
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
            throw new IllegalArgumentException("Préciser le nom d'un élément");
        }
        instruction.put("elementName", elements[1].trim());
    }
}