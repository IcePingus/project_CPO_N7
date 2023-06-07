package graphic.view;

import terminalSVG.view.TerminalProjectPanel;

import javax.swing.*;
import java.awt.*;

public class SelectionPanel extends JPanel {

    private JButton graphicModeButton;
    private JButton terminalModeButton;

    private GraphicProjectPanel graphicProjectPanel;
    private TerminalProjectPanel terminalProjectPanel;
    private JFrame frame;

    public SelectionPanel(JFrame frame) {
        this.setLayout(new GridBagLayout());
        this.frame = frame;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(50, 300, 50, 300);

        this.graphicModeButton = new JButton("Créer une image png");
        this.graphicModeButton.setMargin(new Insets(10, 150, 10, 150));
        this.graphicModeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        this.graphicModeButton.addActionListener(e -> this.onGraphicModeButtonClick());

        this.terminalModeButton = new JButton("Créer une image svg");
        this.terminalModeButton.setMargin(new Insets(10, 150, 10, 150));
        this.terminalModeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        this.terminalModeButton.addActionListener(e -> this.onTerminalModeButtonClick());

        this.add(this.graphicModeButton, gbc);
        this.add(this.terminalModeButton, gbc);
    }

    private void onGraphicModeButtonClick() {
        if (this.graphicProjectPanel == null) {
            this.graphicProjectPanel = new GraphicProjectPanel(this.frame);
        }
        this.frame.setContentPane(this.graphicProjectPanel);
        this.frame.validate();
        this.frame.repaint();
    }

    private void onTerminalModeButtonClick() {
        if (this.terminalProjectPanel == null) {
            this.terminalProjectPanel = new TerminalProjectPanel(this.frame);
        }
        this.frame.setContentPane(this.terminalProjectPanel);
        this.frame.validate();
        this.frame.repaint();
    }
}
