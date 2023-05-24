package graphique.view;

import graphique.model.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ToolInternalFrame extends JInternalFrame implements ActionListener {

    private Toolbox toolbox;
    private int toolChoisi;

    public ToolInternalFrame() {
        super("Tools");
        this.setMaximizable(false);
        this.setIconifiable(true);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(148, 158);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new GridLayout(2,2));

        this.toolbox = new Toolbox();
        this.toolbox.addTool(new PencilTool());
        this.toolbox.addTool(new RubberTool());
        this.toolbox.addTool(new BucketTool());
        this.toolbox.addTool(new PickerTool());

        for (int i = 0; i < this.toolbox.getTools().size(); i++) {
            this.add(this.toolbox.getToolsButtons().get(i));
            this.toolbox.getToolsButtons().get(i).setName(this.toolbox.getTools().get(i).getName());
            this.toolbox.getToolsButtons().get(i).setIcon(this.toolbox.getTools().get(i).getImage());
            this.toolbox.getToolsButtons().get(i).addActionListener(this);
        }
        this.toolChoisi = 0;
        this.toolbox.getToolsButtons().get(0).setBackground(Color.red);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < this.toolbox.getTools().size(); i++) {
            if (e.getSource() == this.toolbox.getToolsButtons().get(i) && this.toolChoisi != i) {
                this.toolbox.getToolsButtons().get(this.toolChoisi).setBackground(null);
                this.toolChoisi = i;
                this.toolbox.getToolsButtons().get(i).setBackground(Color.red);
            }
        }
    }
}
