package graphic.model.tools;

import graphic.model.canva.Pixel;

import javax.swing.*;
import java.util.Observer;

public interface ToolCommand extends Observer {
    String name = null;
    Icon image = null;

    default String getName() {
        return this.name;
    }

    default Icon getImage() {
        return this.getImage();
    }

    default void execute(Pixel pixel) {
    }
}
