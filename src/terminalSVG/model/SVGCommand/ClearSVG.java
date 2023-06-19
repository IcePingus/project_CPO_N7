package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import java.util.Map;

/**
 * The type Clear svg.
 *
 * @author Team 3
 */
public class ClearSVG implements SVGCommand {

    private final String description = ("\n" + "Clear : Efface toutes les formes"
            + "\n" + "commande : clear"
            + "\n" + "couleur : couleur de base"
            + "\n" + "----------------------------------------------"
    );

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
    public String getHelp() {
        return this.description;
    }

    @Override
    public String execute(SVGPreview svgPreview) {
        svgPreview.clearShapeList();
        return  ">> Clear executed\n";
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