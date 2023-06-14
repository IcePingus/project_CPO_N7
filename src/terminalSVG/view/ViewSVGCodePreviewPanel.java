package terminalSVG.view;


import terminalSVG.model.SVGPreview;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
/**
 * The panel that displays the preview of SVG code.
 * It observes the SVGPreview model and updates the displayed SVG code accordingly.
 */
public class ViewSVGCodePreviewPanel extends JScrollPane implements Observer {
    private JTextArea svgCode;
    /**
     * Creates a new instance of ViewSVGCodePreviewPanel.
     * Initializes the JTextArea for displaying the SVG code.
     */
    public ViewSVGCodePreviewPanel() {
        super();
        this.svgCode = new JTextArea();
        this.svgCode.setEditable(false); // Make the JTextArea non-modifiable

        svgCode.setLineWrap(true); // Empêche le retour à la ligne automatique
        svgCode.setWrapStyleWord(true); // Coupe les mots longs pour s'adapter à la largeur

        this.add(svgCode);

        this.setViewportView(this.svgCode);
        this.setLayout(new ScrollPaneLayout());
    }
    /**
     * Updates the view with the latest SVG code from the SVGPreview model.
     *
     * @param o   The Observable object (SVGPreview model).
     * @param arg The argument passed by the Observable (not used in this case).
     */
    @Override
    public void update(Observable o, Object arg) {
        svgCode.setText(((SVGPreview) o).getSVGCode());
    }
}