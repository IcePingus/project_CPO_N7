package graphic.model.tools;

import graphic.model.color.ColorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class PencilTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private Color primaryColor;
    private Color secondaryColor;

    public PencilTool() {
        this.name = "Pencil";
        this.image = new ImageIcon(getClass().getResource("/assets/images/pencil.png"));
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
        this.isResizable = true;
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
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square) {
        Color color = null;
        if (click == InputEvent.BUTTON1_DOWN_MASK) {
            color = primaryColor;
        } else if (click == InputEvent.BUTTON3_DOWN_MASK) {
            color = secondaryColor;
        }
        if (color != null) {
            graphics2D.setPaint(color);
            graphics2D.drawLine(oldX, oldY, currentX, currentY);

            if (square) {
                graphics2D.fillRect(oldX - size / 2, oldY - size / 2, size, size);
            } else {
                graphics2D.fillOval(oldX - size / 2, oldY - size / 2, size, size);
            }

            int dx = Math.abs(currentX - oldX);
            int dy = Math.abs(currentY - oldY);
            int sx = oldX < currentX ? 1 : -1;
            int sy = oldY < currentY ? 1 : -1;
            int err = dx - dy;

            while (oldX != currentX || oldY != currentY) {

                if (square) {
                    graphics2D.fillRect(oldX - size / 2, oldY - size / 2, size, size);
                } else {
                    graphics2D.fillOval(oldX - size / 2, oldY - size / 2, size, size);
                }


                int e2 = 2 * err;
                if (e2 > -dy) {
                    err -= dy;
                    oldX += sx;
                }
                if (e2 < dx) {
                    err += dx;
                    oldY += sy;
                }
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