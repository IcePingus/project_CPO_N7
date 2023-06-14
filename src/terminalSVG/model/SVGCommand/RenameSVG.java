package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;
/**
 * This class represents an SVG command for renaming an element.
 */
public class RenameSVG implements SVGCommand {

    private String newName;
    /**
     * Constructs a RenameSVG object with the specified instruction map.
     *
     * @param instruction The instruction map containing the new name for the element.
     */
    public RenameSVG(Map<String, Object> instruction) {
        this.newName = (String) instruction.get("elementNewName");
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
    public String getHelp() {
        return null;
    }
    /**
     * Executes the rename command on the SVGPreview object.
     *
     * @param svgPreview The SVGPreview object on which to perform the rename operation.
     * @param shapeName  The name of the shape to rename.
     */
    @Override
    public void execute(SVGPreview svgPreview, String shapeName) {
        svgPreview.renameElement(shapeName, this.newName);
    }
    /**
     * Parses the command and populates the instruction map with the provided elements.
     *
     * @param instruction The instruction map to populate.
     * @param elements    The elements of the command.
     * @throws IllegalArgumentException If the command elements are invalid or incomplete.
     */
    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
        if (elements.length == 1) {
            throw new IllegalArgumentException("Préciser le nom de l'élément et son nouveau nom");
        } else if (elements.length == 2) {
            throw new IllegalArgumentException("Préciser le nouveau nom de l'élément");
        }
        instruction.put("elementName", elements[1].trim());
        instruction.put("elementNewName", elements[2].trim());
    }
}