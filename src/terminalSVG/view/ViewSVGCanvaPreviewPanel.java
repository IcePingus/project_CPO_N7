package terminalSVG.view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import org.apache.batik.swing.*;
import terminalSVG.model.SVGPreview;

public class ViewSVGCanvaPreviewPanel extends JPanel implements Observer {
    private final JSVGCanvas svgCanva; // Composant d'affichage SVG

    public ViewSVGCanvaPreviewPanel() {
        // Créer un canvas SVG
        this.svgCanva = new JSVGCanvas();

        this.add(svgCanva);
        this.setLayout(new GridLayout());
    }

    @Override
    public void update(Observable o, Object arg) {
        svgCanva.setSVGDocument(((SVGPreview) o).getSvgDocument());
    }

}
