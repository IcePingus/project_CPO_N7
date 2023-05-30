package graphic.model.tools;

import graphic.model.canva.Pixel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class RubberTool implements ToolCommand {
    private String name;
    private Icon image;

    public RubberTool() {
        this.name = "Rubber";
        this.image = new ImageIcon(getClass().getResource("/assets/images/rubber.png"));
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
        pixel.setBackground(Color.WHITE);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}