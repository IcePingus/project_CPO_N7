import graphique.view.ToolInternalFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FramePinte fp = new FramePinte();
        fp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fp.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fp.setVisible(true);
    }
}