package graphic.view;

import graphic.model.canva.Canva;
import graphic.model.canva.Pixel;
import graphic.model.tools.ToolCommand;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class CanvaPanel extends JPanel implements MouseListener, Observer {

    private ToolCommand activeTool;

    private Canva toile;

    private GridBagConstraints gbc;

    public CanvaPanel(int width, int height, Toolbox toolbox) {

        this.setLayout(new GridBagLayout());

        this.activeTool = toolbox.getActiveTool();

        this.toile = new Canva(width, height, this.getWidth(), this.getHeight());

        this.gbc = new GridBagConstraints();

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                this.gbc.gridx = w;
                this.gbc.gridy = h;
                this.toile.getPixel(w, h).addMouseListener(this);
                this.add(this.toile.getPixel(w, h), gbc);
            }
        }

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                for (int w = 0; w < width; w++) {
                    for (int h = 0; h < height; h++) {
                        toile.getPixel(w, h).setSize(width, height, getWidth(), getHeight());
                    }
                }
            }
        });

    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Toolbox) {
            this.activeTool = ((Toolbox) o).getActiveTool();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof Pixel) {
            this.activeTool.execute((Pixel) e.getSource());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof Pixel && e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
            this.activeTool.execute((Pixel) e.getSource());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) { }
}
