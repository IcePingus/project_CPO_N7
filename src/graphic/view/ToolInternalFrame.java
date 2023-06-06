package graphic.view;

import graphic.controller.ColorController;
import graphic.model.color.ColorModel;
import graphic.model.tools.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private JPanel sliderPanel;
    private JLabel sizeLabel;

    public ToolInternalFrame(Toolbox toolbox, ColorController colorController, ColorModel colorModel) {
        super("Tools");
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        //this.setSize(148, 190);
        this.setSize(148, 250);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        this.setLayout(new BorderLayout());

        this.toolsPanel = new JPanel();

        this.toolsPanel.setLayout(new GridLayout(3,2));

        this.toolbox = toolbox;
        this.toolbox.addTool(new RubberTool());
        this.toolbox.addTool(new BucketTool());
        this.toolbox.addTool(new PickerTool(colorController));
        this.toolbox.addTool(new HighlighterTool());

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
        this.sliderPanel.setLayout(new GridLayout(2, 2));

        this.sizeLabel = new JLabel(" 5");

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
        this.sliderPanel.add(this.squareShapeButton);
        this.sliderPanel.add(this.circleShapeButton);

        this.add(this.sliderPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.squareShapeButton || e.getSource() == this.circleShapeButton) {
            this.toolbox.setIsSquareShape(this.squareShapeButton.isSelected());
        } else {
            for (int i = 0; i < this.toolbox.getTools().size(); i++) {
                if (e.getSource() == this.toolbox.getToolsButtons().get(i) && this.activeTool != i) {
                    this.toolbox.getToolsButtons().get(this.activeTool).setBackground(null);
                    this.activeTool = i;
                    this.toolbox.getToolsButtons().get(i).setBackground(Color.red);
                    this.toolbox.setActiveTool(this.activeTool);

                    if (!this.toolbox.getActiveTool().getIsResizable()) {
                        this.sliderPanel.setVisible(false);
                        this.setSize(148, 240);
                    } else {
                        this.sliderPanel.setVisible(true);
                        this.setSize(148, 250);
                    }
                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.sizeLabel.setText(" " + this.sliderSize.getValue());
        this.toolbox.setToolSize(this.sliderSize.getValue());
    }
}
