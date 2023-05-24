package graphic.view;

import javax.swing.*;
import java.awt.*;

public class ColorSchemeInternalFrame extends JInternalFrame {

    public ColorSchemeInternalFrame() {
        super("Color Scheme");
        this.setBackground(Color.red);
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(500, 240);
        this.setLocation(getToolkit().getScreenSize().width-500, 0);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

}
