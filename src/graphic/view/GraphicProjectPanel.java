package graphic.view;

import graphic.controller.ColorController;
import graphic.model.color.ColorModel;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GraphicProjectPanel extends JDesktopPane implements ActionListener {

    private CanvaPanel canvaPanel;
    private JMenuItem saveImage;

    public GraphicProjectPanel(JFrame frame) {
        this.setLayout(new BorderLayout());

        Toolbox toolbox = new Toolbox();
        ColorModel colorModel = new ColorModel();
        ColorController colorController = new ColorController(colorModel);

        ToolInternalFrame toolInternalFrame = new ToolInternalFrame(toolbox, colorController, colorModel);
        toolInternalFrame.setVisible(true);
        this.add(toolInternalFrame);
        this.setSize(this.getToolkit().getScreenSize());

        ColorSchemeInternalFrame colorSchemeInternalFrame = new ColorSchemeInternalFrame(colorController);
        colorModel.addObserver(colorSchemeInternalFrame);
        colorSchemeInternalFrame.setLocation(this.getSize().width - 430, 0);
        colorSchemeInternalFrame.setVisible(true);
        this.add(colorSchemeInternalFrame);

        this.canvaPanel = new CanvaPanel(toolbox);
        toolbox.addObserver(this.canvaPanel);
        this.add(this.canvaPanel, BorderLayout.CENTER);

        JMenuBar mb = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        this.saveImage = new JMenuItem("Save image");
        menuFile.add(this.saveImage);
        mb.add(menuFile);
        frame.setJMenuBar(mb);

        this.saveImage.addActionListener(this);

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (getSize().width < colorSchemeInternalFrame.getX() + 550) {
                    colorSchemeInternalFrame.setLocation(getSize().width - 550, colorSchemeInternalFrame.getHeight());
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.saveImage) {
            canvaPanel.exportPNG();
        }
    }
}
