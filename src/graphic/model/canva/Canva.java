package graphic.model.canva;

import graphic.model.ShapeTypes;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

/**
 * The Canva class represents a canvas component that allows drawing shapes and images.
 * It extends the JComponent class and provides methods for managing image states,
 * handling user input events, and rendering graphics on the canvas.
 *
 * @author Team 3
 */
public class Canva extends JComponent {

    private List<BufferedImage> imageStates;
    private int currentIndex;
    private Graphics2D g2;
    private Graphics g;
    private int currentX, currentY, oldX, oldY;
    private boolean isFirstPoint;
    private double zoom = 1;
    private int zoomPointX;
    private int zoomPointY;

    private ShapeTypes shapeType = ShapeTypes.RECTANGLE;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private Color color;
    private JLabel canvaSizeLabel;
    private boolean isShapeFilled = false;

    private Toolbox toolbox;

    /**
     * Constructs a new Canva object with the specified toolbox.
     *
     * @param toolbox the toolbox associated with the canvas
     * @param canvaSizeLabel label for the current size of the canva
     * @param zoomLabel label for the current zoom
     */
    public Canva(Toolbox toolbox, JLabel canvaSizeLabel, JLabel zoomLabel) {
        // Initialize variables
        this.toolbox = toolbox;
        this.imageStates = new ArrayList<>();
        this.currentIndex = 0;
        this.zoom = 1.0;
        this.isFirstPoint = true;

        // Configure component
        this.setDoubleBuffered(false);
        this.requestFocusInWindow();
        this.canvaSizeLabel = canvaSizeLabel;

        // Add mouse listener
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // Save coordinates when mouse is pressed
                double doubleOldX = e.getX() / zoom + (getBufferedImage().getWidth() - getWidth() / zoom) / 2;
                oldX = (int) doubleOldX;
                double doubleOldY = e.getY() / zoom + (getBufferedImage().getHeight() - getHeight() / zoom) / 2;
                oldY = (int) doubleOldY;
                currentX = oldX;
                currentY = oldY;
                isFirstPoint = true;

                BufferedImage newImage = nextBufferedImage();

                // Execute active tool
                toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, newImage, g2, e.getModifiersEx(), toolbox.getToolSize(), toolbox.getIsSquareShape(), isFirstPoint, Canva.this);

                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (shapeType != null) {
                    g2.setColor(color);
                    switch (shapeType) {
                        case RECTANGLE -> {
                            if (isShapeFilled) {
                                g2.fillRect(startX, startY, endX, endY);
                            } else {
                                if (toolbox.getToolSize() > endX || toolbox.getToolSize() > endY) {
                                    g2.fillRect(startX, startY, endX, endY);
                                } else {
                                    for (int i = 0; i < toolbox.getToolSize(); i++) {
                                        g2.fillRect(startX, startY, toolbox.getToolSize(), endY);
                                        g2.fillRect(startX, startY, endX, toolbox.getToolSize());
                                        g2.fillRect(startX + endX - toolbox.getToolSize(), startY, toolbox.getToolSize(), endY);
                                        g2.fillRect(startX, startY + endY - toolbox.getToolSize(), endX, toolbox.getToolSize());
                                    }
                                }
                            }
                        }
                        case OVAL -> {
                            if (isShapeFilled) {
                                g2.fillOval(startX, startY, endX, endY);
                            } else {
                                g2.drawOval(startX, startY, endX, endY);
                            }
                        }
                        case LINE -> {
                            int distanceX = Math.abs(endX - startX);
                            int distanceY = Math.abs(endY - startY);
                            int directionX = startX < endX ? 1 : -1;
                            int directionY = startY < endY ? 1 : -1;
                            int erreur = distanceX - distanceY;
                            int erreur2;

                            while (startX != endX || startY != endY) {
                                g2.fillOval(startX - toolbox.getToolSize() / 2, startY - toolbox.getToolSize() / 2, toolbox.getToolSize(), toolbox.getToolSize());

                                erreur2 = 2 * erreur;
                                if (erreur2 > -distanceY) {
                                    erreur -= distanceY;
                                    startX += directionX;
                                }
                                if (erreur2 < distanceX) {
                                    erreur += distanceX;
                                    startY += directionY;
                                }
                            }
                        }
                    }
                    paintComponent(g);
                    shapeType = null;
                }
                repaint();
            }
        });

        // Add mouse motion listener
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double doubleCurrentX = e.getX() / zoom + (getBufferedImage().getWidth() - getWidth() / zoom) / 2;
                currentX = (int) doubleCurrentX;
                double doubleCurrentY = e.getY() / zoom + (getBufferedImage().getHeight() - getHeight() / zoom) / 2;
                currentY = (int) doubleCurrentY;

                if (g2 != null) {
                    // Execute active tool
                    toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, imageStates.get(currentIndex), g2, e.getModifiersEx(), toolbox.getToolSize(), toolbox.getIsSquareShape(), isFirstPoint, Canva.this);
                    if (isFirstPoint) isFirstPoint = false;
                    oldX = currentX;
                    oldY = currentY;
                }
                repaint();
            }
        });

        // Add mouse wheel listener
        this.addMouseWheelListener(e -> {
            zoomPointX = getWidth() / 2;
            zoomPointY = getHeight() / 2;
            if (e.getPreciseWheelRotation() < 0) {
                zoom += (double) getHeight() / 8000;
            } else {
                zoom -= (double) getHeight() / 8000;
            }
            if (zoom > 24.75) {
                zoom = 24.75;
            }
            if (zoom < 0.25) {
                zoom = 0.25;
            }
            zoomLabel.setText(" - Zoom : " + Math.round((zoom + 0.25) * 100.0) / 100.0 + "x");
            repaint();
        });
    }

    /**
     * Creates a copy of the specified BufferedImage.
     *
     * @param source the BufferedImage to copy
     * @return the copied BufferedImage
     */
    private BufferedImage copyBufferedImage(BufferedImage source) {
        ColorModel cm = source.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = source.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * Creates a new BufferedImage based on the current state and sets it as the active image.
     *
     * @return the new BufferedImage
     */
    public BufferedImage nextBufferedImage() {
        BufferedImage newImage = copyBufferedImage(this.imageStates.get(this.currentIndex));
        this.setBufferedImage(newImage);
        return newImage;
    }

    /**
     * Returns the current BufferedImage.
     *
     * @return the current BufferedImage
     */
    public BufferedImage getBufferedImage() {
        return this.imageStates.get(currentIndex);
    }

    /**
     * Returns the current index of the buffered image.
     *
     * @return the current index
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Returns the Graphics2D object associated with the canvas.
     *
     * @return the Graphics2D object
     */
    public Graphics2D getG2() {
        return this.g2;
    }

    /**
     * Returns the list of image states.
     *
     * @return the list of image states
     */
    public List<BufferedImage> getImageStates() {
        return imageStates;
    }

    /**
     * Sets the specified buffered image as the current image state and updates the graphics object.
     *
     * @param bufferedImage the new buffered image
     */
    public void setBufferedImage(BufferedImage bufferedImage) {
        if (this.imageStates.size() == 0) {
            this.imageStates.add(bufferedImage);
        } else {
            this.currentIndex++;
            this.imageStates.add(this.currentIndex, bufferedImage);
            if (this.imageStates.size() > this.currentIndex + 1) {
                this.imageStates.subList(this.currentIndex + 1, this.imageStates.size()).clear();
            }
            this.canvaSizeLabel.setText(this.getBufferedImage().getWidth() + "x" + this.getBufferedImage().getHeight());
            this.g2 = (Graphics2D) bufferedImage.getGraphics();
            this.repaint();
        }
    }

    /**
     * Sets the specified Graphics2D object as the current graphics object.
     *
     * @param g2 the Graphics2D object
     */
    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    /**
     * Sets the current index of the buffered image.
     *
     * @param currentIndex the current index
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        this.canvaSizeLabel.setText(this.getBufferedImage().getWidth() + "x" + this.getBufferedImage().getHeight());
    }

    /**
     * Overrides the paintComponent method to handle the painting of the canvas.
     *
     * @param g the Graphics object
     */
    protected void paintComponent(Graphics g) {
        this.g = g;
        if (this.imageStates.size() == 0 || this.imageStates.get(this.currentIndex) == null) {
            this.imageStates.add(this.currentIndex, new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB));
            this.zoom = 0.75;
            this.zoomPointX = getWidth() / 2;
            this.zoomPointY = getHeight() / 2;
            this.g2 = (Graphics2D) this.imageStates.get(this.currentIndex).getGraphics();
            this.canvaSizeLabel.setText(this.getBufferedImage().getWidth() + "x" + this.getBufferedImage().getHeight());

            this.g2.setPaint(Color.WHITE);
            this.g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            this.repaint();
        }
        AffineTransform at = ((Graphics2D) g).getTransform();
        at.translate(zoomPointX, zoomPointY);
        at.scale(zoom, zoom);
        at.translate(-zoomPointX, -zoomPointY);
        ((Graphics2D) g).setTransform(at);

        g.drawImage(this.imageStates.get(this.currentIndex), ((this.getWidth() - this.imageStates.get(this.currentIndex).getWidth()) / 2), ((this.getHeight() - this.imageStates.get(this.currentIndex).getHeight()) / 2), null);

        if (!isFirstPoint && shapeType != null) {
            this.g.setColor(this.color);
            switch (shapeType) {
                case RECTANGLE -> {
                    if (isShapeFilled) {
                        g.fillRect(startX, startY, endX, endY);
                    } else {
                        if (this.toolbox.getToolSize() > endX || this.toolbox.getToolSize() > endY) {
                            g.fillRect(startX, startY, endX, endY);
                        } else {
                            for (int i = 0; i < this.toolbox.getToolSize(); i++) {
                                g.fillRect(startX, startY, toolbox.getToolSize(), endY);
                                g.fillRect(startX, startY, endX, toolbox.getToolSize());
                                g.fillRect(startX + endX - toolbox.getToolSize(), startY, toolbox.getToolSize(), endY);
                                g.fillRect(startX, startY + endY - toolbox.getToolSize(), endX, toolbox.getToolSize());
                            }
                        }
                    }
                }
                case OVAL -> {
                    if (isShapeFilled) {
                        g.fillOval(startX, startY, endX, endY);
                    } else {
                        g.drawOval(startX, startY, endX, endY);
                    }
                }
                case LINE -> {
                    int startX2 = startX;
                    int startY2 = startY;
                    int endX2 = endX;
                    int endY2 = endY;
                    int distanceX = Math.abs(endX2 - startX2);
                    int distanceY = Math.abs(endY2 - startY2);
                    int directionX = startX2 < endX2 ? 1 : -1;
                    int directionY = startY2 < endY2 ? 1 : -1;
                    int erreur = distanceX - distanceY;
                    int erreur2;

                    while (startX2 != endX2 || startY2 != endY2) {
                        g.fillOval(startX2 - toolbox.getToolSize() / 2, startY2 - toolbox.getToolSize() / 2, toolbox.getToolSize(), toolbox.getToolSize());

                        erreur2 = 2 * erreur;
                        if (erreur2 > -distanceY) {
                            erreur -= distanceY;
                            startX2 += directionX;
                        }
                        if (erreur2 < distanceX) {
                            erreur += distanceX;
                            startY2 += directionY;
                        }
                    }
                }
            }
        }
    }

    /**
     * Repaints the component with the specified shape type, coordinates, color, and shape filling.
     *
     * @param shapeType      the type of shape
     * @param startX         the starting X-coordinate
     * @param startY         the starting Y-coordinate
     * @param endX           the ending X-coordinate
     * @param endY           the ending Y-coordinate
     * @param color          the color of the shape
     * @param isFilledShape  indicates if the shape should be filled
     */
    public void repaintComponent(ShapeTypes shapeType, int startX, int startY, int endX, int endY, Color color, boolean isFilledShape) {
        this.shapeType = shapeType;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.isShapeFilled = isFilledShape;
    }
}