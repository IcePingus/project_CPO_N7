package graphic.controller;

import graphic.model.tools.PencilTool;
import graphic.model.tools.ToolCommand;

import java.util.Observable;

public class ToolController extends Observable {

    private ToolCommand activeTool = new PencilTool();

    public void setActiveTool(ToolCommand tool) {
        this.activeTool = tool;
        this.notifyObservers();
    }

    public ToolCommand getActiveTool() {
        return this.activeTool;
    }

}
