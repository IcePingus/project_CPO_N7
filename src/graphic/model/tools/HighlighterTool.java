package graphic.model.tools;

import graphic.model.color.ColorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class HighlighterTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;
    private final boolean hasShapeSelection;
    private Color primaryColor;
    private Color secondaryColor;

    public HighlighterTool() {
        this.name = "Highlighter";
        this.image = new ImageIcon(getClass().getResource("/assets/images/highlighter.png"));
        this.isResizable = true;
        this.isSquareRoundShape = false;
        this.hasShapeSelection = false;
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
    public boolean getIsResizable() {
        return this.isResizable;
    }

    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    @Override
    public boolean getHasShapeSelection() {
        return this.hasShapeSelection;
    }

    @Override
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint, JComponent canva) {
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