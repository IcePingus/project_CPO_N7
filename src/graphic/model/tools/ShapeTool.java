package graphic.model.tools;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.color.ColorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class ShapeTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private Color primaryColor;
    private Color secondaryColor;

    private int oldX;
    private int oldY;
    private CanvaController canvaController;
    private boolean isFirstPoint;
    private BufferedImage bufferedImage;

    public ShapeTool(CanvaController canvaController) {
        this.name = "Shape";
        this.image = new ImageIcon(getClass().getResource("/assets/images/shape.png"));
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
        this.isResizable = false;
        this.isFirstPoint = false;
        this.canvaController = canvaController;
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
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint) {
        if (isFirstPoint) {
            this.oldX = oldX;
            this.oldY = oldY;
            this.isFirstPoint = !this.isFirstPoint;
            if (this.isFirstPoint) {
                this.bufferedImage = bufferedImage;
            }
        }

        if (!this.isFirstPoint) {
            Color color = null;
            if (click == InputEvent.BUTTON1_DOWN_MASK) {
                color = this.primaryColor;
            } else if (click == InputEvent.BUTTON3_DOWN_MASK) {
                color = this.secondaryColor;
            }

            if (color != null) {
                int startX = Math.min(this.oldX, currentX);
                int startY = Math.min(this.oldY, currentY);
                int width = Math.abs(this.oldX - currentX);
                int height = Math.abs(this.oldY - currentY);

                this.canvaController.setImage(this.bufferedImage);
                graphics2D.setPaint(color);
                graphics2D.drawRect(startX, startY, width, height);
                //graphics2D.drawOval(startX, startY, width, height);
                //graphics2D.drawLine(this.oldX, this.oldY, currentX, currentY);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorModel) {
            this.primaryColor = ((ColorModel) o).getPrimaryColor();
            this.secondaryColor = ((ColorModel) o).getSecondaryColor();
        }
    }
}
