package graphic.model.canva;

import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class Canva extends JComponent  {

    private BufferedImage bufferedImage;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;
    private Toolbox toolbox;

    public Canva(Toolbox toolbox) {
        this.toolbox = toolbox;
        this.setDoubleBuffered(false);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                oldX = e.getX() - ((getWidth() - bufferedImage.getWidth()) / 2);
                oldY = e.getY() - ((getHeight() - bufferedImage.getHeight()) / 2);
                currentX = oldX;
                currentY = oldY;
                toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, bufferedImage, g2, e.getModifiersEx(), toolbox.getToolSize(), toolbox.getIsSquareShape());

                repaint();
            }

        });
        this.setMouseMotionListener();
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public Graphics2D getG2() {
        return g2;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        this.repaint();
        this.g2 = (Graphics2D) this.bufferedImage.getGraphics();
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    public void setToolbox(Toolbox toolbox) {
        this.toolbox = toolbox;
    }

    protected void paintComponent(Graphics g) {
        if (this.bufferedImage == null) {
            this.bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            this.g2 = (Graphics2D) this.bufferedImage.getGraphics();

            this.g2.setPaint(Color.WHITE);
            this.g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            this.repaint();
        }
        g.drawImage(this.bufferedImage, ((this.getWidth() - this.bufferedImage.getWidth()) / 2), ((this.getHeight() - this.bufferedImage.getHeight()) / 2), null);
    }

    public void setMouseMotionListener() {
        if (this.getMouseMotionListeners().length != 0) {
            this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
        }
        this.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                currentX = e.getX() - ((getWidth() - bufferedImage.getWidth()) / 2);
                currentY = e.getY() - ((getHeight() - bufferedImage.getHeight()) / 2);

                if (g2 != null) {
                    toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, bufferedImage, g2, e.getModifiersEx(), toolbox.getToolSize(), toolbox.getIsSquareShape());
                    oldX = currentX;
                    oldY = currentY;
                }
                repaint();
            }
        });
    }
}