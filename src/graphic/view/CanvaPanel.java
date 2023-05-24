package graphic.view;

import graphic.model.Pixel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CanvaPanel extends JPanel {

    private Pixel[][] toile;

    private GridBagConstraints gbc;

    public CanvaPanel(int width, int height) {

        this.setLayout(new GridBagLayout());

        this.toile = new Pixel[width][height];

        this.gbc = new GridBagConstraints();

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                this.gbc.gridx = w;
                this.gbc.gridy = h;
                this.toile[w][h] = new Pixel(width, height, this.getWidth(), this.getHeight());
                this.add(this.toile[w][h], gbc);
            }
        }

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                for (int w = 0; w < width; w++) {
                    for (int h = 0; h < height; h++) {
                        toile[w][h].setSize(width, height, getWidth(), getHeight());
                    }
                }
            }
        });

    }

}
