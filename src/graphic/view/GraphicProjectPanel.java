package graphic.view;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphicProjectPanel extends JDesktopPane implements ActionListener {

    private Canva canva;
    private CanvaController canvaController;
    private JMenuItem saveImage;
    private JMenuItem quit;
    private JMenuItem undo;
    private JMenuItem redo;
    private JMenuItem paste;
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
        this.quit = new JMenuItem("Exit");

        JMenu menuEdit = new JMenu("Edit");
        this.undo = new JMenuItem("Undo");
        this.redo = new JMenuItem("Redo");
        this.paste = new JMenuItem("Paste");

        JMenu menuImage = new JMenu("Image");
        this.resize = new JMenuItem("Resize");
        this.flipHorizontalImage = new JMenuItem("Horizontal flip");
        this.flipVerticalImage = new JMenuItem("Vertical flip");
        this.clear = new JMenuItem("Clear image");

        JMenu menuEffects = new JMenu("Effects");
        this.bwTransform = new JMenuItem("Black and white");

        this.saveImage.addActionListener(this);
        this.importImage.addActionListener(this);
        this.quit.addActionListener(this);
        this.undo.addActionListener(this);
        this.redo.addActionListener(this);
        this.paste.addActionListener(this);
        this.resize.addActionListener(this);
        this.flipHorizontalImage.addActionListener(this);
        this.flipVerticalImage.addActionListener(this);
        this.clear.addActionListener(this);
        this.bwTransform.addActionListener(this);

        menuFile.add(this.saveImage);
        menuFile.add(this.importImage);
        menuFile.add(this.quit);
        menuEdit.add(this.undo);
        menuEdit.add(this.redo);
        menuEdit.add(this.paste);
        menuImage.add(this.resize);
        menuImage.add(this.flipHorizontalImage);
        menuImage.add(this.flipVerticalImage);
        menuImage.add(this.clear);
        menuEffects.add(this.bwTransform);

        mb.add(menuFile);
        mb.add(menuEdit);
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
        this.enableKeyboardInputs();
    }

    private void enableKeyboardInputs() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        KeyStroke saveImageKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(saveImageKeyStroke, "saveImage");
        actionMap.put("saveImage", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.chooseExportPath();
            }
        });

        KeyStroke importImageKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(importImageKeyStroke, "importImage");
        actionMap.put("importImage", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.chooseImportPath(frame);
            }
        });

        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(undoKeyStroke, "undo");
        actionMap.put("undo", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.undo();
            }
        });

        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(redoKeyStroke, "redo");
        actionMap.put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.redo();
            }
        });

        KeyStroke pasteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(pasteKeyStroke, "paste");
        actionMap.put("paste", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.clipboardToBufferedImage();
            }
        });

        KeyStroke resizeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(resizeKeyStroke, "resize");
        actionMap.put("resize", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                resizeDialog.setLocation(getSize().width / 3, getSize().height / 3);
                resizeDialog.setVisible(true);
            }
        });

        KeyStroke leftHorizontalFlipKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(leftHorizontalFlipKeyStroke, "horizontalFlip");
        actionMap.put("horizontalFlip", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.flipImageHorizontal();
            }
        });

        KeyStroke rightHorizontalFlipKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(rightHorizontalFlipKeyStroke, "horizontalFlip");
        actionMap.put("horizontalFlip", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.flipImageHorizontal();
            }
        });

        KeyStroke upVerticalFlipKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_UP, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(upVerticalFlipKeyStroke, "verticalFlip");
        actionMap.put("verticalFlip", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.flipImageVertical();
            }
        });

        KeyStroke downVerticalFlipKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(downVerticalFlipKeyStroke, "verticalFlip");
        actionMap.put("verticalFlip", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.flipImageVertical();
            }
        });

        KeyStroke clearKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(clearKeyStroke, "clear");
        actionMap.put("clear", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.clear();
            }
        });

        KeyStroke blackAndWhiteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(blackAndWhiteKeyStroke, "blackAndWhite");
        actionMap.put("blackAndWhite", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                canvaController.blackAndWhiteTransform();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.saveImage) {
            this.canvaController.chooseExportPath();
        } else if (e.getSource() == this.importImage) {
            this.canvaController.chooseImportPath(this.frame);
        } else if (e.getSource() == this.quit) {
            this.canvaController.quit(this.frame);
        } else if (e.getSource() == this.undo) {
            this.canvaController.undo();
        } else if (e.getSource() == this.redo) {
            this.canvaController.redo();
        } else if (e.getSource() == this.paste) {
            this.canvaController.clipboardToBufferedImage();
        } else if (e.getSource() == this.resize) {
            this.resizeDialog.setInputs();
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
