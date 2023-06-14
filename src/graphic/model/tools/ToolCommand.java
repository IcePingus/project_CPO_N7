package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observer;

public interface ToolCommand extends Observer {
    String getName();

    Icon getImage();

    boolean getIsResizable();

    boolean getIsSquareRoundShape();

    boolean getHasShapeSelection();

    void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint, JComponent canva);
}
