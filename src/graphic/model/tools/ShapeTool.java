package graphic.model.tools;

import graphic.controller.CanvaController;
import graphic.model.ShapeTypes;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

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

    public ShapeTool() {
        this.name = "Shape";
        this.image = new ImageIcon(getClass().getResource("/assets/images/shape.png"));
        this.isResizable = false;
        this.isSquareRoundShape = false;
        this.hasShapeSelection = true;
        this.shapeType = ShapeTypes.RECTANGLE;
        this.isFilledShape = false;
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Icon getImage() {
        return this.image;
    }

    @Override
    public boolean getIsResizable() {
        return this.isResizable;
    }

    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    @Override
    public boolean getHasShapeSelection() {
        return this.hasShapeSelection;
    }

    @Override
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint, JComponent canva) {
        if (isFirstPoint) {
            this.oldX = oldX;
            this.oldY = oldY;

            if (click == InputEvent.BUTTON1_DOWN_MASK) {
                this.color = this.primaryColor;
            } else if (click == InputEvent.BUTTON3_DOWN_MASK) {
                this.color = this.secondaryColor;
            }
        }

        if (this.color != null) {
            int startX = Math.min(this.oldX, currentX);
            int startY = Math.min(this.oldY, currentY);
            int width = Math.abs(this.oldX - currentX);
            int height = Math.abs(this.oldY - currentY);

            if (this.shapeType == ShapeTypes.LINE) {
                ((Canva) canva).repaintComponent(this.shapeType, this.oldX, this.oldY, currentX, currentY, color, this.isFilledShape);
            } else {
                ((Canva) canva).repaintComponent(this.shapeType, startX, startY, width, height, color, this.isFilledShape);
            }
        }
    }

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
