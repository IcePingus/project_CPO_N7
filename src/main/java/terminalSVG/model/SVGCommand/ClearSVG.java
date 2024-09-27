package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Clear svg.
 *
 * @author Team 3
 */
public class ClearSVG implements SVGCommand {

    private final List<String> description = new ArrayList<>(List.of(
            "Clear : Efface toutes les formes",
            "commande : clear",
            "couleur : couleur de base",
            "----------------------------------------------"
    ));

    /**
     * Instantiates a new Clear svg.
     */
    public ClearSVG() {
    }

    public ClearSVG(Instruction instruction) {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<String> getHelp() {
        return this.description;
    }

    @Override
    public List<String> execute(SVGPreview svgPreview) {
        svgPreview.clearShapeList();
        return List.of(">> Clear executed");
    }

    /**
     * Parse command.
     *
     * @param instruction the instruction to parse
     * @param elements    the elements of the instruction
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void parseCommand(Instruction instruction, String[] elements) throws IllegalArgumentException {
        if (!(elements.length == 1)) {
            throw new IllegalArgumentException("Ne pas préciser d'arguments");
        }
    }
}