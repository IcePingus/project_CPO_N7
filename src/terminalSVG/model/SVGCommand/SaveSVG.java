package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents an SVG command for saving an SVG file.
 *
 * @author Team 3
 */
public class SaveSVG implements SVGCommand {
    private final List<String> description = new ArrayList<>(List.of(
            "Save : Enregistre le fichier SVG",
            "commande : save [nomFichier]",
            "nomFichier : nom du fichier SVG",
            "Exemple :",
            "----------------------------------------------"
    ));

    private String eltName;

    public SaveSVG() {
    }

    /**
     * Constructs a SaveSVG object with the specified instruction map.
     *
     * @param instruction The instruction map containing the name of the SVG element to save.
     */
    public SaveSVG(Instruction instruction) {
        this.eltName = instruction.getName();
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
     * Executes the save command on the SVGPreview object.
     *
     * @param svgPreview The SVGPreview object on which to perform the save operation.
     * @param shapeName  The name of the shape (unused).
     * @return
     */
    @Override
    public List<String> execute(SVGPreview svgPreview) {
        try {
            svgPreview.saveSVG(eltName);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return List.of(">> Save executed");
    }

    /**
     * Parses the command and populates the instruction map with the provided elements.
     *
     * @param instruction The instruction map to populate.
     * @param elements    The elements of the command.
     * @throws IllegalArgumentException If the command elements are invalid or incomplete.
     */
    public static void parseCommand(Instruction instruction, String[] elements) throws IllegalArgumentException {
        if (elements.length > 2) {
            throw new IllegalArgumentException("Préciser le nom de l'enregistrement");
        }
        if (elements.length == 2 && !elements[1].trim().isEmpty()) {
            instruction.setName(elements[1].trim());
        } else {
            instruction.setName("canva.svg");
        }
    }
}