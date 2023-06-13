package graphic.view;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.color.ColorModel;
import graphic.model.tools.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolInternalFrame extends JInternalFrame implements ActionListener, ChangeListener {

    private final Toolbox toolbox;
    private int activeTool;
    private JSlider sliderSize;
    private JRadioButton squareShapeButton;
    private JRadioButton circleShapeButton;
    private ButtonGroup shapeButtonGroup;
    private JPanel toolsPanel;
    private JPanel toolOptionsPanel;
    private JPanel squareShapePanel;
    private JPanel sliderPanel;
    private JTextField sizeLabel;

    public ToolInternalFrame(Toolbox toolbox, ColorController colorController, ColorModel colorModel, CanvaController canvaController) {
        super("Tools");
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        //this.setSize(148, 190);
        this.setSize(148, 280);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        this.setLayout(new BorderLayout());

        this.toolsPanel = new JPanel();

        this.toolsPanel.setLayout(new GridLayout(3, 2));

        this.toolbox = toolbox;
        this.toolbox.addTool(new RubberTool());
        this.toolbox.addTool(new BucketTool());
        this.toolbox.addTool(new PickerTool(colorController));
        this.toolbox.addTool(new HighlighterTool());
        this.toolbox.addTool(new ShapeTool(canvaController));

        TextTool textTool = new TextTool();
        this.toolbox.addObserver(textTool);
        this.toolbox.addTool(textTool);

        for (int i = 0; i < this.toolbox.getTools().size(); i++) {
            this.toolsPanel.add(this.toolbox.getToolsButtons().get(i));
            this.toolbox.getToolsButtons().get(i).setName(this.toolbox.getTools().get(i).getName());
            this.toolbox.getToolsButtons().get(i).setIcon(this.toolbox.getTools().get(i).getImage());
            this.toolbox.getToolsButtons().get(i).addActionListener(this);
            colorModel.addObserver(this.toolbox.getTools().get(i));
        }
        this.activeTool = 0;
        this.toolbox.getToolsButtons().get(0).setBackground(Color.red);

        this.add(this.toolsPanel, BorderLayout.CENTER);

        this.sliderPanel = new JPanel();
        this.sliderPanel.setLayout(new GridLayout(1, 2));

        this.squareShapePanel = new JPanel();
        this.squareShapePanel.setLayout(new GridLayout(1, 2));

        this.sizeLabel = new JTextField("5");
        this.sizeLabel.addActionListener(this);

        /*this.sizeLabel.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (Integer.parseInt(sizeLabel.getText()) <= 0 ||Integer.parseInt(sizeLabel.getText()) > 1000) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter number bigger than 0 and smaller than 1000", "Tool size",
                            JOptionPane.WARNING_MESSAGE);
                    if (Integer.parseInt(sizeLabel.getText()) <= 0) {
                        sizeLabel.setText(1 + "");
                    } else {
                        sizeLabel.setText(1000 + "");
                    }
                }
                sliderSize.setValue(Integer.parseInt(sizeLabel.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (sizeLabel.getText().equals("")) {
                    sizeLabel.setText(1 + "");
                }
                sliderSize.setValue(Integer.parseInt(sizeLabel.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });*/

        this.sliderSize = new JSlider(1, 1000, 5);
        this.sliderSize.addChangeListener(this);

        this.squareShapeButton = new JRadioButton("Square");
        this.squareShapeButton.setSelected(true);
        this.squareShapeButton.addActionListener(this);
        this.circleShapeButton = new JRadioButton("Circle");
        this.circleShapeButton.addActionListener(this);

        this.shapeButtonGroup = new ButtonGroup();
        this.shapeButtonGroup.add(this.squareShapeButton);
        this.shapeButtonGroup.add(this.circleShapeButton);

        this.sliderPanel.add(sizeLabel);
        this.sliderPanel.add(sliderSize);
        this.squareShapePanel.add(this.squareShapeButton);
        this.squareShapePanel.add(this.circleShapeButton);

        this.toolOptionsPanel = new JPanel();
        this.toolOptionsPanel.setLayout(new GridLayout(2, 1));

        this.add(this.toolOptionsPanel, BorderLayout.SOUTH);
        this.toolOptionsPanel.add(this.sliderPanel);
        this.toolOptionsPanel.add(this.squareShapePanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.squareShapeButton || e.getSource() == this.circleShapeButton) {
            this.toolbox.setIsSquareShape(this.squareShapeButton.isSelected());
        } else if (e.getSource() == this.sizeLabel) {
            if (Integer.parseInt(this.sizeLabel.getText()) <= 0 || Integer.parseInt(this.sizeLabel.getText()) > 1000) {
                JOptionPane.showMessageDialog(null,
                        "Please enter number bigger than 0 and smaller than 1000", "Tool size",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                this.sliderSize.setValue(Integer.parseInt(this.sizeLabel.getText()));
            }
        } else {
            for (int i = 0; i < this.toolbox.getTools().size(); i++) {
                if (e.getSource() == this.toolbox.getToolsButtons().get(i) && this.activeTool != i) {
                    this.toolbox.getToolsButtons().get(this.activeTool).setBackground(null);
                    this.activeTool = i;
                    this.toolbox.getToolsButtons().get(i).setBackground(Color.red);
                    this.toolbox.setActiveTool(this.activeTool);

                    this.toolOptionsPanel.setVisible(true);
                    if (!this.toolbox.getActiveTool().getIsResizable() && !this.toolbox.getActiveTool().getIsSquareRoundShape()) {
                        this.toolOptionsPanel.setVisible(false);
                        this.toolOptionsPanel.removeAll();
                        this.setSize(148, 240);
                    } else if (!this.toolbox.getActiveTool().getIsResizable() && this.toolbox.getActiveTool().getIsSquareRoundShape()) {
                        this.toolOptionsPanel.setLayout(new GridLayout(1, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.squareShapePanel);
                        this.setSize(148, 250);
                    } else if (this.toolbox.getActiveTool().getIsResizable() && !this.toolbox.getActiveTool().getIsSquareRoundShape()) {
                        this.toolOptionsPanel.setLayout(new GridLayout(1, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.sliderPanel);
                        this.setSize(148, 250);
                    } else {
                        this.toolOptionsPanel.setLayout(new GridLayout(2, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.sliderPanel);
                        this.toolOptionsPanel.add(this.squareShapePanel);
                        this.setSize(148, 280);
                    }
                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.sizeLabel.setText(this.sliderSize.getValue() + "");
        this.toolbox.setToolSize(this.sliderSize.getValue());
    }
}
