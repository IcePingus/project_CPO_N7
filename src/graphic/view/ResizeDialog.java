package graphic.view;

import graphic.controller.CanvaController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResizeDialog extends JDialog implements ActionListener {

    private JPanel contentPane;
    private JPanel inputsPanel;
    private JPanel buttonsPanel;

    private JLabel widthLabel;
    private JLabel heightLabel;
    private JLabel errorMessage;

    private JTextField widthInput;
    private JTextField heightInput;

    private JButton confirmButton;

    private CanvaController canvaController;

    public ResizeDialog(CanvaController canvaController) {
        super();
        this.setResizable(false);
        this.setSize(500, 200);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setName("Resize");
        this.setModal(true);

        this.setLayout(new BorderLayout());

        this.contentPane = new JPanel(new GridLayout(3, 1));
        this.inputsPanel = new JPanel(new GridLayout(2, 2));
        this.buttonsPanel = new JPanel(new FlowLayout());

        this.widthLabel = new JLabel("Width : ");
        this.heightLabel = new JLabel("Height : ");
        this.errorMessage = new JLabel("Invalid width or height !");

        this.errorMessage.setForeground(Color.RED);
        this.errorMessage.setVisible(false);

        this.widthInput = new JTextField(String.valueOf(canvaController.getCanvaWidth()));
        this.heightInput = new JTextField(String.valueOf(canvaController.getCanvaHeight()));

        this.confirmButton = new JButton("Confirm");
        this.confirmButton.addActionListener(this);

        this.inputsPanel.add(this.widthLabel);
        this.inputsPanel.add(this.widthInput);

        this.inputsPanel.add(this.heightLabel);
        this.inputsPanel.add(this.heightInput);

        this.buttonsPanel.add(this.confirmButton, BorderLayout.CENTER);

        this.contentPane.add(this.inputsPanel);
        this.contentPane.add(this.errorMessage);
        this.contentPane.add(this.buttonsPanel);

        this.setContentPane(this.contentPane);

        this.canvaController = canvaController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.confirmButton) {
            try {
                int width = Integer.parseInt(this.widthInput.getText());
                int height = Integer.parseInt(this.heightInput.getText());
                this.canvaController.resizeCanva(width, height);
                this.errorMessage.setVisible(false);
                this.setVisible(false);
            } catch (Exception exception) {
                this.errorMessage.setVisible(true);
                this.validate();
                this.repaint();
            }
        }
    }
}