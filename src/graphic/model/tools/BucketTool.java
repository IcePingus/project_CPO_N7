package graphic.model.tools;

import graphic.model.color.ColorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * The BucketTool class represents a bucket tool in a graphics application.
 * It implements the ToolCommand interface and provides functionality for filling a region with a selected color.
 *
 * @author Team 3
 */
public class BucketTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;
    private final boolean hasShapeSelection;
    private Color primaryColor;
    private Color secondaryColor;

    /**
     * Constructs a BucketTool object with default settings.
     * It initializes the name, image, and sets the tool properties.
     * The primary color and secondary color are initially set to black and white, respectively.
     */
    public BucketTool() {
        this.name = "Bucket";
        this.image = new ImageIcon(getClass().getResource("/assets/images/bucket.png"));
        this.isResizable = false;
        this.isSquareRoundShape = false;
        this.hasShapeSelection = false;
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
    }

    /**
     * Returns the name of the bucket tool.
     *
     * @return the name of the tool
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the image icon representing the bucket tool.
     *
     * @return the image icon of the tool
     */
    @Override
    public Icon getImage() {
        return this.image;
    }

    /**
     * Returns whether the bucket tool is resizable.
     *
     * @return true if the tool is resizable, false otherwise
     */
    @Override
    public boolean getIsResizable() {
        return this.isResizable;
    }

    /**
     * Returns whether the bucket tool is square or round in shape.
     *
     * @return true if the tool is square or round in shape, false otherwise
     */
    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    /**
     * Returns whether the bucket tool requires shape selection.
     *
     * @return true if the tool requires shape selection, false otherwise
     */
    @Override
    public boolean getHasShapeSelection() {
        return this.hasShapeSelection;
    }

    /**
     * Executes the bucket tool operation.
     * It fills the region with the selected color based on the click event and the colors set in the color model.
     *
     * @param oldX          the x-coordinate of the initial point
     * @param oldY          the y-coordinate of the initial point
     * @param currentX      the current x-coordinate
     * @param currentY      the current y-coordinate
     * @param bufferedImage the buffered image
     * @param graphics2D    the graphics context
     * @param click         the click event
     * @param size          the tool size
     * @param square        the flag indicating whether the shape should be square
     * @param isFirstPoint  the flag indicating whether it is the first point
     * @param canva         the canvas component
     */
    @Override
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, boolean isFirstPoint, JComponent canva) {
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

    /**
     * Updates the bucket tool based on changes in the color model.
     *
     * @param o   the observed object
     * @param arg the argument passed by the observed object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorModel) {
            this.primaryColor = ((ColorModel) o).getPrimaryColor();
            this.secondaryColor = ((ColorModel) o).getSecondaryColor();
        }
    }

    /**
     * Fills the region starting from the specified coordinates with the new color.
     *
     * @param startX        the x-coordinate of the starting point
     * @param startY        the y-coordinate of the starting point
     * @param bufferedImage the buffered image
     * @param oldColorRGB   the RGB value of the old color
     * @param newColorRGB   the RGB value of the new color
     */
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