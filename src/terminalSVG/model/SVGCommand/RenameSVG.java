package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.util.Map;

public class RenameSVG implements SVGCommand {

    private String newName;

    public RenameSVG(Map<String, Object> instruction) {
        this.newName = (String) instruction.get("elementNewName");
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
        svgPreview.renameElement(shapeName, this.newName);
    }

    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
        if (elements.length == 1) {
            throw new IllegalArgumentException("Préciser le nom de l'élément et son nouveau nom");
        } else if (elements.length == 2) {
            throw new IllegalArgumentException("Préciser le nouveau nom de l'élément");
        }
        instruction.put("elementName", elements[1].trim());
        instruction.put("elementNewName", elements[2].trim());
    }
}