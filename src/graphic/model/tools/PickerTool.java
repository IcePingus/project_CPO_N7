package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class PickerTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;

    public PickerTool() {
        this.name = "Picker";
        this.image = new ImageIcon(getClass().getResource("/assets/images/picker.png"));
        this.isResizable = false;
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
        System.out.println("Salut je suis une pipette");
    }

    @Override
    public void update(Observable o, Object arg) { }
}