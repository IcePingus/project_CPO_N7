package graphic.model.tools;

import graphic.model.ShapeTypes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * The Toolbox class represents a toolbox in a graphics application.
 * It extends the Observable class to notify observers of changes in the toolbox state.
 *
 * @author Team 3
 */
public class Toolbox extends Observable {
    private List<JButton> toolsButtons;
    private List<ToolCommand> toolCommands;
    private ToolCommand activeTool;
    private int toolSize;
    private boolean isSquareShape;
    private ShapeTypes shapeType;
    private boolean isFilledShape;

    /**
     * Constructs a Toolbox object with default settings.
     * It initializes the tools buttons list, tool commands list, and sets the active tool, tool size,
     * shape type, and filled shape to default values.
     */
    public Toolbox() {
        this.toolsButtons = new ArrayList<>();
        this.toolCommands = new ArrayList<>();
        // Ajouter l'outil crayon et le définir comme actif
        this.addTool(new PencilTool());
        this.activeTool = this.getTool(0);
        // Options par défaut
        this.toolSize = 5;
        this.isSquareShape = true;
        this.shapeType = ShapeTypes.RECTANGLE;
        this.isFilledShape = false;
    }

    /**
     * Adds a tool to the toolbox.
     *
     * @param tool the tool command to add
     */
    public void addTool(ToolCommand tool) {
        this.toolsButtons.add(new JButton());
        this.toolCommands.add(tool);
    }

    /**
     * Returns the list of tool buttons in the toolbox.
     *
     * @return the list of tool buttons
     */
    public List<JButton> getToolsButtons() {
        return this.toolsButtons;
    }

    /**
     * Returns the list of tool commands in the toolbox.
     *
     * @return the list of tool commands
     */
    public List<ToolCommand> getTools() {
        return this.toolCommands;
    }

    /**
     * Returns the tool command at the specified index.
     *
     * @param index the index of the tool command
     * @return the tool command at the specified index
     */
    public ToolCommand getTool(int index) {
        return this.toolCommands.get(index);
    }

    /**
     * Returns the active tool.
     *
     * @return the active tool
     */
    public ToolCommand getActiveTool() {
        return this.activeTool;
    }

    /**
     * Returns the tool size.
     *
     * @return the tool size
     */
    public int getToolSize() {
        return this.toolSize;
    }

    /**
     * Returns a flag indicating whether the shape should be square.
     *
     * @return true if the shape should be square, false otherwise
     */
    public boolean getIsSquareShape() {
        return this.isSquareShape;
    }

    /**
     * Returns the shape type.
     *
     * @return the shape type
     */
    public ShapeTypes getShapeType() {
        return this.shapeType;
    }

    /**
     * Returns a flag indicating whether the shape should be filled.
     *
     * @return true if the shape should be filled, false otherwise
     */
    public boolean getIsFilledShape() {
        return this.isFilledShape;
    }

    /**
     * Sets the active tool based on the specified tool index.
     * Notifies observers of the toolbox state change.
     *
     * @param tool the index of the tool to set as active
     */
    public void setActiveTool(int tool) {
        this.activeTool = this.getTool(tool);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the tool size.
     * Notifies observers of the toolbox state change.
     *
     * @param size the tool size to set
     */
    public void setToolSize(int size) {
        this.toolSize = size;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the flag indicating whether the shape should be square.
     *
     * @param isSquareShape true if the shape should be square, false otherwise
     */
    public void setIsSquareShape(boolean isSquareShape) {
        this.isSquareShape = isSquareShape;
    }

    /**
     * Sets the shape type.
     * Notifies observers of the toolbox state change.
     *
     * @param shapeType the shape type to set
     */
    public void setShapeType(ShapeTypes shapeType) {
        this.shapeType = shapeType;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets the flag indicating whether the shape should be filled.
     * Notifies observers of the toolbox state change.
     *
     * @param filledShape true if the shape should be filled, false otherwise
     */
    public void setIsFilledShape(boolean filledShape) {
        this.isFilledShape = filledShape;
        this.setChanged();
        this.notifyObservers();
    }
}
