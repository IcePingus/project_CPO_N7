package graphic.model.tools;

import javax.swing.*;
import java.awt.*;

public class RubberTool implements ToolCommand {
    private final String name;
    private final Icon image;

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
    public void execute(int oldX, int oldY, int currentX, int currentY, Graphics2D graphics2D) {
        //pixel.setBackground(Color.WHITE);
    }
}