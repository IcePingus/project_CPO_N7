package graphic.view;

import graphic.controller.CanvaController;
import graphic.model.CropTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ResizeDialog class represents a dialog window for resizing the canvas.
 *
 * @author Team 3
 */
public class ResizeDialog extends JDialog implements ActionListener {

    private final JPanel inputsPanel;
    private final JPanel buttonsPanel;
    private final JPanel placementPanel;

    private final JLabel errorMessage;

    private final JTextField widthInput;
    private final JTextField heightInput;

    private final JRadioButton topLeft;
    private final JRadioButton top;
    private final JRadioButton topRight;
    private final JRadioButton middleLeft;
    private final JRadioButton middle;
    private final JRadioButton middleRight;
    private final JRadioButton bottomLeft;
    private final JRadioButton bottom;
    private final JRadioButton bottomRight;

    private final JButton confirmButton;

    private final CanvaController canvaController;

    private boolean isCropping;

    private CropTypes horizontalAlign = CropTypes.MIDDLE;
    private CropTypes verticalAlign = CropTypes.MIDDLE;

    /**
     * Constructs a ResizeDialog with the specified CanvaController.
     *
     * @param canvaController the CanvaController object
     */
    public ResizeDialog(CanvaController canvaController) {
        super();
        this.setResizable(false);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setName("Resize");
        this.setModal(true);

        this.setLayout(new BorderLayout());

        this.inputsPanel = new JPanel(new GridLayout(2, 2));
        this.placementPanel = new JPanel(new GridLayout(3, 3));
        this.buttonsPanel = new JPanel(new FlowLayout());

        JLabel widthLabel = new JLabel("Width : ");
        JLabel heightLabel = new JLabel("Height : ");
        this.errorMessage = new JLabel("Invalid width or height !");

        this.errorMessage.setForeground(Color.RED);
        this.errorMessage.setVisible(false);

        // Initialisation des boutons de placement lorsque l'on ajuste la taille de la toile
        this.topLeft = new JRadioButton();
        this.topLeft.addActionListener(this);
        this.top = new JRadioButton();
        this.top.addActionListener(this);
        this.topRight = new JRadioButton();
        this.topRight.addActionListener(this);
        this.middleLeft = new JRadioButton();
        this.middleLeft.addActionListener(this);
        this.middle = new JRadioButton();
        this.middle.addActionListener(this);
        this.middle.setSelected(true);
        this.middleRight = new JRadioButton();
        this.middleRight.addActionListener(this);
        this.bottomLeft = new JRadioButton();
        this.bottomLeft.addActionListener(this);
        this.bottom = new JRadioButton();
        this.bottom.addActionListener(this);
        this.bottomRight = new JRadioButton();
        this.bottomRight.addActionListener(this);

        ButtonGroup placementButtons = new ButtonGroup();

        placementButtons.add(this.topLeft);
        placementButtons.add(this.top);
        placementButtons.add(this.topRight);
        placementButtons.add(this.middleLeft);
        placementButtons.add(this.middle);
        placementButtons.add(this.middleRight);
        placementButtons.add(this.bottomLeft);
        placementButtons.add(this.bottom);
        placementButtons.add(this.bottomRight);

        this.confirmButton = new JButton("Confirm");
        this.confirmButton.addActionListener(this);

        this.widthInput = new JTextField();
        this.heightInput = new JTextField();

        this.inputsPanel.add(widthLabel);
        this.inputsPanel.add(this.widthInput);

        this.inputsPanel.add(heightLabel);
        this.inputsPanel.add(this.heightInput);

        this.placementPanel.add(this.topLeft);
        this.placementPanel.add(this.top);
        this.placementPanel.add(this.topRight);
        this.placementPanel.add(this.middleLeft);
        this.placementPanel.add(this.middle);
        this.placementPanel.add(this.middleRight);
        this.placementPanel.add(this.bottomLeft);
        this.placementPanel.add(this.bottom);
        this.placementPanel.add(this.bottomRight);

        this.buttonsPanel.add(this.confirmButton, BorderLayout.CENTER);

        this.canvaController = canvaController;
    }

    /**
     * Performs an action based on the event triggered.
     *
     * @param e the ActionEvent object representing the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Confirmer le redimensionnement/ajustement
        if (e.getSource() == this.confirmButton) {
            try {
                int width = Integer.parseInt(this.widthInput.getText());
                int height = Integer.parseInt(this.heightInput.getText());
                // Afficher une erreur lorsque la(les) valeur(s) rentrées ne sont pas valides
                if (width > 5000 || height > 5000 || width < 16 || height < 16) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter number bigger than 16 and smaller than 5000", "Image size",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    if (this.isCropping) {
                        this.canvaController.cropCanva(width, height, this.horizontalAlign, this.verticalAlign);
                    } else {
                        this.canvaController.resizeCanva(width, height);
                    }
                    this.errorMessage.setVisible(false);
                    this.setVisible(false);
                }
            } catch (Exception exception) {
                this.errorMessage.setVisible(true);
                this.validate();
                this.repaint();
            }
        // Définition de la zone à ajuster
        } else if (e.getSource() == this.topLeft) {
            this.horizontalAlign = CropTypes.LEFT;
            this.verticalAlign = CropTypes.TOP;
        } else if (e.getSource() == this.top) {
            this.horizontalAlign = CropTypes.MIDDLE;
            this.verticalAlign = CropTypes.TOP;
        } else if (e.getSource() == this.topRight) {
            this.horizontalAlign = CropTypes.RIGHT;
            this.verticalAlign = CropTypes.TOP;
        } else if (e.getSource() == this.middleLeft) {
            this.horizontalAlign = CropTypes.LEFT;
            this.verticalAlign = CropTypes.MIDDLE;
        } else if (e.getSource() == this.middle) {
            this.horizontalAlign = CropTypes.MIDDLE;
            this.verticalAlign = CropTypes.MIDDLE;
        } else if (e.getSource() == this.middleRight) {
            this.horizontalAlign = CropTypes.RIGHT;
            this.verticalAlign = CropTypes.MIDDLE;
        } else if (e.getSource() == this.bottomLeft) {
            this.horizontalAlign = CropTypes.LEFT;
            this.verticalAlign = CropTypes.BOTTOM;
        } else if (e.getSource() == this.bottom) {
            this.horizontalAlign = CropTypes.MIDDLE;
            this.verticalAlign = CropTypes.BOTTOM;
        } else if (e.getSource() == this.bottomRight) {
            this.horizontalAlign = CropTypes.RIGHT;
            this.verticalAlign = CropTypes.BOTTOM;
        }
    }

    /**
     * Sets the initial input values based on the CanvaController's canvas width and height.
     */
    public void setInputs(boolean isCropping) {
        this.isCropping = isCropping;
        JPanel contentPane = new JPanel(new GridLayout(isCropping ? 4 : 3, 1));
        contentPane.add(this.inputsPanel);
        contentPane.add(this.errorMessage);
        if (isCropping) {
            contentPane.add(this.placementPanel);
        }
        contentPane.add(this.buttonsPanel);
        this.setContentPane(contentPane);
        this.widthInput.setText(String.valueOf(canvaController.getCanvaWidth()));
        this.heightInput.setText(String.valueOf(canvaController.getCanvaHeight()));
    }
}