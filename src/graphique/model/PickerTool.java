package graphique.model;
import javax.swing.*;

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
    public void execute() {
        System.out.println("Salut");
    }
}