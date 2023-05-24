package graphique.model;

import javax.swing.*;
import javax.tools.Tool;

public interface ToolCommand {
    String name = null;
    Icon image = null;

    default String getName() {
        return this.name;
    }

    default Icon getImage() {
        return this.getImage();
    }

    default void execute() {
    }
}
