package core;

import graphic.view.GraphicProjectPanel;
import terminalSVG.view.TerminalProjectPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The SelectionPanel class represents a panel for selecting between graphic mode and terminal mode.
 *
 * @author Team 3
 */
public class SelectionPanel extends JPanel {

    private final JButton graphicModeButton;
    private final JButton terminalModeButton;

    private GraphicProjectPanel graphicProjectPanel;
    private TerminalProjectPanel terminalProjectPanel;
    private final JFrame frame;

    /**
     * Constructs a SelectionPanel object with the specified JFrame.
     *
     * @param frame the JFrame object representing the parent frame
     */
    public SelectionPanel(JFrame frame) {
        this.setLayout(new GridBagLayout());
        this.frame = frame;
        // Utilisé pour mettre en forme les éléments plus précisément qu'avec un layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(frame.getWidth() / 120, 100, 70, (int) (frame.getWidth() / 1.5));
        this.setOpaque(false);

        // Bouton pour lancer un projet graphique
        this.graphicModeButton = new JButton("Créer une image png");
        this.graphicModeButton.setMargin(new Insets(10, 150, 10, 150));
        this.graphicModeButton.setBackground(Color.BLACK);
        this.graphicModeButton.setForeground(Color.WHITE);
        this.graphicModeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                graphicModeButton.setBackground(new Color(40, 40, 40));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                graphicModeButton.setBackground(Color.BLACK);
            }
        });

        this.graphicModeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        this.graphicModeButton.setFocusPainted(false);
        this.graphicModeButton.addActionListener(e -> this.onGraphicModeButtonClick());

        // Bouton pour lancer un projet en ligne de commande
        this.terminalModeButton = new JButton("Créer une image svg");
        this.terminalModeButton.setMargin(new Insets(10, 150, 10, 150));
        this.terminalModeButton.setBackground(Color.BLACK);
        this.terminalModeButton.setForeground(Color.WHITE);
        this.terminalModeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                terminalModeButton.setBackground(new Color(40, 40, 40));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                terminalModeButton.setBackground(Color.BLACK);
            }
        });
        this.terminalModeButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        this.terminalModeButton.addActionListener(e -> this.onTerminalModeButtonClick());

        this.add(this.graphicModeButton, gbc);
        this.add(this.terminalModeButton, gbc);
    }

    /**
     * Event handler for the graphic mode button click.
     * Switches the content pane of the frame to the graphic project panel.
     */
    private void onGraphicModeButtonClick() {
        if (this.graphicProjectPanel == null) {
            this.graphicProjectPanel = new GraphicProjectPanel(this.frame);
        }
        this.frame.setContentPane(this.graphicProjectPanel);
        this.frame.validate();
        this.frame.repaint();
    }

    /**
     * Event handler for the terminal mode button click.
     * Switches the content pane of the frame to the terminal project panel.
     */
    private void onTerminalModeButtonClick() {
        if (this.terminalProjectPanel == null) {
            this.terminalProjectPanel = new TerminalProjectPanel(this.frame);
        }
        this.frame.setContentPane(this.terminalProjectPanel);
        this.frame.validate();
        this.frame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Définir l'image de fond de l'application
        final Image backgroundImage;
        try {
            backgroundImage = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/images/background.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}