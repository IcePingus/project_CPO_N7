package graphic.view;

import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class CanvaPanel extends JComponent implements Observer {

    private BufferedImage bufferedImage;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;
    private Toolbox toolbox;

    public CanvaPanel(Toolbox toolbox) {
        this.toolbox = toolbox;
        this.setDoubleBuffered(false);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                oldX = e.getX();
                oldY = e.getY();
                currentX = oldX;
                currentY = oldY;
                toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, bufferedImage, g2, e.getModifiersEx());

                repaint();
            }

        });
        this.setMouseMotionListener();
    }

    protected void paintComponent(Graphics g) {
        if (this.bufferedImage == null) {
            this.bufferedImage = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_RGB);
            this.g2 = (Graphics2D) this.bufferedImage.getGraphics();

            this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            this.clear();
        }
        g.drawImage(this.bufferedImage, 0, 0, null);
    }

    public void clear() {
        this.g2.setPaint(Color.white);
        this.g2.fillRect(0, 0, this.getSize().width, this.getSize().height);
        this.repaint();
    }

    public void setMouseMotionListener() {
        if (this.getMouseMotionListeners().length != 0) {
            this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
        }
        this.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                /*
                if (oldX != currentX && oldY != currentY) {
                    oldX = currentX;
                    oldY = currentY;
                }
                */

                if (g2 != null) {
                    toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, bufferedImage, g2, e.getModifiersEx());
                    //tool action
                    oldX = currentX;
                    oldY = currentY;
                }
                repaint();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Toolbox) {
            this.toolbox = (Toolbox) o;
            this.setMouseMotionListener();
        }
    }
}