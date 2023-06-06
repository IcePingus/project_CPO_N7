package graphic.view;

import com.nitido.utils.toaster.Toaster;
import graphic.model.tools.Toolbox;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
                oldX = e.getX() - ((getWidth() - bufferedImage.getWidth()) / 2);
                oldY = e.getY() - ((getHeight() - bufferedImage.getHeight()) / 2);
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
            this.bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            this.g2 = (Graphics2D) this.bufferedImage.getGraphics();

            this.clear();
        }
        g.drawImage(this.bufferedImage, ((this.getWidth() - this.bufferedImage.getWidth()) / 2), ((this.getHeight() - this.bufferedImage.getHeight()) / 2), null);
    }

    protected void exportPNG() {
        Toaster toasterManager = new Toaster();
        try {
            toasterManager.showToaster("Image exported !");
            ImageIO.write(this.bufferedImage, "png", new File("export/image.png"));
        } catch (IOException e) {
            toasterManager.showToaster("There was an error while attempting to save the image.");
            e.printStackTrace();
        }
    }

    protected void blackAndWhiteTransform() {
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(this.bufferedImage, this.bufferedImage);
        this.repaint();
    }

    public void resizeCanva(int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, this.bufferedImage.getType());
        this.g2 = resizedImage.createGraphics();
        this.g2.drawImage(this.bufferedImage, 0, 0, width, height, null);
        this.bufferedImage = resizedImage;
        this.revalidate();
        this.repaint();
    }

    public void flipImageHorizontal() {
        AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
        affineTransform.translate(-this.bufferedImage.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.bufferedImage = op.filter(this.bufferedImage, null);
        this.repaint();
        this.g2 = (Graphics2D) this.bufferedImage.getGraphics();
    }

    public void flipImageVertical() {
        AffineTransform affineTransform = AffineTransform.getScaleInstance(1, -1);
        affineTransform.translate(0, -this.bufferedImage.getHeight());
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.bufferedImage = op.filter(this.bufferedImage, null);
        this.repaint();
        this.g2 = (Graphics2D) this.bufferedImage.getGraphics();
    }

    public void importImage(JFrame frame) {
        String filename = File.separator+"tmp";
        JFileChooser fileChooser = new JFileChooser(new File(filename));

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Image");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.getName().endsWith(".png") ||file.getName().endsWith(".jpg") || file.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return null;
            }
        });

        fileChooser.showOpenDialog(frame);
        try {
            this.bufferedImage = ImageIO.read(fileChooser.getSelectedFile());
            repaint();
            this.g2 = (Graphics2D) this.bufferedImage.getGraphics();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        this.g2.setPaint(Color.WHITE);
        this.g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.repaint();
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