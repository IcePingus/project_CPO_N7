package graphique.view;

import javax.swing.*;
import java.awt.*;

public class ToolInternalFrame extends JInternalFrame {
    public ToolInternalFrame() {
        super("Tools");
        this.setBackground(Color.red);
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(320, 240);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
