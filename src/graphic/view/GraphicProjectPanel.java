package graphic.view;

import graphic.controller.ColorController;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GraphicProjectPanel extends JDesktopPane {

    public GraphicProjectPanel() {
        this.setLayout(new BorderLayout());

        Toolbox toolbox = new Toolbox();
        ColorController colorController = new ColorController();

        ToolInternalFrame toolInternalFrame = new ToolInternalFrame(toolbox);
        toolInternalFrame.setVisible(true);
        this.add(toolInternalFrame);
        this.setSize(this.getToolkit().getScreenSize());

        ColorSchemeInternalFrame colorSchemeInternalFrame = new ColorSchemeInternalFrame(colorController);
        colorSchemeInternalFrame.setLocation(this.getSize().width - 430, 0);
        colorSchemeInternalFrame.setVisible(true);
        this.add(colorSchemeInternalFrame);

        CanvaPanel canvaPanel = new CanvaPanel(toolbox);
        toolbox.addObserver(canvaPanel);
        colorController.addObserver(canvaPanel);
        this.add(canvaPanel, BorderLayout.CENTER);

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
