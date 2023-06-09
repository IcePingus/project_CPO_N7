package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;

public class EraseSVG implements SVGCommand {

    private String eltName;

    public EraseSVG(Map<String, Object> instruction) {
        this.eltName = (String) instruction.get("elementName");
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
    public void execute(SVGPreview svgPreview, String elementName) {
        svgPreview.delElement(eltName);
    }

    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
        if (!(elements.length == 2)) {
            throw new IllegalArgumentException("Préciser le nom d'un élément");
        }
        instruction.put("elementName", elements[1].trim());
    }
}