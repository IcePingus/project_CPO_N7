package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Erase svg.
 *
 * @author Team 3
 */
public class EraseSVG implements SVGCommand {

    private final List<String> description = new ArrayList<>(List.of(
            "Erase : Efface une forme",
            "commande : erase <nom>",
            "nom : nom d'une forme",
            "Exemple :",
            "----------------------------------------------"
    ));
    private String eltName;

    /**
     * Instantiates a new Erase svg.
     *
     * @param instruction the instruction of erase
     */
    public EraseSVG(Instruction instruction) {
        this.eltName = instruction.getName();
    }

    public EraseSVG() {
    }

    @Override
    public String getName() {
        return eltName;
    }

    @Override
    public List<String> getHelp() {
        return this.description;
    }

    @Override
    public List<String> execute(SVGPreview svgPreview) {
        svgPreview.delElement(eltName);
        return List.of("[-] " + eltName);
    }

    /**
     * Parse command.
     *
     * @param instruction the instruction to parse
     * @param elements    the elements of the instruction
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void parseCommand(Instruction instruction, String[] elements) throws IllegalArgumentException {
        if (!(elements.length == 2)) {
            throw new IllegalArgumentException("Préciser le nom d'un élément");
        }
        instruction.setName(elements[1].trim());
    }
}