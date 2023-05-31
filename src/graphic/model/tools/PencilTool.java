package graphic.model.tools;

import graphic.controller.ColorController;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class PencilTool implements ToolCommand {

    private final String name;
    private final Icon image;

    private Color primaryColor;
    private Color secondaryColor;

    public PencilTool() {
        this.name = "Pencil";
        this.image = new ImageIcon(getClass().getResource("/assets/images/pencil.png"));
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
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
        if (leftClick) {
            graphics2D.setPaint(this.primaryColor);
            graphics2D.drawLine(oldX, oldY, currentX, currentY);
            graphics2D.setPaint(this.primaryColor);
        } else {
            graphics2D.setPaint(this.secondaryColor);
            graphics2D.drawLine(oldX, oldY, currentX, currentY);
            graphics2D.setPaint(this.secondaryColor);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorController) {
            this.primaryColor = ((ColorController) o).getPrimaryColor();
            this.secondaryColor = ((ColorController) o).getSecondaryColor();
        }
    }
}