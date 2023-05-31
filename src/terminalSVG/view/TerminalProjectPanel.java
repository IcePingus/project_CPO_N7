package terminalSVG.view;

import terminalSVG.controller.ControllerTerminalPanel;
import terminalSVG.model.History;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TerminalProjectPanel extends JPanel {
    public TerminalProjectPanel() {

        // ----------- I. Définition du panel Terminal -----------
        // A. Vue Panel Preview Code SVG (preview)
        // -> Remplacer ici par les panels du module conçu pour le previewSVGCodePanel
        JPanel vPreviewSVGCodePanel = new JPanel();
        JLabel previewSVGCodeLabel = new JLabel("Terminal - Preview Code SVG");
        vPreviewSVGCodePanel.add(previewSVGCodeLabel);

        // B. Vue Panel Historique
        History h = new History(null);
        ViewHistoryPanel vhPanel = new ViewHistoryPanel();
        h.addObserver(vhPanel);

        //C. Contrôleur Panel Terminal
        ControllerTerminalPanel ctPanel = new ControllerTerminalPanel(h);

        //D. Sous-SplitPane History-Preview (Haut: Historique - Bas : PreviewCodeSVG)
        JSplitPane historyPreviewPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,vPreviewSVGCodePanel, vhPanel);
        // Dimension de base du SplitPane (50% Historique - 50% Preview Code SVG)
        historyPreviewPanel.setResizeWeight(0.5);

        //E. Global-SplitPane Terminal (Haut: Historique/Preview - Bas : Contrôleur)
        JSplitPane terminalPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, historyPreviewPanel, ctPanel);
        // Dimension de base du SplitPane (95% Historique - 5% Terminal)
        terminalPanel.setResizeWeight(0.95);

        // Gestion dynamique des dimensions - Terminal
        // Proportion du contrôleur
        ctPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int minimumWidth = (int) (terminalPanel.getWidth() * 1.0);
                int minimumHeight = (int) (terminalPanel.getHeight() * 0.05);
                ctPanel.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
            }
        });

        // Proportion de l'historique/PreviewCodeSVG
        historyPreviewPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int minimumWidth = (int) (terminalPanel.getWidth() * 1.0);
                int minimumHeight = (int) (terminalPanel.getHeight() * 0.85);
                historyPreviewPanel.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
            }
        });

        // ----------- II. Définition du panel Canva (preview) -----------
        // -> Remplacer ici par les panels du module conçu pour le canva
        JPanel canvaPanel = new JPanel();
        JLabel canvaLabel = new JLabel("Canva - Canva");
        canvaPanel.add(canvaLabel);

        // ----------- III. Définition du panel pinteCLI (contenant global) -----------
        // Panel Split Global (Gauche: Terminal - Droite: Canva)
        JSplitPane pinteCLIMainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        // Dimension de base du SplitPane (20% Terminal - 80% Canva)
        pinteCLIMainPanel.setResizeWeight(0.2);

        // Gestion dynamique des dimensions - PinteCLI
        // Proportion du canva
        canvaPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int minimumWidth = (int) (pinteCLIMainPanel.getWidth() * 0.7);
                int minimumHeight = (int) (pinteCLIMainPanel.getHeight() * 1.0);
                canvaPanel.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
            }
        });

        // Proportion du Terminal
        terminalPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int minimumWidth = (int) (pinteCLIMainPanel.getWidth() * 0.2);
                int minimumHeight = (int) (pinteCLIMainPanel.getHeight() * 1.0);
                terminalPanel.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
            }
        });

        // ----------- IV. Ajout et affichage ----------
        pinteCLIMainPanel.add(terminalPanel);
        pinteCLIMainPanel.add(canvaPanel);

        // Ajout de MainPanel dans la vue
        this.setLayout(new BorderLayout());
        this.add(pinteCLIMainPanel, BorderLayout.CENTER);
    }
}