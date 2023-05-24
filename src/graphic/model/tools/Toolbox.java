package graphic.model.tools;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Toolbox {
    private List<JButton> toolsButtons;
    private List<ToolCommand> toolCommands;

    public Toolbox() {
        this.toolsButtons = new ArrayList<JButton>();
        this.toolCommands = new ArrayList<ToolCommand>();
    }

    public void addTool(ToolCommand tool) {
        this.toolsButtons.add(new JButton());
        this.toolCommands.add(tool);
    }

    public List<ToolCommand> getTools() {
        return this.toolCommands;
    }

    public List<JButton> getToolsButtons() {
        return this.toolsButtons;
    }
}
