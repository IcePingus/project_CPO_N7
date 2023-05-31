package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;

public interface ToolCommand extends Observer {
    String name = null;
    Icon image = null;
    Boolean isResizable = true;

    default String getName() {
        return this.name;
    }

    default Icon getImage() {
        return this.image;
    }
    default boolean getIsResizable() { return this.isResizable; }

    void execute(int oldX, int oldY, int currentX, int currentY, Graphics2D graphics2D, int click, int size);
}
