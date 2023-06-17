package graphic.view;

import graphic.controller.ColorController;
import graphic.model.color.ColorModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * The ColorSchemeInternalFrame class represents an internal frame for color scheme selection in a graphics application.
 * The selected colors are stored in the ColorController.
 *
 * @author Team 3
 */
public class ColorSchemeInternalFrame extends JInternalFrame implements ChangeListener, ActionListener, DocumentListener, Observer {

    private JButton primaryButton;
    private JButton secondaryButton;
    private JLabel lLabel;
    private JLabel rLabel;
    private JColorChooser colorChooser;
    private ColorController colorController;
    private Runnable runnableInsert;
    private JTextField hexColor;

    /**
     * Constructs a new ColorSchemeInternalFrame object with the specified ColorController.
     *
     * @param colorController the ColorController for storing selected colors
     */
    public ColorSchemeInternalFrame(ColorController colorController) {
        super("Color Scheme");
        this.setMaximizable(false);
        this.setIconifiable(false);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(549, 150);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setFrameIcon(new ImageIcon(getClass().getResource("/assets/images/colorSchemeLogo.png")));

        this.primaryButton = new JButton();
        this.primaryButton.addActionListener(this);
        this.primaryButton.setBackground(Color.BLACK);
        this.primaryButton.setBorder(BorderFactory.createBevelBorder(0, Color.GRAY, Color.GRAY));

        this.secondaryButton = new JButton();
        this.secondaryButton.addActionListener(this);
        this.secondaryButton.setBackground(Color.WHITE);

        this.lLabel = new JLabel(" L ");
        this.lLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.lLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        this.rLabel = new JLabel(" R ");
        this.rLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.rLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        JPanel lrChoice = new JPanel();
        GridLayout gl = new GridLayout(2, 2);
        gl.setHgap(10);
        lrChoice.setLayout(gl);
        lrChoice.add(this.primaryButton);
        lrChoice.add(this.secondaryButton);
        lrChoice.add(this.lLabel);
        lrChoice.add(this.rLabel);

        this.hexColor = new JTextField(6);
        this.hexColor.setText("000000");

        JPanel hexChoice = new JPanel();
        hexChoice.setLayout(new BorderLayout());
        JLabel hashtag = new JLabel("#");
        hexChoice.add(hashtag, BorderLayout.WEST);
        hexChoice.add(this.hexColor, BorderLayout.CENTER);

        JPanel colorChoice = new JPanel();
        colorChoice.setBorder(new EmptyBorder(10, 10, 10, 0));
        colorChoice.setLayout(new BorderLayout());
        this.add(colorChoice, BorderLayout.WEST);
        colorChoice.add(lrChoice, BorderLayout.NORTH);
        colorChoice.add(hexChoice, BorderLayout.SOUTH);

        this.hexColor.getDocument().addDocumentListener(this);


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
        if (this.colorController.getIsPrimaryColor()) {
            this.primaryButton.setBackground(this.colorChooser.getColor());
            this.colorController.setPrimaryColor(this.colorChooser.getColor());
        } else {
            this.secondaryButton.setBackground(this.colorChooser.getColor());
            this.colorController.setSecondaryColor(this.colorChooser.getColor());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String hexColorString = null;
        if (e.getSource() == this.primaryButton) {
            hexColorString = "" + Integer.toHexString(this.colorController.getPrimaryColor().getRGB()).substring(2);
            this.colorController.setIsPrimaryColor(true);
            this.primaryButton.setBorder(BorderFactory.createBevelBorder(0, Color.DARK_GRAY, Color.GRAY));
            this.secondaryButton.setBorder(null);
        } else {
            hexColorString = "" + Integer.toHexString(this.colorController.getSecondaryColor().getRGB()).substring(2);
            this.colorController.setIsPrimaryColor(false);
            this.primaryButton.setBorder(null);
            this.secondaryButton.setBorder(BorderFactory.createBevelBorder(0, Color.DARK_GRAY, Color.GRAY));
        }
        this.hexColor.getDocument().removeDocumentListener(this);
        this.hexColor.setText(hexColorString);
        this.hexColor.getDocument().addDocumentListener(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorModel) {
            String hexColorString = null;
            if (this.colorController.getIsPrimaryColor()) {
                this.primaryButton.setBackground(this.colorController.getPrimaryColor());
                hexColorString = "" + Integer.toHexString(this.colorController.getPrimaryColor().getRGB()).substring(2);
            } else {
                this.secondaryButton.setBackground(this.colorController.getSecondaryColor());
                hexColorString = "" + Integer.toHexString(this.colorController.getSecondaryColor().getRGB()).substring(2);
            }
            this.hexColor.getDocument().removeDocumentListener(this);
            this.hexColor.setText(hexColorString);
            this.hexColor.getDocument().addDocumentListener(this);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        insert();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private void insert() {
        if (runnableInsert == null) {
            runnableInsert = new Runnable() {
                @Override
                public void run() {
                    if (!hexColor.getText().matches("^[0-9a-fA-F]+$") || hexColor.getText().length() > 6) {
                        hexColor.setText(hexColor.getText().substring(0, hexColor.getText().length() - 1));
                    }

                    if (hexColor.getText().length() == 6) {
                        Color color = new Color(
                                Integer.valueOf(hexColor.getText().substring(0, 2), 16),
                                Integer.valueOf(hexColor.getText().substring(2, 4), 16),
                                Integer.valueOf(hexColor.getText().substring(4, 6), 16));
                        if (colorController.getIsPrimaryColor()) {
                            colorController.setPrimaryColor(color);
                        } else {
                            colorController.setSecondaryColor(color);
                        }
                    }
                }
            };
        }
        SwingUtilities.invokeLater(runnableInsert);
    }
}