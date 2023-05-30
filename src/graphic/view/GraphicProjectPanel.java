package graphic.view;

import graphic.controller.ColorController;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GraphicProjectPanel extends JDesktopPane {

    private CanvaPanel canvaPanel;

    private Toolbox toolbox;

    private ColorController colorController;
    private ToolInternalFrame toolInternalFrame;
    private ColorSchemeInternalFrame colorSchemeInternalFrame;

    public GraphicProjectPanel() {
        this.setLayout(new BorderLayout());

        this.toolbox = new Toolbox();
        this.colorController = new ColorController();

        this.toolInternalFrame = new ToolInternalFrame(this.toolbox, this.colorController);
        this.toolInternalFrame.setVisible(true);
        this.add(this.toolInternalFrame);
        this.setSize(this.getToolkit().getScreenSize());

        this.colorSchemeInternalFrame = new ColorSchemeInternalFrame(this.colorController);
        this.colorSchemeInternalFrame.setLocation(this.getSize().width - 430, 0);
        this.colorSchemeInternalFrame.setVisible(true);
        this.add(this.colorSchemeInternalFrame);

        this.canvaPanel = new CanvaPanel(128, 128, this.toolbox);
        this.add(this.canvaPanel, BorderLayout.CENTER);

        this.toolbox.addObserver(this.canvaPanel);
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (getSize().width < colorSchemeInternalFrame.getX() + 430) {
                    colorSchemeInternalFrame.setLocation(getSize().width - 430, colorSchemeInternalFrame.getHeight());
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
}
