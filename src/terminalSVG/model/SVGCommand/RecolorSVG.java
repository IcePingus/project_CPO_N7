package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;
import terminalSVG.model.parser.Parser;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The command Recolor svg.
 *
 * @author Team 3
 */
public class RecolorSVG implements SVGCommand {

    private final List<String> description = new ArrayList<>(List.of(
            "Recolor : Redéfinit la couleur d'une forme",
            "commande : recolor <nom> [-s contour] [-f remplissage]",
            "nom : nom de la forme",
            "contour : couleur de contour du cercle",
            "remplissage : couleur de remplissage du cercle",
            "Exemple :",
            "----------------------------------------------"
    ));

    private String eltName;
    private Color sColor;
    private Color fColor;

    public RecolorSVG() {
    }

    /**
     * Instantiates a new Recolor svg.
     *
     * @param instruction the instruction of recolor
     */
    public RecolorSVG(Instruction instruction) {
        this.eltName = instruction.getName();
        this.sColor = instruction.getStrokeColor();
        this.fColor = instruction.getFillColor();
    }

    @Override
    public String getName() {
        return this.eltName;
    }

    @Override
    public List<String> getHelp() {
        return this.description;
    }

    @Override
    public java.util.List<String> execute(SVGPreview svgPreview) {
        svgPreview.setNewColorShape(sColor, fColor, this.getName());
        return List.of(">> Recolor executed");
    }

    /**
     * Parse command.
     *
     * @param instruction the instruction to parse
     * @param elements    the elements of the instruction
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void parseCommand(Instruction instruction, String[] elements) throws IllegalArgumentException {

        if (elements.length < 3) {
            throw new IllegalArgumentException("not enough arguments");
        } else if (elements.length > 6) {
            throw new IllegalArgumentException("You have too many argument");
        }
        for (int i = 0; i < elements.length; i++) {
            String element = elements[i].trim();

            if (element.equals("-s")) {
                if (!(i + 1 < elements.length)) {
                    throw new IllegalArgumentException("need argument after option -s");
                }

                instruction.setStrokeColor(Parser.convertStringToColor(elements[i + 1].trim()));

            } else if (element.equals("-f")) {
                if (!(i + 1 < elements.length)) {
                    throw new IllegalArgumentException("need argument after option -f");
                }

                instruction.setFillColor(Parser.convertStringToColor(elements[i + 1].trim()));
            }
        }
        instruction.setName(elements[1].trim());
    }
}