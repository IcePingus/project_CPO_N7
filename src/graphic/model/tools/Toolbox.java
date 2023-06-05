package graphic.model.tools;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Toolbox extends Observable {
    private List<JButton> toolsButtons;
    private List<ToolCommand> toolCommands;
    private ToolCommand activeTool;
    private int toolSize;

    public Toolbox() {
        this.toolsButtons = new ArrayList<>();
        this.toolCommands = new ArrayList<>();
        this.addTool(new PencilTool());
        this.activeTool = this.getTool(0);
        this.toolSize = 5;
    }

    public void addTool(ToolCommand tool) {
        this.toolsButtons.add(new JButton());
        this.toolCommands.add(tool);
    }

    public ToolCommand getTool(int i) {
        return this.toolCommands.get(i);
    }

    public List<ToolCommand> getTools() {
        return this.toolCommands;
    }

    public List<JButton> getToolsButtons() {
        return this.toolsButtons;
    }

    public void setActiveTool(int tool) {
        this.activeTool = this.getTool(tool);

        this.setChanged();
        this.notifyObservers();
    }

    public ToolCommand getActiveTool() {
        return this.activeTool;
    }

    public void setToolSize(int size) {
        this.toolSize = size;
    }

    public int getToolSize() {
        return this.toolSize;
    }
}
