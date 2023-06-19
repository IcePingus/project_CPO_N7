package terminalSVG.model.SVGCommand;

import terminalSVG.model.Instruction;
import terminalSVG.model.SVGPreview;

import java.util.Map;

/**
 * The ResizeSVG class  represents a command to resize an SVG element.
 *
 * @author Team 3
 */
public class ResizeSVG implements SVGCommand {

    private final String description = (
            "\n" + "Resize : redimensionne une forme"
            + "\n" + "commande : resize <nom> [-w largeur] [-h hauteur]"
            + "\n" + "largeur / hauteur : largeur & hauteur de la forme"
            + "\n" + "NB : Dépend de la forme (cercle & carré -> largeur = hauteur"
            + "\n" + "Exemple :"
            + "\n" + "----------------------------------------------"
    );
    private Double newWidth;

    private Double newHeight;
    /**
     * Constructs a new ResizeSVG instance with the given instruction.
     *
     * @param instruction the instruction containing the sizes for resizing
     */
    public ResizeSVG(Instruction instruction) {
        this.newWidth = instruction.getWidth();
        this.newHeight = instruction.getHeight();
    }

    public ResizeSVG(){}

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getHelp() {
        return this.description;
    }

    /**
     * Resizes the SVG element with the specified shape name.
     *
     * @param svgPreview the SVG preview instance
     * @param shapeName the name of the shape to be resized
     */
    @Override
    public String execute(SVGPreview svgPreview, String shapeName) {
        System.out.print(newHeight);
        svgPreview.resizeElement(shapeName, this.newWidth, this.newHeight);
        return  ">> Resize executed\n";
    }

    /**
     * Parses the command and sets the corresponding values in the instruction map.
     *
     * @param instruction the instruction map to populate
     * @param elements    the command elements
     * @throws IllegalArgumentException if the command is invalid or missing required arguments
     */
    public static void parseCommand(Instruction instruction, String[] elements) throws IllegalArgumentException {
        boolean foundW = false;
        boolean foundH = false;

        if (elements.length < 2) {
            throw new IllegalArgumentException("Shape name needed");
        }
        for (int i = 2; i < elements.length; i++) {
            String element = elements[i].trim();

            if (element.equals("-w")) {
                if (foundW) {
                    throw new IllegalArgumentException("Only one -w option allowed");
                }

                if (!(i + 1 < elements.length)) {
                    throw new IllegalArgumentException("need argument after option -w");
                }
                instruction.setWidth(Double.parseDouble(elements[i + 1].trim()));
                foundW = true;
                i += 1;

            } else if (element.equals("-h")) {
                if (foundH) {
                    throw new IllegalArgumentException("Only one -h option allowed");
                }

                if (!(i + 1 < elements.length)) {
                    throw new IllegalArgumentException("need argument after option -h");
                }

                instruction.setHeight(Double.parseDouble(elements[i + 1].trim()));
                foundH = true;
                i += 1;
            } else {
                throw new IllegalArgumentException("Invalid argument: " + element);
            }
        }

        if (!foundW && !foundH) {
            throw new IllegalArgumentException("No -w or -h option found");
        }

        instruction.setName(elements[1].trim());
    }
}