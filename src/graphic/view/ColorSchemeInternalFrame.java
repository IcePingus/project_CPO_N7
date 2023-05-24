package graphic.view;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ColorSchemeInternalFrame extends JInternalFrame implements ChangeListener {

    private JColorChooser colorChooser;
    private Color activeColor;

    public ColorSchemeInternalFrame() {
        super("Color Scheme");
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(430, 150);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setFrameIcon(new ImageIcon(getClass().getResource("/assets/images/colorSchemeLogo.png")));

        this.activeColor = Color.black;
        this.colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(this);

        AbstractColorChooserPanel[] oldPanels = colorChooser.getChooserPanels();
        for (int i = 0; i < oldPanels.length; i++) {
            String clsName = oldPanels[i].getClass().getName();
            if (clsName.equals("javax.swing.colorchooser.ColorChooserPanel")) {
                colorChooser.removeChooserPanel(oldPanels[i]);
            }
        }
        colorChooser.setPreviewPanel(new JPanel());
        this.add(colorChooser, BorderLayout.CENTER);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.activeColor = this.colorChooser.getColor();
    }
}