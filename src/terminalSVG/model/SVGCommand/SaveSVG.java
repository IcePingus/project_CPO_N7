package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import javax.xml.transform.TransformerException;
import java.util.Map;

/**
 * This class represents an SVG command for saving an SVG file.
 *
 * @author Team 3
 */
public class SaveSVG implements SVGCommand {

    private final String description = ("\n" + "Save : Enregistre le fichier SVG"
            + "\n" + "commande : save [nomFichier]"
            + "\n" + "nomFichier : nom du fichier SVG"
            + "\n" + "Exemple :"
            + "\n" + "----------------------------------------------"
    );

    private String eltName;

    public SaveSVG() {
    }

    /**
     * Constructs a SaveSVG object with the specified instruction map.
     *
     * @param instruction The instruction map containing the name of the SVG element to save.
     */
    public SaveSVG(Map<String, Object> instruction) {
        this.eltName = (String) instruction.get("elementName");
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
        return this.description;
    }

    /**
     * Executes the save command on the SVGPreview object.
     *
     * @param svgPreview The SVGPreview object on which to perform the save operation.
     * @param shapeName  The name of the shape (unused).
     * @return
     */
    @Override
    public String execute(SVGPreview svgPreview, String shapeName) {
        try {
            svgPreview.saveSVG(eltName);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return  ">> Save executed\n";
    }

    /**
     * Parses the command and populates the instruction map with the provided elements.
     *
     * @param instruction The instruction map to populate.
     * @param elements    The elements of the command.
     * @throws IllegalArgumentException If the command elements are invalid or incomplete.
     */
    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
        if (elements.length > 2) {
            throw new IllegalArgumentException("Préciser le nom de l'enregistrement");
        }
        if (elements.length == 2 && !elements[1].trim().isEmpty()) {
            instruction.put("elementName", elements[1].trim());
        } else {
            instruction.put("elementName", "canva.svg");
        }
    }
}