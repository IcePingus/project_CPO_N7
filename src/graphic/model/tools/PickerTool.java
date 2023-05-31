package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class PickerTool implements ToolCommand {

    private final String name;
    private final Icon image;

    public PickerTool() {
        this.name = "Picker";
        this.image = new ImageIcon(getClass().getResource("/assets/images/picker.png"));
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
    public void execute(int oldX, int oldY, int currentX, int currentY, Graphics2D graphics2D, int click) {
        System.out.println("Salut");
    }

    @Override
    public void update(Observable o, Object arg) { }
}