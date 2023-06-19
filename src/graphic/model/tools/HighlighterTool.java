package graphic.model.tools;

import graphic.model.color.ColorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;

/**
 * The HighlighterTool class represents a highlighter tool in a graphics application.
 * It implements the ToolCommand interface and provides functionality for highlighting with a translucent color.
 *
 * @author Team 3
 */
public class HighlighterTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private boolean isSquareRoundShape;
    private boolean hasShapeSelection;
    private Color primaryColor;
    private Color secondaryColor;

    /**
     * Constructs a HighlighterTool object with default settings.
     * It initializes the name, image, and sets the tool properties.
     * The primary color and secondary color are initially set to black and white, respectively.
     */
    public HighlighterTool() {
        this.name = "Highlighter";
        this.image = new ImageIcon(getClass().getResource("/assets/images/highlighter.png"));
        // Options par défaut
        this.isResizable = true;
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
    }

    /**
     * Returns the name of the highlighter tool.
     *
     * @return the name of the tool
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the image icon representing the highlighter tool.
     *
     * @return the image icon of the tool
     */
    @Override
    public Icon getImage() {
        return this.image;
    }

    /**
     * Returns whether the highlighter tool is resizable.
     *
     * @return true if the tool is resizable, false otherwise
     */
    @Override
    public boolean getIsResizable() {
        return this.isResizable;
    }

    /**
     * Returns whether the highlighter tool is square or round in shape.
     *
     * @return true if the tool is square or round in shape, false otherwise
     */
    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    /**
     * Returns whether the highlighter tool requires shape selection.
     *
     * @return true if the tool requires shape selection, false otherwise
     */
    @Override
    public boolean getHasShapeSelection() {
        return this.hasShapeSelection;
    }

    /**
     * Executes the highlighter tool operation.
     * It draws a translucent line with the selected color based on the click event and the colors set in the color model.
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
        // Récupérer la couleur et appliquer le niveau de transparence en fonction du type de clic
        Color color = null;
        if (click == InputEvent.BUTTON1_DOWN_MASK) {
            color = new Color(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), 17);
        } else if (click == InputEvent.BUTTON3_DOWN_MASK) {
            color = new Color(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue(), 17);
        }
        if (color != null) {
            graphics2D.setPaint(color);
            graphics2D.drawLine(oldX, oldY, currentX, currentY);
            // Dessine des lignes sur la toile en fonction de la taille de l'outil
            for (int i = 1; i < size; i++) {
                graphics2D.drawLine(oldX + i, oldY + i, currentX + i, currentY + i);
                graphics2D.drawLine(oldX - i, oldY - i, currentX - i, currentY - i);
            }
        }
    }

    /**
     * Updates the highlighter tool based on changes in the color model.
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
}