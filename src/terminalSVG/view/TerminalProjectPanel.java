package terminalSVG.view;

import terminalSVG.controller.ControllerTerminalPanel;
import terminalSVG.model.History;
import terminalSVG.model.SVGPreview;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TerminalProjectPanel extends JPanel {
    public TerminalProjectPanel() {

        // ----------- I. Partie Terminal -----------
        // A. Instance Panels Historique & PreviewCodeSVG (preview)
        // -> Remplacer ici par le panel du module conçu pour le previewCodeSVG
        ViewHistoryPanel vhPanel = new ViewHistoryPanel();
        JPanel vSVGCodePreviewPanel = new JPanel();
        JLabel previewSVGCodeLabel = new JLabel("Terminal - Preview Code SVG");
        vSVGCodePreviewPanel.add(previewSVGCodeLabel);

        // B. Instance modèle Historique & Ajout observer
        History h = new History(null);
        h.addObserver(vhPanel);  //Observer HistoryPanel -> Historique

        // ----------- II. Partie Canva -----------
        // A. Instance Panel PreviewCanvaSVG
        ViewSVGCanvaPreviewPanel vSVGCanvaPreviewPanel = new ViewSVGCanvaPreviewPanel();

        // B. Instance modèle SVGPreview & Ajout observers
        SVGPreview svgp = new SVGPreview();

        svgp.addObserver(vSVGCanvaPreviewPanel); //Observer SVGCanvaPreview -> SVGPreview

        // ----------- III. Partie Controller -----------
        // A. Instance Panels Controller -> Historique & SVGPreview
        ControllerTerminalPanel cTerminalPanel = new ControllerTerminalPanel(h, svgp);

        // ----------- IV. Gestion des panels -----------
        // A. Sous-SplitPane History-Preview (Haut: Historique - Bas : PreviewCodeSVG)
        JSplitPane historyCodePreviewPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vSVGCodePreviewPanel, vhPanel);
        // Dimension de base du SplitPane (50% Historique - 50% Preview Code SVG)
        historyCodePreviewPanel.setResizeWeight(0.5);

        // B. SplitPane global Terminal (Haut: Historique/Preview - Bas : Contrôleur)
        JSplitPane terminalPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, historyCodePreviewPanel, cTerminalPanel);
        // Dimension de base du SplitPane (95% Historique - 5% Terminal)
        terminalPanel.setResizeWeight(0.95);

        // C. Gestion dynamique des dimensions - Terminal
        // 1. Proportion du contrôleur
        cTerminalPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int minimumWidth = (int) (terminalPanel.getWidth() * 1.0);
                int minimumHeight = (int) (terminalPanel.getHeight() * 0.05);
                cTerminalPanel.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
            }
        });

        // 2. Proportion de l'historique/PreviewCodeSVG
        historyCodePreviewPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int minimumWidth = (int) (terminalPanel.getWidth() * 1.0);
                int minimumHeight = (int) (terminalPanel.getHeight() * 0.85);
                historyCodePreviewPanel.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
            }
        });

        // ----------- V. Définition du panel pinteCLI (contenant global) -----------
        // A. SplitPane Global (Gauche: Terminal - Droite: Canva)
        JSplitPane pinteCLIMainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, terminalPanel, vSVGCanvaPreviewPanel);
        // Dimension de base du SplitPane (20% Terminal - 80% Canva)
        pinteCLIMainPanel.setResizeWeight(0.2);

        // B. Gestion dynamique des dimensions - PinteCLI
        // 1. Proportion du canva
        vSVGCanvaPreviewPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int minimumWidth = (int) (pinteCLIMainPanel.getWidth() * 0.7);
                int minimumHeight = (int) (pinteCLIMainPanel.getHeight() * 1.0);
                vSVGCanvaPreviewPanel.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
            }
        });

        // 2. Proportion du Terminal
        terminalPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int minimumWidth = (int) (pinteCLIMainPanel.getWidth() * 0.2);
                int minimumHeight = (int) (pinteCLIMainPanel.getHeight() * 1.0);
                terminalPanel.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
            }
        });

        // ----------- VI. Ajout MainPanel dans la vue -----------
        this.setLayout(new BorderLayout());
        this.add(pinteCLIMainPanel, BorderLayout.CENTER);
    }
}