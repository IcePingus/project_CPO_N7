package graphic.view;

import graphic.controller.ColorController;
import graphic.model.tools.ColorModel;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ColorSchemeInternalFrame extends JInternalFrame implements ChangeListener, ActionListener, Observer {

    private JButton primaryButton;
    private JButton secondaryButton;
    private JColorChooser colorChooser;
    private ColorController colorController;

    private boolean isPrimaryColor = true;

    public ColorSchemeInternalFrame(ColorController colorController) {
        super("Color Scheme");
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(549, 150);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setFrameIcon(new ImageIcon(getClass().getResource("/assets/images/colorSchemeLogo.png")));


        this.primaryButton = new JButton();
        this.secondaryButton = new JButton();
        this.colorController = colorController;
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(this.primaryButton, BorderLayout.NORTH);
        jp.add(this.secondaryButton, BorderLayout.SOUTH);
        this.primaryButton.addActionListener(this);
        this.secondaryButton.addActionListener(this);
        jp.setSize(50, 50);
        jp.setVisible(true);

        this.add(jp);

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
        if (this.isPrimaryColor) {
            this.primaryButton.setBackground(this.colorChooser.getColor());
            this.colorController.setPrimaryColor(this.colorChooser.getColor());
        } else {
            this.secondaryButton.setBackground(this.colorChooser.getColor());
            this.colorController.setSecondaryColor(this.colorChooser.getColor());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.primaryButton) {
            this.isPrimaryColor = true;
        } else {
            this.isPrimaryColor = false;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorModel) {
            this.primaryButton.setBackground(this.colorController.getPrimaryColor());
            this.secondaryButton.setBackground(this.colorController.getSecondaryColor());
        }
    }
}