package graphic.model.tools;

import graphic.model.ToolContext;
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
        this.image = new ImageIcon(getClass().getResource("/images/bucket.png"));
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
     * @param context          the application context
     */
    @Override
    public void execute(ToolContext context) {
        // Récupérer la couleur en fonction du type de clic
        Color color = null;
        if (context.getClick() == InputEvent.BUTTON1_DOWN_MASK) {
            color = primaryColor;
        } else if (context.getClick() == InputEvent.BUTTON3_DOWN_MASK) {
            color = secondaryColor;
        }
        try {
            /* Exécuter la méthode fill si la couleur n'est pas nulle et que la couleur du pixel cliqué est différente
               de la couleur à définir */
            if (color != null && color.getRGB() != context.getCanva().getBufferedImage().getRGB(context.getOldX(), context.getOldY())) {
                fill(context.getOldX(), context.getOldY(), context.getCanva().getBufferedImage(), context.getCanva().getBufferedImage().getRGB(context.getOldX(), context.getOldY()), color.getRGB());
            }
        } catch (Exception e) {
            // !(Salut Bucket !)
            System.out.println("Au revoir Bucket !");
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


        // Instancier la liste de pixel à traiter et y ajouter le pixel cliqué
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY));

        // Tant que la liste n'est pas vide
        while (!queue.isEmpty()) {
            // Récupérer le premier point et le retirer de la liste
            Point point = queue.poll();
            int x = (int) point.getX();
            int y = (int) point.getY();

            // Si le point n'est pas dans la bufferedImage, passer à la prochaine itération du tant que
            if (x < 0 || x >= bufferedImage.getWidth() || y < 0 || y >= bufferedImage.getHeight()) {
                continue;
            }

            // Récupérer le code de la couleur du pixel traité
            int currentColor = bufferedImage.getRGB(x, y);
            // Si la couleur est différente de la couleur du pixel cliqué, passer à la prochaine itération du tant que
            if (currentColor != oldColorRGB) {
                continue;
            }

            // Définir la couleur du pixel traité à la nouvelle couleur
            bufferedImage.setRGB(x, y, newColorRGB);

            // Ajouter les points adjacents au point traité à la queue
            queue.add(new Point(x - 1, y));
            queue.add(new Point(x + 1, y));
            queue.add(new Point(x, y - 1));
            queue.add(new Point(x, y + 1));
        }
    }
}