package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class RubberTool implements ToolCommand {
    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;

    public RubberTool() {
        this.name = "Rubber";
        this.image = new ImageIcon(getClass().getResource("/assets/images/rubber.png"));
        this.isResizable = true;
        this.isSquareRoundShape = true;
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
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint, JComponent canva) {
        graphics2D.setPaint(Color.WHITE);
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

    @Override
    public void update(Observable o, Object arg) {
    }
}