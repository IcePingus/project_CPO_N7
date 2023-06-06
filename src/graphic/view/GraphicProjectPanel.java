package graphic.view;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GraphicProjectPanel extends JDesktopPane implements ActionListener {

    private Canva canva;
    private CanvaController canvaController;
    private JMenuItem saveImage;
    private JMenuItem importImage;
    private JMenuItem bwTransform;
    private JMenuItem resize;
    private JMenuItem clear;
    private JMenuItem flipHorizontalImage;
    private JMenuItem flipVerticalImage;
    private JFrame frame;

    ResizeDialog resizeDialog;

    public GraphicProjectPanel(JFrame frame) {
        this.setLayout(new BorderLayout());
        this.frame = frame;

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

        this.canva = new Canva(toolbox);
        this.canvaController = new CanvaController(this.canva);

        this.resizeDialog = new ResizeDialog(this.canvaController);

        toolbox.addObserver(this.canvaController);
        this.add(this.canva, BorderLayout.CENTER);

        JMenuBar mb = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        this.saveImage = new JMenuItem("Save image");
        this.importImage = new JMenuItem("Import image");

        JMenu menuImage = new JMenu("Image");
        this.resize = new JMenuItem("Resize");
        this.flipHorizontalImage = new JMenuItem("Flip horizontal");
        this.flipVerticalImage = new JMenuItem("Flip vertical");
        this.clear = new JMenuItem("Clear image");

        JMenu menuEffects = new JMenu("Effects");
        this.bwTransform = new JMenuItem("Black and white");

        this.saveImage.addActionListener(this);
        this.importImage.addActionListener(this);
        this.resize.addActionListener(this);
        this.flipHorizontalImage.addActionListener(this);
        this.flipVerticalImage.addActionListener(this);
        this.clear.addActionListener(this);
        this.bwTransform.addActionListener(this);

        menuFile.add(this.saveImage);
        menuFile.add(this.importImage);
        menuImage.add(this.resize);
        menuImage.add(this.flipHorizontalImage);
        menuImage.add(this.flipVerticalImage);
        menuImage.add(this.clear);
        menuEffects.add(this.bwTransform);

        mb.add(menuFile);
        mb.add(menuImage);
        mb.add(menuEffects);
        frame.setJMenuBar(mb);

        this.setBackground(Color.LIGHT_GRAY);

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
            this.canvaController.exportPNG();
        } else if (e.getSource() == this.importImage) {
            this.canvaController.importImage(this.frame);
        } else if (e.getSource() == this.resize) {
            this.resizeDialog.setLocation(this.getSize().width / 3, this.getSize().height / 3);
            this.resizeDialog.setVisible(true);
        } else if (e.getSource() == this.flipHorizontalImage) {
            this.canvaController.flipImageHorizontal();
        } else if (e.getSource() == this.flipVerticalImage) {
            this.canvaController.flipImageVertical();
        } else if (e.getSource() == this.clear) {
            int resultOptionPane = JOptionPane.showConfirmDialog(this, "Do you really want to clear the canva ?", "Clear image", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resultOptionPane == JOptionPane.YES_OPTION) {
                this.canvaController.clear();
            }
        }  else if (e.getSource() == this.bwTransform) {
            this.canvaController.blackAndWhiteTransform();
        }
    }
}
