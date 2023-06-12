import graphic.view.SelectionPanel;

import javax.swing.*;
import java.awt.*;

public class FramePinte extends JFrame {

    private SelectionPanel selectionPanel;

    public FramePinte() {
        super("Pinte");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 540);
        this.selectionPanel = new SelectionPanel(this);
        this.setContentPane(this.selectionPanel);
        this.setMinimumSize(new Dimension(960, 540));
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
    }
}
