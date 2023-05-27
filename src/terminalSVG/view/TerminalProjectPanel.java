package terminalSVG.view;

import javax.swing.*;
import java.awt.*;

public class TerminalProjectPanel extends JPanel {
    public TerminalProjectPanel() {


        //Définition du panel terminal (Preview)
        // -> Remplacer ici par les panels du module MVC conçu pour le terminal
        JPanel vtPanel = new JPanel();
        JLabel tpLabel = new JLabel("Terminal - Vue");
        vtPanel.add(tpLabel);
        JPanel ctPanel = new JPanel();
        JLabel ctpLabel = new JLabel("Terminal - Contrôleur");
        ctPanel.add(ctpLabel);


        //Définition du panel pinteCLI (contenant global)
        // Panel Split Global (Gauche: Terminal - Droite: Canva)
        JSplitPane pinteCLIMainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        pinteCLIMainPanel.setResizeWeight(0.2);

        // Panel Split Terminal (Haut: Terminal - Bas : Contrôleur)
        JSplitPane terminalPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vtPanel, ctPanel);
        terminalPanel.setResizeWeight(0.9);


        // Définition du panel Canva (preview)
        // -> Remplacer ici par les panels du module conçu pour le canva
        JPanel canvaPanel = new JPanel();
        JLabel canvaLabel = new JLabel("Canva - Canva");
        canvaPanel.add(canvaLabel);

        // Ajout des deux Panels dans MainPanel
        pinteCLIMainPanel.add(terminalPanel);
        pinteCLIMainPanel.add(canvaPanel);

        // Ajout de MainPanel dans la vue
        this.setLayout(new BorderLayout());
        this.add(pinteCLIMainPanel, BorderLayout.CENTER);
    }
}