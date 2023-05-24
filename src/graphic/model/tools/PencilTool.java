package graphic.model.tools;

import javax.swing.*;

public class PencilTool implements ToolCommand {

    private String name;
    private Icon image;

    public PencilTool() {
        this.name = "Pencil";
        this.image = new ImageIcon(getClass().getResource("/assets/images/pencil.png"));
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