import javax.swing.*;
import java.awt.*;
import graphique.view.ColorSchemeInternalFrame;
import graphique.view.ToolInternalFrame;

public class FramePinte extends JFrame {

    private JPanel selectionPanel;

    private JButton graphicModeButton;
    private JButton terminalModeButton;
    private JDesktopPane desktopPane = new JDesktopPane();

    public FramePinte() {
        super("Pinte");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setupSelectionPanel();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    private void setupSelectionPanel() {
        this.selectionPanel = new JPanel(new GridBagLayout());
        this.setContentPane(this.selectionPanel);

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
        ToolInternalFrame tif = new ToolInternalFrame();
        ColorSchemeInternalFrame csif = new ColorSchemeInternalFrame();
        tif.setVisible(true);
        csif.setVisible(true);
        desktopPane.add(tif);
        desktopPane.add(csif);
        this.setContentPane(desktopPane);
        this.repaint();
    }

    private void onTerminalModeButtonClick() {
        this.setContentPane(new JPanel());
        this.repaint();
    }
}
