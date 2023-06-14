package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;

public class TranslateXSVG implements SVGCommand {

    private Double dx;

    public TranslateXSVG(Map<String, Object> instruction) {
        this.dx = (Double) instruction.get("translationX");
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
        svgPreview.translateXElement(shapeName, dx);
    }

    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
        if (elements.length < 2) {
            throw new IllegalArgumentException("Préciser le nom d'une forme à translater");
        } else if (!(elements.length == 3)) {
            throw new IllegalArgumentException("Préciser une valeur de transition (X)");
        }
        instruction.put("elementName", elements[1].trim());
        instruction.put("translationX", Double.parseDouble(elements[2].trim()));
    }
}