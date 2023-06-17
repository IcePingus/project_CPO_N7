package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class MoveTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;

    public MoveTool() {
        this.name = "Move";
        this.image = new ImageIcon(getClass().getResource("/assets/images/move.png"));
        this.isResizable = false;
        this.isSquareRoundShape = false;
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
        return false;
    }

    @Override
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint, JComponent jComponent) {
        int mouseDeltaX = currentX - oldX;
        int mouseDeltaY = currentY - oldY;

        int sensitivity = 5;
        double deltaX = (double) mouseDeltaX / sensitivity;
        double deltaY = (double) mouseDeltaY / sensitivity;

        int newX = jComponent.getLocation().x + (int) deltaX;
        int newY = jComponent.getLocation().y + (int) deltaY;

        jComponent.setLocation(newX, newY);

        jComponent.repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}