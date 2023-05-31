package graphic.model.tools;

import graphic.controller.ColorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
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
    public void execute(int oldX, int oldY, int currentX, int currentY, Graphics2D graphics2D, int click, int size) {
        Color color = null;
        if (click == InputEvent.BUTTON1_DOWN_MASK) {
            color = primaryColor;
        } else if (click == InputEvent.BUTTON3_DOWN_MASK) {
            color = secondaryColor;
        }
        if (color != null) {
            graphics2D.setPaint(color);
            for (int i = 0; i < size/2; i++) {
                for (int j = 0 - size; j < size/2; j++) {
                    graphics2D.drawLine(oldX - j, oldY - size, currentX - j, currentY - size);
                    graphics2D.drawLine(oldX - j, oldY - size, currentX - j, currentY + size);
                    graphics2D.drawLine(oldX - j, oldY - size, currentX + j, currentY - size);
                    graphics2D.drawLine(oldX - j, oldY - size, currentX + j, currentY + size);
                    graphics2D.drawLine(oldX - j, oldY + size, currentX - j, currentY - size);
                    graphics2D.drawLine(oldX - j, oldY + size, currentX - j, currentY + size);
                    graphics2D.drawLine(oldX - j, oldY - size, currentX + j, currentY + size);
                    graphics2D.drawLine(oldX - j, oldY + size, currentX + j, currentY + size);
                    graphics2D.drawLine(oldX + j, oldY - size, currentX - j, currentY - size);
                    graphics2D.drawLine(oldX + j, oldY - size, currentX - j, currentY + size);
                    graphics2D.drawLine(oldX + j, oldY - size, currentX + j, currentY - size);
                    graphics2D.drawLine(oldX + j, oldY - size, currentX + j, currentY + size);
                    graphics2D.drawLine(oldX + j, oldY + size, currentX - j, currentY - size);
                    graphics2D.drawLine(oldX + j, oldY + size, currentX - j, currentY + size);
                    graphics2D.drawLine(oldX + j, oldY + size, currentX + j, currentY - size);
                    graphics2D.drawLine(oldX + j, oldY + size, currentX + j, currentY + size);

                    graphics2D.drawLine(oldX - size, oldY - j, currentX - size, currentY + j);
                    graphics2D.drawLine(oldX - size, oldY - j, currentX + size, currentY - j);
                    graphics2D.drawLine(oldX - size, oldY - j, currentX + size, currentY + j);
                    graphics2D.drawLine(oldX - size, oldY + j, currentX - size, currentY - j);
                    graphics2D.drawLine(oldX - size, oldY + j, currentX - size, currentY + j);
                    graphics2D.drawLine(oldX - size, oldY - j, currentX + size, currentY + j);
                    graphics2D.drawLine(oldX - size, oldY + j, currentX + size, currentY + j);
                    graphics2D.drawLine(oldX + size, oldY - j, currentX - size, currentY - j);
                    graphics2D.drawLine(oldX + size, oldY - j, currentX - size, currentY + j);
                    graphics2D.drawLine(oldX + size, oldY - j, currentX + size, currentY - j);
                    graphics2D.drawLine(oldX + size, oldY - j, currentX + size, currentY + j);
                    graphics2D.drawLine(oldX + size, oldY + j, currentX - size, currentY - j);
                    graphics2D.drawLine(oldX + size, oldY + j, currentX - size, currentY + j);
                    graphics2D.drawLine(oldX + size, oldY + j, currentX + size, currentY - j);
                    graphics2D.drawLine(oldX + size, oldY + j, currentX + size, currentY + j);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorController) {
            this.primaryColor = ((ColorController) o).getPrimaryColor();
            this.secondaryColor = ((ColorController) o).getSecondaryColor();
        }
    }
}