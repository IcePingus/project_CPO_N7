package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;

/**
 * The type Erase svg.
 *
 * @author Team 3
 */
public class EraseSVG implements SVGCommand {

    private final String description = ("\n" + "Erase : Efface une forme"
            + "\n" + "commande : erase <nom>"
            + "\n" + "nom : nom d'une forme"
            + "\n" + "Exemple :"
            + "\n" + "----------------------------------------------"
    );
    private String eltName;

    /**
     * Instantiates a new Erase svg.
     *
     * @param instruction the instruction of erase
     */
    public EraseSVG(Map<String, Object> instruction) {
        this.eltName = (String) instruction.get("elementName");
    }

    public EraseSVG() {
    }

    @Override
    public String getName() {
        return eltName;
    }

    @Override
    public String getHelp() {
        return this.description;
    }

    @Override
    public String execute(SVGPreview svgPreview, String shapeName) {
        svgPreview.delElement(eltName);
        return "[-] " + eltName + "\n";
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