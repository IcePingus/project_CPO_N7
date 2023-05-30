package graphic.model.tools;

import graphic.model.canva.Pixel;

import javax.swing.*;
import java.util.Observable;

public class PickerTool implements ToolCommand {

    private String name;
    private Icon image;

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
    public void execute(Pixel pixel) {
        System.out.println("Salut");
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}