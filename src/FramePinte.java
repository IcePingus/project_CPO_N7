import graphic.view.GraphicProjectPanel;
import terminalSVG.view.TerminalProjectPanel;

import javax.swing.*;
import java.awt.*;

public class FramePinte extends JFrame {

    private JPanel selectionPanel;

    private JButton graphicModeButton;
    private JButton terminalModeButton;

    public FramePinte() {
        super("Pinte");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setupSelectionPanel();
        this.setSize(960, 540);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private void setupSelectionPanel() {
        this.selectionPanel = new JPanel(new GridBagLayout());
        this.setContentPane(this.selectionPanel);
        this.setMinimumSize(new Dimension(960, 540));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(50, 300, 50, 300);

        this.graphicModeButton= new JButton("Je suis un artiste");
        this.graphicModeButton.setMargin(new Insets(10, 150, 10, 150));
        this.graphicModeButton.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 20));
        this.graphicModeButton.addActionListener(e -> this.onGraphicModeButtonClick() );

        this.terminalModeButton= new JButton("Je mange des cartes graphiques");
        this.terminalModeButton.setMargin(new Insets(10, 150, 10, 150));
        this.terminalModeButton.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 20));
        this.terminalModeButton.addActionListener(e -> this.onTerminalModeButtonClick() );

        this.selectionPanel.add(this.graphicModeButton, gbc);
        this.selectionPanel.add(this.terminalModeButton, gbc);
    }

    private void onGraphicModeButtonClick() {
        this.setContentPane(new GraphicProjectPanel());
        this.validate();
        this.repaint();
    }

    private void onTerminalModeButtonClick() {
        this.setContentPane(new TerminalProjectPanel());
        this.validate();
        this.repaint();
    }
}
