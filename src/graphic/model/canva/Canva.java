package graphic.model.canva;

import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

public class Canva extends JComponent {

    private List<BufferedImage> imageStates;
    private int currentIndex;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;
    private Toolbox toolbox;

    public Canva(Toolbox toolbox) {
        this.toolbox = toolbox;
        this.imageStates = new ArrayList<>();
        this.currentIndex = 0;
        this.setDoubleBuffered(false);
        this.requestFocusInWindow();
        this.enableKeyboardInputs();
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                oldX = e.getX() - ((getWidth() - imageStates.get(currentIndex).getWidth()) / 2);
                oldY = e.getY() - ((getHeight() - imageStates.get(currentIndex).getHeight()) / 2);
                currentX = oldX;
                currentY = oldY;

                BufferedImage newImage = nextBufferedImage();

                toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, newImage, g2, e.getModifiersEx(), toolbox.getToolSize(), toolbox.getIsSquareShape(), Canva.this);

                repaint();
            }

        });
        this.setMouseMotionListener();
    }

    private void enableKeyboardInputs() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(undoKeyStroke, "undo");
        actionMap.put("undo", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                undo();
            }
        });

        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(redoKeyStroke, "redo");
        actionMap.put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                redo();
            }
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

    private void undo() {
        if (this.imageStates.size() > 1 && this.currentIndex > 0) {
            this.currentIndex--;
            this.g2 = (Graphics2D) this.imageStates.get(this.currentIndex).getGraphics();
            this.repaint();
        }
    }

    private void redo() {
        if (this.currentIndex < this.imageStates.size() - 1) {
            this.currentIndex++;
            this.g2 = (Graphics2D) this.imageStates.get(this.currentIndex).getGraphics();
            this.repaint();
        }
    }

    public BufferedImage getBufferedImage() {
        return this.imageStates.get(currentIndex);
    }

    public Graphics2D getG2() {
        return this.g2;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.currentIndex++;
        this.imageStates.add(this.currentIndex, bufferedImage);
        if (this.imageStates.size() > this.currentIndex + 1) {
            this.imageStates.subList(this.currentIndex + 1, this.imageStates.size()).clear();
        }
        this.g2 = (Graphics2D) bufferedImage.getGraphics();
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    public void setToolbox(Toolbox toolbox) {
        this.toolbox = toolbox;
    }

    protected void paintComponent(Graphics g) {
        if (this.imageStates.size() == 0 || this.imageStates.get(this.currentIndex) == null) {
            this.imageStates.add(this.currentIndex, new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB));
            this.g2 =(Graphics2D) this.imageStates.get(this.currentIndex).getGraphics();

            this.g2.setPaint(Color.WHITE);
            this.g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            this.repaint();
        }
        g.drawImage(this.imageStates.get(this.currentIndex), ((this.getWidth() - this.imageStates.get(this.currentIndex).getWidth()) / 2), ((this.getHeight() - this.imageStates.get(this.currentIndex).getHeight()) / 2), null);    }

    public void setMouseMotionListener() {
        if (this.getMouseMotionListeners().length != 0) {
            this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
        }
        this.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                currentX = e.getX() - ((getWidth() - imageStates.get(currentIndex).getWidth()) / 2);
                currentY = e.getY() - ((getHeight() - imageStates.get(currentIndex).getHeight()) / 2);

                if (g2 != null) {
                    toolbox.getActiveTool().execute(oldX, oldY, currentX, currentY, imageStates.get(currentIndex), g2, e.getModifiersEx(), toolbox.getToolSize(), toolbox.getIsSquareShape(), Canva.this);
                    oldX = currentX;
                    oldY = currentY;
                }
                repaint();
            }
        });
    }
}