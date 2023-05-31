package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class RubberTool implements ToolCommand {
    private final String name;
    private final Icon image;
    private final boolean isResizable;

    public RubberTool() {
        this.name = "Rubber";
        this.image = new ImageIcon(getClass().getResource("/assets/images/rubber.png"));
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
        graphics2D.setPaint(Color.WHITE);
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

    @Override
    public void update(Observable o, Object arg) { }
}