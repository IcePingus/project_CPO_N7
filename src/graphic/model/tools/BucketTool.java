package graphic.model.tools;

import graphic.controller.ColorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.*;

public class BucketTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;

    private Color primaryColor;
    private Color secondaryColor;

    public BucketTool() {
        this.name = "Bucket";
        this.image = new ImageIcon(getClass().getResource("/assets/images/bucket.png"));
        this.isResizable = false;
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
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size) {
        Color color = null;
        if (click == InputEvent.BUTTON1_DOWN_MASK) {
            color = primaryColor;
        } else if (click == InputEvent.BUTTON3_DOWN_MASK) {
            color = secondaryColor;
        }
        if (color != null && color.getRGB() != bufferedImage.getRGB(oldX, oldY)) {
            fill(oldX, oldY, bufferedImage, bufferedImage.getRGB(oldX, oldY), color.getRGB());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorController) {
            this.primaryColor = ((ColorController) o).getPrimaryColor();
            this.secondaryColor = ((ColorController) o).getSecondaryColor();
        }
    }

    private void fill(int startX, int startY, BufferedImage bufferedImage, int oldColorRGB, int newColorRGB) {

        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY));

        while (!queue.isEmpty()) {
            Point point = queue.poll();
            int x = (int) point.getX();
            int y = (int) point.getY();

            if (x < 0 || x >= bufferedImage.getWidth() || y < 0 || y >= bufferedImage.getHeight()) {
                continue;
            }

            Color currentColor = new Color(bufferedImage.getRGB(x, y));
            if (currentColor.getRGB() != oldColorRGB) {
                continue;
            }

            bufferedImage.setRGB(x, y, newColorRGB);

            queue.add(new Point(x - 1, y));
            queue.add(new Point(x + 1, y));
            queue.add(new Point(x, y - 1));
            queue.add(new Point(x, y + 1));
        }
    }
}