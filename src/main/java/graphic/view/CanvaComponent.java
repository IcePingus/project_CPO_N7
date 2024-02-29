package graphic.view;

import graphic.controller.CanvaController;
import graphic.controller.ToolController;
import graphic.model.ShapeTypes;
import graphic.model.ToolContext;
import graphic.model.tools.MoveTool;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * The Canva class represents a canvas component that allows drawing shapes and images.
 * It extends the JComponent class and provides methods for managing image states,
 * handling user input events, and rendering graphics on the canvas.
 *
 * @author Team 3
 */
public class CanvaComponent extends JComponent implements Observer {

    private Graphics2D g2;
    private Graphics g;
    private int currentX, currentY, oldX, oldY;
    private boolean isFirstPoint;
    private double zoom;
    private int zoomPointX;
    private int zoomPointY;

    private ShapeTypes shapeType = ShapeTypes.RECTANGLE;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private Color color;
    private final JLabel canvaSizeLabel;
    private boolean isShapeFilled = false;

    private final ToolController toolController;

    private final ToolContext context;
    private final CanvaController canvaController;

    /**
     * Constructs a new Canva object with the specified toolbox.
     *
     * @param toolbox        the toolbox associated with the canvas
     * @param canvaSizeLabel label for the current size of the canva
     * @param zoomLabel      label for the current zoom
     */
    public CanvaComponent(Toolbox toolbox, CanvaController canvaController, JLabel canvaSizeLabel, JLabel zoomLabel) {
        // Initialiser les variables
        this.toolController = new ToolController(toolbox);
        this.zoom = 1.0;
        this.isFirstPoint = true;
        this.context = new ToolContext();
        this.canvaController = canvaController;

        // Configurer le composant
        this.setDoubleBuffered(false);
        this.requestFocusInWindow();
        this.canvaSizeLabel = canvaSizeLabel;

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // Sauvegarder les coordonnées lors d'un clic de souris
                double doubleOldX = e.getX() / zoom + (canvaController.getCanva().getBufferedImage().getWidth() - getWidth() / zoom) / 2;
                oldX = (int) doubleOldX;
                double doubleOldY = e.getY() / zoom + (canvaController.getCanva().getBufferedImage().getHeight() - getHeight() / zoom) / 2;
                oldY = (int) doubleOldY;
                currentX = oldX;
                currentY = oldY;
                isFirstPoint = true;

                // Instancier une nouvelle bufferedImage et l'ajouter dans le tableau de bufferedImage
                canvaController.getCanva().nextBufferedImage();

                // Changer le curseur si l'outil actif est moveTool
                if (toolController.getActiveTool() instanceof MoveTool) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                // Execute l'outil actif
                context.setOldX(oldX);
                context.setOldY(oldY);
                context.setCurrentX(currentX);
                context.setCurrentY(currentY);
                context.setClick(e.getModifiersEx());
                context.setSize(toolController.getToolSize());
                context.setSquare(toolController.getIsSquareShape());
                context.setFirstPoint(isFirstPoint);
                context.setCanva(canvaController.getCanva());
                context.setCanvaComponent(CanvaComponent.this);
                toolController.getActiveTool().execute(context);

                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Changer le curseur si l'outil actif est moveTool
                if (toolController.getActiveTool() instanceof MoveTool) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                // Si une forme est dessinée, la construire sur la buffered image (voir paintComponent(Graphics g) pour plus de détails).
                if (shapeType != null) {
                    canvaController.getCanva().getGraphics2D().setPaint(color);
                    switch (shapeType) {
                        case RECTANGLE -> {
                            if (isShapeFilled) {
                                canvaController.getCanva().getGraphics2D().fillRect(startX, startY, endX, endY);
                                repaint();
                            } else {
                                if (toolController.getToolSize() > endX || toolController.getToolSize() > endY) {
                                    canvaController.getCanva().getGraphics2D().fillRect(startX, startY, endX, endY);
                                } else {
                                    canvaController.getCanva().getGraphics2D().fillRect(startX, startY, toolController.getToolSize(), endY);
                                    canvaController.getCanva().getGraphics2D().fillRect(startX, startY, endX, toolController.getToolSize());
                                    canvaController.getCanva().getGraphics2D().fillRect(startX + endX - toolController.getToolSize(), startY, toolController.getToolSize(), endY);
                                    canvaController.getCanva().getGraphics2D().fillRect(startX, startY + endY - toolController.getToolSize(), endX, toolController.getToolSize());
                                }
                            }
                        }
                        case OVAL -> {
                            if (isShapeFilled) {
                                canvaController.getCanva().getGraphics2D().fillOval(startX, startY, endX, endY);
                            } else {
                                canvaController.getCanva().getGraphics2D().drawOval(startX, startY, endX, endY);
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
                                canvaController.getCanva().getGraphics2D().fillOval(startX - toolController.getToolSize() / 2, startY - toolController.getToolSize() / 2, toolController.getToolSize(), toolController.getToolSize());

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

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Sauvegarde les coordonnées actuelles
                double doubleCurrentX = e.getX() / zoom + (canvaController.getCanva().getBufferedImage().getWidth() - getWidth() / zoom) / 2;
                currentX = (int) doubleCurrentX;
                double doubleCurrentY = e.getY() / zoom + (canvaController.getCanva().getBufferedImage().getHeight() - getHeight() / zoom) / 2;
                currentY = (int) doubleCurrentY;

                if (g2 != null) {
                    // Execute l'outil actif
                    context.setOldX(oldX);
                    context.setOldY(oldY);
                    context.setCurrentX(currentX);
                    context.setCurrentY(currentY);
                    context.setClick(e.getModifiersEx());
                    context.setSize(toolController.getToolSize());
                    context.setSquare(toolController.getIsSquareShape());
                    context.setFirstPoint(isFirstPoint);
                    context.setCanva(canvaController.getCanva());
                    context.setCanvaComponent(CanvaComponent.this);
                    toolController.getActiveTool().execute(context);
                    if (isFirstPoint) isFirstPoint = false;
                    oldX = currentX;
                    oldY = currentY;
                }
                repaint();
            }
        });

        this.addMouseWheelListener(e -> {
            // Ajuster le zoom quand on scroll
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
     * Overrides the paintComponent method to handle the painting of the canvas.
     *
     * @param g the Graphics object
     */
    protected void paintComponent(Graphics g) {
        this.g = g;
        // Vérifier si la bufferedImage est null ou si aucune bufferedImage est présent dans la liste
        if (this.canvaController.getCanva().getImageStates().size() == 0 || this.canvaController.getCanva().getImageStates().get(this.canvaController.getCanva().getCurrentIndex()) == null) {
            // Initialise la toile
            this.canvaController.getCanva().getImageStates().add(this.canvaController.getCanva().getCurrentIndex(), new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB));
            this.zoom = 0.75;
            this.zoomPointX = getWidth() / 2;
            this.zoomPointY = getHeight() / 2;
            this.g2 = (Graphics2D) this.canvaController.getCanva().getImageStates().get(this.canvaController.getCanva().getCurrentIndex()).getGraphics();
            this.canvaSizeLabel.setText(this.canvaController.getCanva().getBufferedImage().getWidth() + "x" + this.canvaController.getCanva().getBufferedImage().getHeight());

            this.g2.setPaint(Color.WHITE);
            this.g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            this.repaint();
        }
        // Applique le niveau de zoom
        AffineTransform at = ((Graphics2D) g).getTransform();
        at.translate(zoomPointX, zoomPointY);
        at.scale(zoom, zoom);
        at.translate(-zoomPointX, -zoomPointY);
        ((Graphics2D) g).setTransform(at);

        // Dessiner le fond transparent
        BufferedImage transparentBackground = new BufferedImage(this.canvaController.getCanva().getImageStates().get(this.canvaController.getCanva().getCurrentIndex()).getWidth(), this.canvaController.getCanva().getImageStates().get(this.canvaController.getCanva().getCurrentIndex()).getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D backgroundG2 = (Graphics2D) transparentBackground.getGraphics();
        int size = 1 + (int) ((double) (48 - 1) / (5000 - 16) * (Math.min(transparentBackground.getWidth(), transparentBackground.getHeight()) - 16));
        for (int row = 0; row < transparentBackground.getHeight() / size; row++) {
            for (int col = 0; col < transparentBackground.getWidth() / size; col++) {
                int x = col * size;
                int y = row * size;

                // Alterner entre gris et blanc
                Color color = (row + col) % 2 == 0 ? Color.WHITE : Color.lightGray;
                backgroundG2.setColor(color);
                backgroundG2.fillRect(x, y, size, size);
            }
        }
        g.drawImage(transparentBackground, ((this.getWidth() - transparentBackground.getWidth()) / 2), ((this.getHeight() - transparentBackground.getHeight()) / 2), null);


        // Dessiner la bufferedImage sur la toile
        g.drawImage(this.canvaController.getCanva().getImageStates().get(this.canvaController.getCanva().getCurrentIndex()), ((this.getWidth() - this.canvaController.getCanva().getImageStates().get(this.canvaController.getCanva().getCurrentIndex()).getWidth()) / 2), ((this.getHeight() - this.canvaController.getCanva().getImageStates().get(this.canvaController.getCanva().getCurrentIndex()).getHeight()) / 2), null);

        // Si une forme est en train d'être dessinée, la dessiner sur le panel
        if (!isFirstPoint && shapeType != null) {
            // Couleur de dessin
            this.g.setColor(this.color);
            // Choisir la forme à dessiner en fonction de son type
            switch (shapeType) {
                case RECTANGLE -> {
                    if (isShapeFilled) {
                        // Forme remplie si l'option est activée
                        g.fillRect(startX, startY, endX, endY);
                    } else {
                        if (this.toolController.getToolSize() > endX || this.toolController.getToolSize() > endY) {
                            /* Forme remplie si l'option bordure est activée, mais que la taille du trait est plus
                               grande que la distance entre le point de départ et de fin */
                            g.fillRect(startX, startY, endX, endY);
                        } else {
                            /* Forme remplie si l'option bordure est activée, mais que la taille du trait est plus
                               grande que la distance entre le point de départ et de fin */
                            g.fillRect(startX, startY, toolController.getToolSize(), endY);
                            g.fillRect(startX, startY, endX, toolController.getToolSize());
                            g.fillRect(startX + endX - toolController.getToolSize(), startY, toolController.getToolSize(), endY);
                            g.fillRect(startX, startY + endY - toolController.getToolSize(), endX, toolController.getToolSize());
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
                    // Utiliser l'algorithme Bresenham pour calculer tous les points de la ligne et faire un cercle de la taille de l'outil pour remplir la ligne
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
                        g.fillOval(startX2 - toolController.getToolSize() / 2, startY2 - toolController.getToolSize() / 2, toolController.getToolSize(), toolController.getToolSize());

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
     * @param shapeType     the type of shape
     * @param startX        the starting X-coordinate
     * @param startY        the starting Y-coordinate
     * @param endX          the ending X-coordinate
     * @param endY          the ending Y-coordinate
     * @param color         the color of the shape
     * @param isFilledShape indicates if the shape should be filled
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

    @Override
    public void update(Observable o, Object arg) {
        this.validate();
        this.repaint();
        this.canvaSizeLabel.setText(this.canvaController.getCanva().getBufferedImage().getWidth() + "x" + this.canvaController.getCanva().getBufferedImage().getHeight());
    }
}