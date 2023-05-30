package graphic.view;

import graphic.model.tools.BucketTool;
import graphic.model.tools.PickerTool;
import graphic.model.tools.RubberTool;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolInternalFrame extends JInternalFrame implements ActionListener {

    private final Toolbox toolbox;
    private int activeTool;

    public ToolInternalFrame(Toolbox toolbox) {
        super("Tools");
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(148, 158);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new GridLayout(2,2));

        this.toolbox = toolbox;
        this.toolbox.addTool(new RubberTool());
        this.toolbox.addTool(new BucketTool());
        this.toolbox.addTool(new PickerTool());

        for (int i = 0; i < this.toolbox.getTools().size(); i++) {
            this.add(this.toolbox.getToolsButtons().get(i));
            this.toolbox.getToolsButtons().get(i).setName(this.toolbox.getTools().get(i).getName());
            this.toolbox.getToolsButtons().get(i).setIcon(this.toolbox.getTools().get(i).getImage());
            this.toolbox.getToolsButtons().get(i).addActionListener(this);
        }
        this.activeTool = 0;
        this.toolbox.getToolsButtons().get(0).setBackground(Color.red);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < this.toolbox.getTools().size(); i++) {
            if (e.getSource() == this.toolbox.getToolsButtons().get(i) && this.activeTool != i) {
                this.toolbox.getToolsButtons().get(this.activeTool).setBackground(null);
                this.activeTool = i;
                this.toolbox.getToolsButtons().get(i).setBackground(Color.red);
                this.toolbox.setActiveTool(this.activeTool);
            }
        }
    }
}
