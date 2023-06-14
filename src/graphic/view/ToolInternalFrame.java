package graphic.view;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.ShapeTypes;
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

    private JRadioButton squarePencilShapeButton;
    private JRadioButton circlePencilShapeButton;
    private ButtonGroup shapePencilButtonGroup;

    private JRadioButton rectangleShapeButton;
    private JRadioButton circleShapeButton;
    private JRadioButton lineShapeButton;
    private ButtonGroup shapeButtonGroup;

    private JRadioButton strokeButton;
    private JRadioButton fillButton;
    private ButtonGroup fillButtonGroup;

    private JPanel toolsPanel;
    private JPanel toolOptionsPanel;

    private JPanel sliderPanel;
    private JPanel squareShapePanel;
    private  JPanel shapePanel;
    private  JPanel fillOptionPanel;

    private JTextField sizeLabel;

    public ToolInternalFrame(Toolbox toolbox, ColorController colorController, ColorModel colorModel, CanvaController canvaController) {
        super("Tools");
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(148, 360);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        this.setLayout(new BorderLayout());

        this.toolsPanel = new JPanel();

        this.toolsPanel.setLayout(new GridLayout(4, 2));

        this.toolbox = toolbox;
        this.toolbox.addTool(new RubberTool());
        this.toolbox.addTool(new BucketTool());
        this.toolbox.addTool(new PickerTool(colorController));
        this.toolbox.addTool(new HighlighterTool());

        TextTool textTool = new TextTool();
        this.toolbox.addObserver(textTool);
        this.toolbox.addTool(textTool);

        ShapeTool shapeTool = new ShapeTool();
        this.toolbox.addObserver(shapeTool);
        this.toolbox.addTool(shapeTool);

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

        this.shapePanel = new JPanel();
        this.shapePanel.setLayout(new GridLayout(1, 3));

        this.fillOptionPanel = new JPanel();
        this.fillOptionPanel.setLayout(new GridLayout(1, 2));

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

        this.sliderPanel.add(sizeLabel);
        this.sliderPanel.add(sliderSize);

        this.squarePencilShapeButton = new JRadioButton("Square");
        this.squarePencilShapeButton.setSelected(true);
        this.squarePencilShapeButton.addActionListener(this);
        this.circlePencilShapeButton = new JRadioButton("Circle");
        this.circlePencilShapeButton.addActionListener(this);

        this.shapePencilButtonGroup = new ButtonGroup();
        this.shapePencilButtonGroup.add(this.squarePencilShapeButton);
        this.shapePencilButtonGroup.add(this.circlePencilShapeButton);

        this.squareShapePanel.add(this.squarePencilShapeButton);
        this.squareShapePanel.add(this.circlePencilShapeButton);

        this.rectangleShapeButton = new JRadioButton("Rectangle");
        this.rectangleShapeButton.setSelected(true);
        this.rectangleShapeButton.addActionListener(this);
        this.circleShapeButton = new JRadioButton("Circle");
        this.circleShapeButton.addActionListener(this);
        this.lineShapeButton = new JRadioButton("Line");
        this.lineShapeButton.addActionListener(this);

        this.shapeButtonGroup = new ButtonGroup();
        this.shapeButtonGroup.add(this.rectangleShapeButton);
        this.shapeButtonGroup.add(this.circleShapeButton);
        this.shapeButtonGroup.add(this.lineShapeButton);

        this.shapePanel.add(this.rectangleShapeButton);
        this.shapePanel.add(this.circleShapeButton);
        this.shapePanel.add(this.lineShapeButton);

        this.strokeButton = new JRadioButton("Stroke");
        this.strokeButton.setSelected(true);
        this.strokeButton.addActionListener(this);
        this.fillButton = new JRadioButton("Fill");
        this.fillButton.addActionListener(this);

        this.fillButtonGroup = new ButtonGroup();
        this.fillButtonGroup.add(this.strokeButton);
        this.fillButtonGroup.add(this.fillButton);

        this.fillOptionPanel.add(this.strokeButton);
        this.fillOptionPanel.add(this.fillButton);

        this.toolOptionsPanel = new JPanel();
        this.toolOptionsPanel.setLayout(new GridLayout(2, 1));

        this.add(this.toolOptionsPanel, BorderLayout.SOUTH);
        this.toolOptionsPanel.add(this.sliderPanel);
        this.toolOptionsPanel.add(this.squareShapePanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.squarePencilShapeButton || e.getSource() == this.circlePencilShapeButton) {
            this.toolbox.setIsSquareShape(this.squarePencilShapeButton.isSelected());
        } else if(e.getSource() == this.rectangleShapeButton || e.getSource() == this.circleShapeButton || e.getSource() == this.lineShapeButton) {
            if (e.getSource() == this.rectangleShapeButton) {
                this.toolbox.setShapeType(ShapeTypes.RECTANGLE);
            } else if (e.getSource() == this.circleShapeButton) {
                this.toolbox.setShapeType(ShapeTypes.OVAL);
            } else {
                this.toolbox.setShapeType(ShapeTypes.LINE);
            }
        } else if (e.getSource() == this.strokeButton || e.getSource() == this.fillButton) {
            this.toolbox.setIsFilledShape(this.fillButton.isSelected());
        } else if (e.getSource() == this.sizeLabel) {
            if (Integer.parseInt(this.sizeLabel.getText()) <= 0 || Integer.parseInt(this.sizeLabel.getText()) > 1000) {
                JOptionPane.showMessageDialog(null,
                        "Please enter number bigger than 0 and smaller than 1000", "Tool size",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                this.sliderSize.setValue(Integer.parseInt(this.sizeLabel.getText()));
            }
            //TO CHANGE SEE COMMENTS EARLIER
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
                        this.setSize(148, 315);
                    } else if (!this.toolbox.getActiveTool().getIsResizable() && this.toolbox.getActiveTool().getIsSquareRoundShape()) {
                        this.toolOptionsPanel.setLayout(new GridLayout(1, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.squareShapePanel);
                        this.setSize(148, 333);
                    } else if (this.toolbox.getActiveTool().getIsResizable() && !this.toolbox.getActiveTool().getIsSquareRoundShape()) {
                        this.toolOptionsPanel.setLayout(new GridLayout(1, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.sliderPanel);
                        this.setSize(148, 333);
                    } else {
                        this.toolOptionsPanel.setLayout(new GridLayout(2, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.sliderPanel);
                        this.toolOptionsPanel.add(this.squareShapePanel);
                        this.setSize(148, 360);
                    }

                    if (this.toolbox.getActiveTool().getHasShapeSelection()) {
                        this.toolOptionsPanel.setVisible(true);
                        this.toolOptionsPanel.setLayout(new GridLayout(2, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.shapePanel);
                        this.toolOptionsPanel.add(this.fillOptionPanel);
                        this.setSize(148, 360);
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
