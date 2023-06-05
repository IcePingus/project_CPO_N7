package graphic.view;

import graphic.controller.ColorController;
import graphic.model.color.ColorModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ColorSchemeInternalFrame extends JInternalFrame implements ChangeListener, ActionListener, Observer {

    private boolean isPrimaryColor;
    private JButton primaryButton;
    private JButton secondaryButton;
    private JLabel lLabel;
    private JLabel rLabel;
    private JColorChooser colorChooser;
    private ColorController colorController;

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
        this.primaryButton.addActionListener(this);
        this.primaryButton.setBackground(Color.BLACK);
        this.primaryButton.setBorder(BorderFactory.createBevelBorder(0 ,Color.GRAY, Color.GRAY));

        this.secondaryButton = new JButton();
        this.secondaryButton.addActionListener(this);
        this.secondaryButton.setBackground(Color.WHITE);

        this.isPrimaryColor = true;
        this.lLabel = new JLabel(" L ");
        this.lLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.lLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 24));
        this.rLabel = new JLabel(" R ");
        this.rLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.rLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 24));

        JPanel jp = new JPanel();
        GridLayout gl = new GridLayout(2, 2);
        gl.setHgap(10);
        jp.setLayout(gl);
        jp.setBorder(new EmptyBorder(10, 10, 10, 0));
        jp.add(this.primaryButton);
        jp.add(this.secondaryButton);
        jp.add(this.lLabel);
        jp.add(this.rLabel);

        this.add(jp, BorderLayout.WEST);

        this.colorController = colorController;
        this.colorChooser = new JColorChooser();
        this.colorChooser.getSelectionModel().addChangeListener(this);

        AbstractColorChooserPanel[] oldPanels = this.colorChooser.getChooserPanels();
        for (int i = 0; i < oldPanels.length; i++) {
            String clsName = oldPanels[i].getClass().getName();
            if (clsName.equals("javax.swing.colorchooser.ColorChooserPanel")) {
                this.colorChooser.removeChooserPanel(oldPanels[i]);
            }
        }
        this.colorChooser.setPreviewPanel(new JPanel());
        this.add(this.colorChooser, BorderLayout.CENTER);
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
            this.primaryButton.setBorder(BorderFactory.createBevelBorder(0 ,Color.DARK_GRAY, Color.GRAY));
            this.secondaryButton.setBorder(null);
        } else {
            this.isPrimaryColor = false;
            this.primaryButton.setBorder(null);
            this.secondaryButton.setBorder(BorderFactory.createBevelBorder(0 ,Color.DARK_GRAY, Color.GRAY));
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