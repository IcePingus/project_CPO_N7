package graphic.model.tools;

import graphic.model.ToolContext;
import graphic.model.color.ColorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
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
    private final boolean isSquareRoundShape;
    private final boolean hasShapeSelection;
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
        this.isSquareRoundShape = false;
        this.hasShapeSelection = false;
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
     * @param context          the application context
     */
    @Override
    public void execute(ToolContext context) {
        // Récupérer la couleur et appliquer le niveau de transparence en fonction du type de clic
        Color color = null;
        if (context.getClick() == InputEvent.BUTTON1_DOWN_MASK) {
            color = new Color(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), 17);
        } else if (context.getClick() == InputEvent.BUTTON3_DOWN_MASK) {
            color = new Color(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue(), 17);
        }
        if (color != null) {
            context.getCanva().getG2().setPaint(color);
            context.getCanva().getG2().drawLine(context.getOldX(), context.getOldY(), context.getCurrentX(), context.getCurrentY());
            // Dessine des lignes sur la toile en fonction de la taille de l'outil
            for (int i = 1; i < context.getSize(); i++) {
                context.getCanva().getG2().drawLine(context.getOldX() + i, context.getOldY() + i, context.getCurrentX() + i, context.getCurrentY() + i);
                context.getCanva().getG2().drawLine(context.getOldX() - i, context.getOldY() - i, context.getCurrentX() - i, context.getCurrentY() - i);
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