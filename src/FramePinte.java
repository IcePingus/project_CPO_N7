import graphic.view.SelectionPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FramePinte extends JFrame {

    private SelectionPanel selectionPanel;

    public FramePinte() {
        super("Pinte");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(960, 540);

        this.setMinimumSize(new Dimension(960, 540));
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);
        try {
            this.setIconImage(javax.imageio.ImageIO.read(new File("src/assets/images/logo.png")));
            final Image backgroundImage = javax.imageio.ImageIO.read(new File("src/assets/images/background.png"));
            this.setLayout(new BorderLayout());
            this.setContentPane(new JPanel(new BorderLayout()) {
                @Override public void paintComponent(Graphics g) {
                    g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
                }
            });
            this.selectionPanel = new SelectionPanel(this);
            this.add(this.selectionPanel, BorderLayout.CENTER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
