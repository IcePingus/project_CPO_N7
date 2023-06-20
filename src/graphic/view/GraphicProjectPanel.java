package graphic.view;

import graphic.controller.CanvaController;
import graphic.controller.ColorController;
import graphic.model.canva.Canva;
import graphic.model.color.ColorModel;
import graphic.model.tools.Toolbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The GraphicProjectPanel class represents a panel that contains various components for a graphic project.
 *
 * @author Team 3
 */
public class GraphicProjectPanel extends JDesktopPane implements ActionListener {

    private final CanvaController canvaController;
    private final JMenuItem saveImage;
    private final JMenuItem quit;
    private final JMenuItem undo;
    private final JMenuItem redo;
    private final JMenuItem paste;
    private final JMenuItem importImage;
    private final JMenuItem bwTransform;
    private final JMenuItem resize;
    private final JMenuItem crop;
    private final JMenuItem clear;
    private final JMenuItem flipHorizontalImage;
    private final JMenuItem flipVerticalImage;
    private final JFrame frame;
    private final ResizeDialog resizeDialog;

    /**
     * Constructs a GraphicProjectPanel view with the specified JFrame.
     *
     * @param frame the JFrame object to which the panel belongs
     */
    public GraphicProjectPanel(JFrame frame) {

        this.setLayout(new BorderLayout());
        this.frame = frame;

        // Création toolbar et ajout labels (taille de la toile et niveau de zoom)
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBorderPainted(false);

        JLabel canvaSizeLabel = new JLabel("");
        toolbar.add(canvaSizeLabel);

        JLabel zoomLabel = new JLabel(" - Zoom : 1x");
        toolbar.add(zoomLabel);

        add(toolbar, BorderLayout.SOUTH);

        // Instanciation des models
        Toolbox toolbox = new Toolbox();
        ColorModel colorModel = new ColorModel();
        Canva canva = new Canva(toolbox, canvaSizeLabel, zoomLabel);

        // Instanciation des controllers
        ColorController colorController = new ColorController(colorModel);
        this.canvaController = new CanvaController(canva);

        // Initialisation des fenêtres internes et ajout au panel
        ToolInternalFrame toolInternalFrame = new ToolInternalFrame(toolbox, colorController, colorModel);
        toolInternalFrame.setVisible(true);
        this.add(toolInternalFrame);
        this.setSize(this.getToolkit().getScreenSize());
        ColorSchemeInternalFrame colorSchemeInternalFrame = new ColorSchemeInternalFrame(colorController);
        colorModel.addObserver(colorSchemeInternalFrame);
        colorSchemeInternalFrame.setLocation(this.getSize().width - 430, 0);
        colorSchemeInternalFrame.setVisible(true);
        this.add(colorSchemeInternalFrame);

        this.resizeDialog = new ResizeDialog(this.canvaController);

        this.add(canva, BorderLayout.CENTER);

        // Ajout des menus et options de menu
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
        this.crop = new JMenuItem("Crop");
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
        this.crop.addActionListener(this);
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
        menuImage.add(this.crop);
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
                // Déplacement des fenêtres internes lorsque la fenêtre est trop petite
                if (getSize().width < colorSchemeInternalFrame.getX() + 550) {
                    colorSchemeInternalFrame.setLocation(getSize().width - 550, 0);
                }
                if (getSize().width < toolInternalFrame.getX() + 148) {
                    toolInternalFrame.setLocation(0, 0);
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

    /**
     * Enables keyboard inputs for specific actions.
     */
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
                resizeDialog.setInputs(false);
                resizeDialog.setLocation(getSize().width / 3, getSize().height / 3);
                resizeDialog.setVisible(true);
            }
        });

        KeyStroke cropKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        inputMap.put(cropKeyStroke, "crop");
        actionMap.put("crop", new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                resizeDialog.setInputs(true);
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

    /**
     * Performs an action based on the event triggered.
     *
     * @param e the ActionEvent object representing the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Traitement lors du clic sur une option de menu
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
        } else if (e.getSource() == this.resize || e.getSource() == this.crop) {
            this.resizeDialog.setInputs(e.getSource() == this.crop);
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