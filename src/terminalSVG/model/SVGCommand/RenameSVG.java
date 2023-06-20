package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents an SVG command for renaming an element.
 *
 * @author Team 3
 */
public class RenameSVG implements SVGCommand {

    private final List<String> description = new ArrayList<>(List.of(
            "Rename : renomme une forme",
            "commande : rename <ancienNom> <NouveauNom>",
            "ancienNom : ancien nom de la forme",
            "ancienNom : nouveau nom de la forme",
            "Exemple : rename circle1 rond",
            "----------------------------------------------"
    ));

    private String newName;

    private String oldName;

    /**
     * Constructs a RenameSVG object with the specified instruction map.
     *
     * @param instruction The instruction map containing the new name for the element.
     */

    public RenameSVG(Instruction instruction) {
        this.newName = instruction.getName();
        this.oldName = instruction.getOldName();
    }

    public RenameSVG() {
    }

    /**
     * Gets the name of the command.
     *
     * @return The name of the command.
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Gets the help information for the command.
     *
     * @return The help information for the command.
     */
    @Override
    public List<String> getHelp() {
        return this.description;
    }

    /**
     * Executes the rename command on the SVGPreview object.
     *
     * @param svgPreview The SVGPreview object on which to perform the rename operation.
     * @param shapeName  The name of the shape to rename.
     * @return
     */

    public List<String> execute(SVGPreview svgPreview) {
        svgPreview.renameElement(this.oldName, this.newName);
        return List.of(">> Rename executed");
    }

    /**
     * Parses the command and populates the instruction map with the provided elements.
     *
     * @param instruction The instruction map to populate.
     * @param elements    The elements of the command.
     * @throws IllegalArgumentException If the command elements are invalid or incomplete.
     */
    public static void parseCommand(Instruction instruction, String[] elements) throws IllegalArgumentException {
        if (elements.length == 1) {
            throw new IllegalArgumentException("Préciser le nom de l'élément et son nouveau nom");
        } else if (elements.length == 2) {
            throw new IllegalArgumentException("Préciser le nouveau nom de l'élément");
        }
        instruction.setOldName(elements[1].trim());
        instruction.setName(elements[2].trim());
    }
}