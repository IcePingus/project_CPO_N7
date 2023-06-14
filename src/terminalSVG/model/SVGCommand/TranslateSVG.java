package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;
import terminalSVG.model.parser.Parser;

import java.security.InvalidParameterException;
import java.util.Map;

public class TranslateSVG implements SVGCommand {

    private Double dx;
    private Double dy;

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
        return null;
    }

    @Override
    public void execute(SVGPreview svgPreview, String shapeName) {
        svgPreview.translateElement(shapeName, dx ,dy);
    }

    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
        boolean foundX = false;
        boolean foundY = false;

        if (elements.length < 3) {
            throw new IllegalArgumentException("not enough arguments");
        } else if (elements.length > 6) {
            throw new IllegalArgumentException("You have too many arguments");
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
            }
            else {
                throw new IllegalArgumentException("Invalid argument");
            }
        }

        if (!foundX && !foundY) {
            throw new IllegalArgumentException("No -x or -y option found");
        }

        instruction.put("elementName", elements[1].trim());
    }
}