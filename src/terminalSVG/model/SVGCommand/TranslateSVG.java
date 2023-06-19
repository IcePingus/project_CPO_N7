package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;

/**
 * The TranslateSVG class represents an SVG command for translating an SVG element.
 *
 * @author Team 3
 */
public class TranslateSVG implements SVGCommand {

    private final String description = ("\n" + "Translate : Déplacement d'une forme"
            + "\n" + "commande : resize <nom> [-x dx] [-y dy]"
            + "\n" + "dx : déplacement en abscisse"
            + "\n" + "dy : déplacement en ordonnée"
            + "\n" + "Exemple :"
            + "\n" + "----------------------------------------------"
    );

    private Double dx;
    private Double dy;

    public TranslateSVG(){}

    /**
     * Constructs a TranslateSVG object with the specified translation values.
     *
     * @param instruction A map containing the instruction parameters.
     */
    public TranslateSVG(Map<String, Object> instruction) {
        this.dx = (Double) instruction.get("translationX");
        this.dy = (Double) instruction.get("translationY");
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
    public String execute(SVGPreview svgPreview, String shapeName) {
        svgPreview.translateElement(shapeName, dx, dy);
        return  ">> Translate executed\n";
    }

    /**
     * Parses the command parameters and populates the instruction map.
     *
     * @param instruction The instruction map to populate.
     * @param elements    The command elements to parse.
     * @throws IllegalArgumentException If the command parameters are invalid.
     */
    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
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

                instruction.put("translationX", Double.parseDouble(elements[i + 1].trim()));
                foundX = true;
                i += 1; // Skip the next element since it is used as an argument for -x
            } else if (element.equals("-y")) {
                if (foundY) {
                    throw new IllegalArgumentException("Only one -y option allowed");
                }

                if (!(i + 1 < elements.length)) {
                    throw new IllegalArgumentException("need argument after option -y");
                }

                instruction.put("translationY", Double.parseDouble(elements[i + 1].trim()));
                foundY = true;
                i += 1; // Skip the next element since it is used as an argument for -y
            } else {
                throw new IllegalArgumentException("Invalid argument :" + element);
            }
        }

        if (!foundX && !foundY) {
            throw new IllegalArgumentException("No -x or -y option found");
        }

        instruction.put("elementName", elements[1].trim());
    }
}