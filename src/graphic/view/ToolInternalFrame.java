package graphic.view;

import graphic.controller.ColorController;
import graphic.model.tools.BucketTool;
import graphic.model.tools.PickerTool;
import graphic.model.tools.RubberTool;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class ToolInternalFrame extends JInternalFrame implements ActionListener, ChangeListener {

    private final Toolbox toolbox;
    private int activeTool;
    private JSlider sliderSize;
    private JPanel toolsPanel;
    private JPanel sliderPanel;
    private JLabel sizeLabel;

    public ToolInternalFrame(Toolbox toolbox, ColorController colorController) {
        super("Tools");
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(148, 190);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        this.setLayout(new BorderLayout());

        this.toolsPanel = new JPanel();

        this.toolsPanel.setLayout(new GridLayout(2,2));

        this.toolbox = toolbox;
        this.toolbox.addTool(new RubberTool());
        this.toolbox.addTool(new BucketTool());
        this.toolbox.addTool(new PickerTool());

        for (int i = 0; i < this.toolbox.getTools().size(); i++) {
            this.toolsPanel.add(this.toolbox.getToolsButtons().get(i));
            this.toolbox.getToolsButtons().get(i).setName(this.toolbox.getTools().get(i).getName());
            this.toolbox.getToolsButtons().get(i).setIcon(this.toolbox.getTools().get(i).getImage());
            this.toolbox.getToolsButtons().get(i).addActionListener(this);
            colorController.addObserver(this.toolbox.getTools().get(i));
        }
        this.activeTool = 0;
        this.toolbox.getToolsButtons().get(0).setBackground(Color.red);

        this.add(this.toolsPanel, BorderLayout.CENTER);

        this.sliderPanel = new JPanel();
        this.sliderPanel.setLayout(new BorderLayout());
        this.sliderPanel.setSize(148, 30);
        this.sizeLabel = new JLabel(" 5");
        this.sliderPanel.add(sizeLabel, BorderLayout.WEST);
        this.sliderSize = new JSlider(1, 15, 5);
        this.sliderSize.addChangeListener(this);
        this.sliderPanel.add(sliderSize, BorderLayout.CENTER);

        this.add(this.sliderPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < this.toolbox.getTools().size(); i++) {
            if (e.getSource() == this.toolbox.getToolsButtons().get(i) && this.activeTool != i) {
                this.toolbox.getToolsButtons().get(this.activeTool).setBackground(null);
                this.activeTool = i;
                this.toolbox.getToolsButtons().get(i).setBackground(Color.red);
                this.toolbox.setActiveTool(this.activeTool);

                if (!this.toolbox.getActiveTool().getIsResizable()) {
                    this.sliderPanel.setVisible(false);
                    this.setSize(148, 180);
                } else {
                    this.sliderPanel.setVisible(true);
                    this.setSize(148, 190);
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
