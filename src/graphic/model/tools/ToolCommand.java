package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observer;

public interface ToolCommand extends Observer {
    String name = null;
    Icon image = null;

    default String getName() {
        return this.name;
    }

    default Icon getImage() {
        return this.image;
    }

    void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click);
}
