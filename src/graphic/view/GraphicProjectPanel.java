package graphic.view;

import javax.swing.*;
import java.awt.*;

public class GraphicProjectPanel extends JDesktopPane {

    private CanvaPanel canvaPanel;

    private ToolInternalFrame toolInternalFrame;
    private ColorSchemeInternalFrame colorSchemeInternalFrame;

    public GraphicProjectPanel() {
        this.setLayout(new BorderLayout());

        this.toolInternalFrame = new ToolInternalFrame();
        this.toolInternalFrame.setVisible(true);
        this.add(this.toolInternalFrame);
        this.setSize(this.getToolkit().getScreenSize());

        this.colorSchemeInternalFrame = new ColorSchemeInternalFrame();
        this.colorSchemeInternalFrame.setLocation(this.getSize().width-430, 0);
        this.colorSchemeInternalFrame.setVisible(true);
        this.add(this.colorSchemeInternalFrame);

        this.canvaPanel = new CanvaPanel(128, 128);
        this.add(this.canvaPanel, BorderLayout.CENTER);
    }

}
