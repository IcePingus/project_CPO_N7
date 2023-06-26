package graphic.controller;

import graphic.model.ShapeTypes;
import graphic.model.ToolContext;
import graphic.model.tools.ToolCommand;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.util.List;

public class ToolController {

    private Toolbox toolbox;

    public ToolController(Toolbox toolbox) {
        this.toolbox = toolbox;
    }

    public void addTool(ToolCommand tool) {
        this.toolbox.addTool(tool);
    }

    public ToolCommand getActiveTool() {
        return this.toolbox.getActiveTool();
    }

    public int getToolSize() {
        return this.toolbox.getToolSize();
    }

    public List<ToolCommand> getTools() {
        return this.toolbox.getTools();
    }

    public List<JButton> getToolsButtons() {
        return this.toolbox.getToolsButtons();
    }

    public boolean getIsSquareShape() {
        return this.toolbox.getIsSquareShape();
    }

    public void setActiveTool(int tool) {
        this.toolbox.setActiveTool(tool);
    }

    public void setToolSize(int size) {
        this.toolbox.setToolSize(size);
    }

    public void setIsFilledShape(boolean isFilledShape) {
        this.toolbox.setIsFilledShape(isFilledShape);
    }

    public void setShapeType(ShapeTypes shapeType) {
        this.toolbox.setShapeType(shapeType);
    }

    public void setIsSquareShape(boolean isSquareShape) {
        this.toolbox.setIsSquareShape(isSquareShape);
    }

}
