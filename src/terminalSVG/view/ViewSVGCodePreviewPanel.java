package terminalSVG.view;


import terminalSVG.model.SVGPreview;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ViewSVGCodePreviewPanel extends JScrollPane implements Observer {
    private JTextArea svgCode;
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

    @Override
    public void update(Observable o, Object arg) {
        svgCode.setText(((SVGPreview) o).getSVGCode());
    }
}