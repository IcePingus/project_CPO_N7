package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;
import terminalSVG.model.parser.Parser;

import java.awt.*;
import java.util.Map;

/**
 * The type Color svg.
 *
 * @author Team 3
 */
public class ColorSVG implements SVGCommand {

    private final String description = ("\n" + "Color : Définit la couleur de base"
            + "\n" + "commande : color <couleur>"
            + "\n" + "couleur : nouvelle couleur de base"
            + "\n" + "Exemple :"
            + "\n" + "----------------------------------------------"
    );

    private Color color;

    /**
     * Instantiates a new Color svg.
     *
     * @param instruction the instruction strokeColor
     */
    public ColorSVG(Instruction instruction) {
        this.color = instruction.getStrokeColor();
    }

    public ColorSVG() {
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
        svgPreview.setDefaultColor(color);
        return  ">> Color executed\n";
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
            throw new IllegalArgumentException("Préciser une nouvelle couleur");
        }
        try {
            instruction.setStrokeColor(Parser.convertStringToColor(elements[1].trim()));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}