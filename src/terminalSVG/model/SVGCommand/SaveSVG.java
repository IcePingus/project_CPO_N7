package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import javax.xml.transform.TransformerException;
import java.util.Map;

public class SaveSVG implements SVGCommand {

    private String eltName;

    public SaveSVG(Map<String, Object> instruction) {
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
    public void execute(SVGPreview svgPreview, String shapeName) {
        try {
            if (!eltName.endsWith(".svg")) {
                eltName += ".svg";
            }
            svgPreview.saveSVG(eltName);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseCommand(Map<String, Object> instruction, String[] elements) throws IllegalArgumentException {
        if (!(elements.length == 2)) {
            throw new IllegalArgumentException("Préciser le nom de l'enregistrement");
        }
        instruction.put("elementName", elements[1].trim());
    }
}