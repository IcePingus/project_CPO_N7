package core;

import javax.swing.*;
import java.awt.*;

public class FramePinte extends JFrame {

    public FramePinte() {
        super("Pinte");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 540);

        this.setMinimumSize(new Dimension(960, 540));
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setIconImage((new ImageIcon(getClass().getResource("/assets/images/frameLogo.png"))).getImage());
        // Panel de selection pour choisir entre projet graphique ou en ligne de commande
        SelectionPanel selectionPanel = new SelectionPanel(this);
        this.add(selectionPanel, BorderLayout.CENTER);
    }
}
