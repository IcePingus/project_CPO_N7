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


    public Canva(Toolbox toolbox, JLabel canvaSizeLabel, JLabel zoomLabel) {
        this.imageStates = new ArrayList<>();
        this.currentIndex = 0;
        this.zoom = 1.0;
        this.isFirstPoint = true;
        this.setDoubleBuffered(false);
        this.requestFocusInWindow();
        this.canvaSizeLabel = canvaSizeLabel;
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                double doubleOldX = e.getX() / zoom + (getBufferedImage().getWidth() - getWidth() / zoom) / 2;
                oldX = (int) doubleOldX;
                double doubleOldY = e.getY() / zoom + (getBufferedImage().getHeight() - getHeight() / zoom) / 2;
                oldY = (int) doubleOldY;
                currentX = oldX;
                currentY = oldY;
                isFirstPoint = true;

                BufferedImage newImage = nextBufferedImage();

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
                                g2.drawRect(startX, startY, endX, endY);
                            }
                        }
                        case OVAL -> {
                            if (isShapeFilled) {
                                g2.fillOval(startX, startY, endX, endY);
                            } else {
                                g2.drawOval(startX, startY, endX, endY);
                            }
                        }
                        case LINE -> g2.drawLine(startX, startY, endX, endY);
                    }
                    paintComponent(g);
                    shapeType = null;
                }
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                double doubleCurrentX = e.getX() / zoom + (getBufferedImage().getWidth() - getWidth() / zoom) / 2;
                currentX = (int) doubleCurrentX;
                double doubleCurrentY = e.getY() / zoom + (getBufferedImage().getHeight() - getHeight() / zoom) / 2;
                currentY = (int) doubleCurrentY;

                if (g2 != null) {
                    toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, imageStates.get(currentIndex), g2, e.getModifiersEx(), toolbox.getToolSize(), toolbox.getIsSquareShape(), isFirstPoint, Canva.this);
                    if (isFirstPoint) isFirstPoint = false;
                    oldX = currentX;
                    oldY = currentY;
                }
                repaint();
            }
        });
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

    private BufferedImage copyBufferedImage(BufferedImage source) {
        ColorModel cm = source.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = source.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public BufferedImage nextBufferedImage() {
        BufferedImage newImage = copyBufferedImage(this.imageStates.get(this.currentIndex));
        this.setBufferedImage(newImage);
        return newImage;
    }

    public BufferedImage getBufferedImage() {
        return this.imageStates.get(currentIndex);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public Graphics2D getG2() {
        return this.g2;
    }

    public List<BufferedImage> getImageStates() {
        return imageStates;
    }

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

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        this.canvaSizeLabel.setText(this.getBufferedImage().getWidth() + "x" + this.getBufferedImage().getHeight());
    }

    protected void paintComponent(Graphics g) {
        this.g = g;
        if (this.imageStates.size() == 0 || this.imageStates.get(this.currentIndex) == null) {
            this.imageStates.add(this.currentIndex, new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB));
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
                        g.drawRect(startX, startY, endX, endY);
                    }
                }
                case OVAL -> {
                    if (isShapeFilled) {
                        g.fillOval(startX, startY, endX, endY);
                    } else {
                        g.drawOval(startX, startY, endX, endY);
                    }
                }
                case LINE -> g.drawLine(startX, startY, endX, endY);
            }
        }
    }

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