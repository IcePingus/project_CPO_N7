package graphic.view;

import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;

public class GraphicProjectPanel extends JDesktopPane {

    private CanvaPanel canvaPanel;

    private Toolbox toolbox;

    private ToolInternalFrame toolInternalFrame;
    private ColorSchemeInternalFrame colorSchemeInternalFrame;

    public GraphicProjectPanel() {
        this.setLayout(new BorderLayout());

        this.toolbox = new Toolbox();

        this.toolInternalFrame = new ToolInternalFrame(this.toolbox);
        this.toolInternalFrame.setVisible(true);
        this.add(this.toolInternalFrame);

        this.colorSchemeInternalFrame = new ColorSchemeInternalFrame();
        this.colorSchemeInternalFrame.setVisible(true);
        this.add(this.colorSchemeInternalFrame);

        this.canvaPanel = new CanvaPanel(128, 128, this.toolbox);
        this.add(this.canvaPanel, BorderLayout.CENTER);

        this.toolbox.addObserver(this.canvaPanel);
    }

}
