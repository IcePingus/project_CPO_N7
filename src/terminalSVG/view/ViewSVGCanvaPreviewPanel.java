package terminalSVG.view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import org.apache.batik.swing.*;
import terminalSVG.model.SVGPreview;
/**
 * The panel that displays the SVG canvas preview.
 * It observes the SVGPreview model and updates the displayed SVG document accordingly.
 */
public class ViewSVGCanvaPreviewPanel extends JPanel implements Observer {
    private final JSVGCanvas svgCanva; // Composant d'affichage SVG
    /**
     * Creates a new instance of ViewSVGCanvaPreviewPanel.
     * Initializes the SVG canvas for displaying the SVG document.
     */
    public ViewSVGCanvaPreviewPanel() {
        // Créer un canvas SVG
        this.svgCanva = new JSVGCanvas();

        this.add(svgCanva);
        this.setLayout(new GridLayout());
    }
    /**
     * Updates the view with the latest SVG document from the SVGPreview model.
     *
     * @param o   The Observable object (SVGPreview model).
     * @param arg The argument passed by the Observable (not used in this case).
     */
    @Override
    public void update(Observable o, Object arg) {
        svgCanva.setSVGDocument(((SVGPreview) o).getSvgDocument());
    }

}
