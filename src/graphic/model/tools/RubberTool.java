package graphic.model.tools;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

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
    public void execute(int oldX, int oldY, int currentX, int currentY, Graphics2D graphics2D, boolean leftClick) {
        graphics2D.drawLine(oldX, oldY, currentX, currentY);
        graphics2D.setPaint(Color.WHITE);
    }

    @Override
    public void update(Observable o, Object arg) { }
}