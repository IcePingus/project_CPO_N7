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
    private final boolean isSquareRoundShape;
    private final boolean hasShapeSelection;
    private Color primaryColor;
    private Color secondaryColor;

    public PencilTool() {
        this.name = "Pencil";
        this.image = new ImageIcon(getClass().getResource("/assets/images/pencil.png"));
        this.isResizable = true;
        this.isSquareRoundShape = true;
        this.hasShapeSelection = false;
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

            int distanceX = Math.abs(currentX - oldX);
            int distanceY = Math.abs(currentY - oldY);
            int directionX = oldX < currentX ? 1 : -1;
            int directionY = oldY < currentY ? 1 : -1;
            int erreur = distanceX - distanceY;
            int erreur2;

            while (oldX != currentX || oldY != currentY) {

                if (square) {
                    graphics2D.fillRect(oldX - size / 2, oldY - size / 2, size, size);
                } else {
                    graphics2D.fillOval(oldX - size / 2, oldY - size / 2, size, size);
                }

                erreur2 = 2 * erreur;
                if (erreur2 > -distanceY) {
                    erreur -= distanceY;
                    oldX += directionX;
                }
                if (erreur2 < distanceX) {
                    erreur += distanceX;
                    oldY += directionY;
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