package graphic.model.tools;

import graphic.controller.ColorController;
import graphic.model.canva.Pixel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class PencilTool implements ToolCommand {

    private String name;
    private Icon image;

    private Color activeColor;

    public PencilTool() {
        this.name = "Pencil";
        this.image = new ImageIcon(getClass().getResource("/assets/images/pencil.png"));
        this.activeColor = Color.BLACK;
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
        pixel.setBackground(this.activeColor);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorController) {
            this.activeColor = ((ColorController) o).getActiveColor();
        }
    }
}