package graphic.model.tools;

import graphic.controller.ColorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class HighlighterTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private Color primaryColor;
    private Color secondaryColor;

    public HighlighterTool() {
        this.name = "Highlighter";
        this.image = new ImageIcon(getClass().getResource("/assets/images/highlighter.png"));
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
        this.isResizable = true;
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
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size) {
        Color color = null;
        if (click == InputEvent.BUTTON1_DOWN_MASK) {
            color = new Color(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), 17);
        } else if (click == InputEvent.BUTTON3_DOWN_MASK) {
            color = new Color(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue(), 17);
        }
        if (color != null) {
            graphics2D.setPaint(color);
            graphics2D.drawLine(oldX, oldY, currentX, currentY);
            for (int i = 1; i < size; i++) {
                graphics2D.drawLine(oldX+i, oldY+i, currentX+i, currentY+i);
                graphics2D.drawLine(oldX-i, oldY-i, currentX-i, currentY-i);
            }
        }
    }

        @Override
        public void update(Observable o, Object arg) {
            if (o instanceof ColorModel) {
                this.primaryColor = ((ColorModel) o).getPrimaryColor();
                this.secondaryColor = ((ColorModel) o).getSecondaryColor();
            }
        }
    }