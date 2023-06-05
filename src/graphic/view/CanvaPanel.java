package graphic.view;

import com.nitido.utils.toaster.Toaster;
import graphic.model.tools.Toolbox;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
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
                toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, bufferedImage, g2, e.getModifiersEx(), toolbox.getToolSize());

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

    protected void exportPNG() {
        Toaster toasterManager = new Toaster();
        try {
            toasterManager.showToaster("Votre image a été enregistré");
            ImageIO.write(this.bufferedImage, "png", new File("image.png"));
        } catch (IOException e) {
            toasterManager.showToaster("Problème lors de l'exportation de l'image");
            e.printStackTrace();
        }
    }

    protected void blackAndWhiteTransform() {
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(bufferedImage, bufferedImage);
        this.repaint();
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

                if (g2 != null) {
                    toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, bufferedImage, g2, e.getModifiersEx(), toolbox.getToolSize());
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