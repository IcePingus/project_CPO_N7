package graphic.model.tools;

import graphic.model.ShapeTypes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Toolbox extends Observable {
    private List<JButton> toolsButtons;
    private List<ToolCommand> toolCommands;
    private ToolCommand activeTool;
    private int toolSize;
    private boolean isSquareShape;
    private ShapeTypes shapeType;
    private boolean isFilledShape;

    public Toolbox() {
        this.toolsButtons = new ArrayList<>();
        this.toolCommands = new ArrayList<>();
        this.addTool(new PencilTool());
        this.activeTool = this.getTool(0);
        this.toolSize = 5;
        this.isSquareShape = true;
        this.shapeType = ShapeTypes.RECTANGLE;
        this.isFilledShape = false;
    }

    public void addTool(ToolCommand tool) {
        this.toolsButtons.add(new JButton());
        this.toolCommands.add(tool);
    }

    public List<JButton> getToolsButtons() {
        return this.toolsButtons;
    }

    public List<ToolCommand> getTools() {
        return this.toolCommands;
    }

    public ToolCommand getTool(int i) {
        return this.toolCommands.get(i);
    }

    public ToolCommand getActiveTool() {
        return this.activeTool;
    }

    public int getToolSize() {
        return this.toolSize;
    }

    public boolean getIsSquareShape() {
        return this.isSquareShape;
    }

    public ShapeTypes getShapeType() {
        return this.shapeType;
    }

    public boolean getIsFilledShape() {
        return this.isFilledShape;
    }

    public void setActiveTool(int tool) {
        this.activeTool = this.getTool(tool);
        this.setChanged();
        this.notifyObservers();
    }


    public void setToolSize(int size) {
        this.toolSize = size;
        this.setChanged();
        this.notifyObservers();
    }

    public void setIsSquareShape(boolean isSquareShape) {
        this.isSquareShape = isSquareShape;
    }

    public void setShapeType(ShapeTypes shapeType) {
        this.shapeType = shapeType;
        this.setChanged();
        this.notifyObservers();
    }

    public void setIsFilledShape(boolean filledShape) {
        this.isFilledShape = filledShape;
        this.setChanged();
        this.notifyObservers();
    }
}
