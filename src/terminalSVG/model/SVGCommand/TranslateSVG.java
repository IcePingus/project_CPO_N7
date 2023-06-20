package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The TranslateSVG class represents an SVG command for translating an SVG element.
 *
 * @author Team 3
 */
public class TranslateSVG implements SVGCommand {

    private final List<String> description = new ArrayList<>(List.of(
            "Translate : Déplacement d'une forme",
            "commande : resize <nom> [-x dx] [-y dy]",
            "dx : déplacement en abscisse",
            "dy : déplacement en ordonnée",
            "Exemple : translate carre -x -100 -y 50",
            "----------------------------------------------"
    ));

    private String eltName;
    private Double dx;
    private Double dy;

    public TranslateSVG() {
    }

    /**
     * Constructs a TranslateSVG object with the specified translation values.
     *
     * @param instruction A map containing the instruction parameters.
     */
    public TranslateSVG(Instruction instruction) {
        this.eltName = instruction.getName();
        this.dx = instruction.getDx();
        this.dy =  instruction.getDy();
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
    public List<String> execute(SVGPreview svgPreview) {
        svgPreview.translateElement(this.getName(), dx, dy);
        return List.of(">> Translate executed");
    }

    /**
     * Parses the command parameters and populates the instruction map.
     *
     * @param instruction The instruction map to populate.
     * @param elements    The command elements to parse.
     * @throws IllegalArgumentException If the command parameters are invalid.
     */
    public static void parseCommand(Instruction instruction, String[] elements) throws IllegalArgumentException {
        boolean foundX = false;
        boolean foundY = false;

        if (elements.length < 2) {
            throw new IllegalArgumentException("Shape name needed");
        }
        for (int i = 2; i < elements.length; i++) {
            String element = elements[i].trim();

            if (element.equals("-x")) {
                if (foundX) {
                    throw new IllegalArgumentException("Only one -x option allowed");
                }

                if (!(i + 1 < elements.length)) {
                    throw new IllegalArgumentException("need argument after option -x");
                }

                instruction.setDx(Double.parseDouble(elements[i + 1].trim()));
                foundX = true;
                i += 1; // Skip the next element since it is used as an argument for -x
            } else if (element.equals("-y")) {
                if (foundY) {
                    throw new IllegalArgumentException("Only one -y option allowed");
                }

                if (!(i + 1 < elements.length)) {
                    throw new IllegalArgumentException("need argument after option -y");
                }

                instruction.setDy(Double.parseDouble(elements[i + 1].trim()));
                foundY = true;
                i += 1; // Skip the next element since it is used as an argument for -y
            } else {
                throw new IllegalArgumentException("Invalid argument :" + element);
            }
        }

        if (!foundX && !foundY) {
            throw new IllegalArgumentException("No -x or -y option found");
        }

        instruction.setName(elements[1].trim());
    }
}