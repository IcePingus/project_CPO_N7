package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;

public class TranslateYSVG implements SVGCommand {

    private Double dy;

    public TranslateYSVG(Map<String, Object> instruction) {
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
        svgPreview.translateYElement(shapeName, dy);
    }

    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
        if (elements.length < 2) {
            throw new IllegalArgumentException("Préciser le nom d'une forme à translater");
        } else if (!(elements.length == 3)) {
            throw new IllegalArgumentException("Préciser une valeur de transition (Y)");
        }
        instruction.put("elementName", elements[1].trim());
        instruction.put("translationY", Double.parseDouble(elements[2].trim()));
    }
}