package graphic.model.tools;

import graphic.model.ShapeTypes;
import graphic.model.ToolContext;
import graphic.model.color.ColorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Observable;

/**
 * The ShapeTool class represents a shape tool in a graphics application.
 * It implements the ToolCommand interface and provides functionality for drawing shapes on the canvas.
 *
 * @author Team 3
 */
public class ShapeTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;
    private final boolean hasShapeSelection;
    private Color primaryColor;
    private Color secondaryColor;

    private int oldX;
    private int oldY;

    private ShapeTypes shapeType;
    private boolean isFilledShape;

    private Color color;

    /**
     * Constructs a ShapeTool object.
     * It initializes the name, image, and sets the tool properties.
     */
    public ShapeTool() {
        this.name = "Shape";
        this.image = new ImageIcon(getClass().getResource("/assets/images/shape.png"));
        this.isResizable = true;
        this.isSquareRoundShape = false;
        this.hasShapeSelection = true;
        this.shapeType = ShapeTypes.RECTANGLE;
        this.isFilledShape = false;
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
    }

    /**
     * Returns the name of the shape tool.
     *
     * @return the name of the tool
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the image icon representing the shape tool.
     *
     * @return the image icon of the tool
     */
    @Override
    public Icon getImage() {
        return this.image;
    }

    /**
     * Returns whether the shape tool is resizable.
     *
     * @return true if the tool is resizable, false otherwise
     */
    @Override
    public boolean getIsResizable() {
        return this.isResizable;
    }

    /**
     * Returns whether the shape tool is square or round in shape.
     *
     * @return true if the tool is square or round in shape, false otherwise
     */
    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    /**
     * Returns whether the shape tool requires shape selection.
     *
     * @return true if the tool requires shape selection, false otherwise
     */
    @Override
    public boolean getHasShapeSelection() {
        return this.hasShapeSelection;
    }

    /**
     * Executes the shape tool operation.
     * It draws shapes on the canvas based on the current tool properties.
     *
     * @param context          the application context
     */
    @Override
    public void execute(ToolContext context) {
        if (context.isFirstPoint()) {
            // Sauvegarder le point de début lors de la création de la forme
            this.oldX = context.getOldX();
            this.oldY = context.getOldY();

            // Récupérer la couleur en fonction du type de clic
            if (context.getClick() == InputEvent.BUTTON1_DOWN_MASK) {
                this.color = this.primaryColor;
            } else if (context.getClick() == InputEvent.BUTTON3_DOWN_MASK) {
                this.color = this.secondaryColor;
            }
        }

        if (this.color != null) {
            // Définir le point de début et la largeur et hauteur de la forme
            // Le point de début doit être le point le plus en haut à gauche
            int startX = Math.min(this.oldX, context.getCurrentX());
            int startY = Math.min(this.oldY, context.getCurrentY());
            int width = Math.abs(this.oldX - context.getCurrentX());
            int height = Math.abs(this.oldY - context.getCurrentY());

            // Définir la création de forme et ses attributs sur la toile
            if (this.shapeType == ShapeTypes.LINE) {
                // Ligne ne fonctionne pas avec point de début et largeur et hauteur, donc on passe le point de début et
                // le point de fin en paramètres
                context.getCanvaComponent().repaintComponent(this.shapeType, this.oldX, this.oldY, context.getCurrentX(), context.getCurrentY(), color, this.isFilledShape);
            } else {
                context.getCanvaComponent().repaintComponent(this.shapeType, startX, startY, width, height, color, this.isFilledShape);
            }
        }
    }

    /**
     * Updates the shape tool based on changes in the observed object.
     *
     * @param o   the observed object
     * @param arg the argument passed by the observed object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorModel) {
            this.primaryColor = ((ColorModel) o).getPrimaryColor();
            this.secondaryColor = ((ColorModel) o).getSecondaryColor();
        } else if (o instanceof Toolbox) {
            this.shapeType = ((Toolbox) o).getShapeType();
            this.isFilledShape = ((Toolbox) o).getIsFilledShape();
        }
    }
}