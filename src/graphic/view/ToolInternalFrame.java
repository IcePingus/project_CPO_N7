package graphic.view;

import graphic.controller.ColorController;
import graphic.model.ShapeTypes;
import graphic.model.color.ColorModel;
import graphic.model.tools.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ToolInternalFrame class represents an internal frame that contains the tools and options for drawing.
 *
 * @author Team 3
 */
public class ToolInternalFrame extends JInternalFrame implements ActionListener, ChangeListener, DocumentListener {

    private final Toolbox toolbox;
    private int activeTool;
    private JSlider sliderSize;

    private JRadioButton squarePencilShapeButton;
    private JRadioButton circlePencilShapeButton;
    private ButtonGroup shapePencilButtonGroup;

    private JLabel shapeLabel;
    private JRadioButton rectangleShapeButton;
    private JRadioButton circleShapeButton;
    private JRadioButton lineShapeButton;
    private ButtonGroup shapeButtonGroup;

    private JLabel fillModeLabel;
    private JRadioButton strokeButton;
    private JRadioButton fillButton;
    private ButtonGroup fillButtonGroup;

    private JPanel toolsPanel;
    private JPanel toolOptionsPanel;

    private JPanel sliderPanel;
    private JPanel squareShapePanel;
    private JPanel shapePanel;
    private JPanel fillOptionPanel;

    private JTextField sizeLabel;

    private Runnable runnableInsert;

    /**
     * Constructs a ToolInternalFrame with the specified toolbox, color controller, and color model.
     *
     * @param toolbox         the Toolbox object representing the collection of tools
     * @param colorController the ColorController object for handling color changes
     * @param colorModel      the ColorModel object representing the color model
     */
    public ToolInternalFrame(Toolbox toolbox, ColorController colorController, ColorModel colorModel) {
        super("Tools");
        this.setMaximizable(false);
        this.setIconifiable(false);
        this.setResizable(false);
        this.setClosable(false);
        this.setSize(148, 360);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setFrameIcon(new ImageIcon(getClass().getResource("/assets/images/toolFrameLogo.png")));

        this.setLayout(new BorderLayout());

        this.toolsPanel = new JPanel();

        this.toolsPanel.setLayout(new GridLayout(4, 2));

        this.toolbox = toolbox;
        // Ajout des outils
        this.toolbox.addTool(new RubberTool());
        this.toolbox.addTool(new BucketTool());
        this.toolbox.addTool(new PickerTool(colorController));
        this.toolbox.addTool(new HighlighterTool());
        TextTool textTool = new TextTool();
        this.toolbox.addObserver(textTool);
        this.toolbox.addTool(textTool);
        ShapeTool shapeTool = new ShapeTool();
        this.toolbox.addObserver(shapeTool);
        this.toolbox.addTool(shapeTool);
        this.toolbox.addTool(new MoveTool());

        for (int i = 0; i < this.toolbox.getTools().size(); i++) {
            // Ajout des outils au panel
            this.toolsPanel.add(this.toolbox.getToolsButtons().get(i));
            this.toolbox.getToolsButtons().get(i).setName(this.toolbox.getTools().get(i).getName());
            this.toolbox.getToolsButtons().get(i).setIcon(this.toolbox.getTools().get(i).getImage());
            this.toolbox.getToolsButtons().get(i).addActionListener(this);
            colorModel.addObserver(this.toolbox.getTools().get(i));
        }

        // Définition de l'outil actif
        this.activeTool = 0;
        this.toolbox.getToolsButtons().get(0).setBackground(Color.red);

        this.add(this.toolsPanel, BorderLayout.CENTER);

        // Positionnement des panels
        this.sliderPanel = new JPanel();
        this.sliderPanel.setLayout(new GridLayout(1, 2));

        this.squareShapePanel = new JPanel();
        this.squareShapePanel.setLayout(new GridLayout(1, 2));

        this.shapePanel = new JPanel();
        this.shapePanel.setLayout(new GridLayout(4, 1));

        this.fillOptionPanel = new JPanel();
        this.fillOptionPanel.setLayout(new GridLayout(3, 1));

        // Définition des options d'outil
        this.sizeLabel = new JTextField("5");
        this.sizeLabel.addActionListener(this);
        this.sizeLabel.getDocument().addDocumentListener(this);

        this.sliderSize = new JSlider(1, 1000, 5);
        this.sliderSize.addChangeListener(this);

        this.sliderPanel.add(sizeLabel);
        this.sliderPanel.add(sliderSize);

        this.squarePencilShapeButton = new JRadioButton("Square");
        this.squarePencilShapeButton.setSelected(true);
        this.squarePencilShapeButton.addActionListener(this);
        this.circlePencilShapeButton = new JRadioButton("Circle");
        this.circlePencilShapeButton.addActionListener(this);

        this.shapePencilButtonGroup = new ButtonGroup();
        this.shapePencilButtonGroup.add(this.squarePencilShapeButton);
        this.shapePencilButtonGroup.add(this.circlePencilShapeButton);

        this.squareShapePanel.add(this.squarePencilShapeButton);
        this.squareShapePanel.add(this.circlePencilShapeButton);

        this.shapeLabel = new JLabel("Shape :");

        this.rectangleShapeButton = new JRadioButton("Rectangle");
        this.rectangleShapeButton.setSelected(true);
        this.rectangleShapeButton.addActionListener(this);
        this.circleShapeButton = new JRadioButton("Circle");
        this.circleShapeButton.addActionListener(this);
        this.lineShapeButton = new JRadioButton("Line");
        this.lineShapeButton.addActionListener(this);

        this.shapeButtonGroup = new ButtonGroup();
        this.shapeButtonGroup.add(this.rectangleShapeButton);
        this.shapeButtonGroup.add(this.circleShapeButton);
        this.shapeButtonGroup.add(this.lineShapeButton);

        this.shapePanel.add(this.shapeLabel);
        this.shapePanel.add(this.rectangleShapeButton);
        this.shapePanel.add(this.circleShapeButton);
        this.shapePanel.add(this.lineShapeButton);

        this.fillModeLabel = new JLabel("Fill mode :");

        this.strokeButton = new JRadioButton("Stroke");
        this.strokeButton.setSelected(true);
        this.strokeButton.addActionListener(this);
        this.fillButton = new JRadioButton("Fill");
        this.fillButton.addActionListener(this);

        this.fillButtonGroup = new ButtonGroup();
        this.fillButtonGroup.add(this.strokeButton);
        this.fillButtonGroup.add(this.fillButton);

        this.fillOptionPanel.add(this.fillModeLabel);
        this.fillOptionPanel.add(this.strokeButton);
        this.fillOptionPanel.add(this.fillButton);

        this.toolOptionsPanel = new JPanel();
        this.toolOptionsPanel.setLayout(new GridLayout(2, 1));

        this.add(this.toolOptionsPanel, BorderLayout.SOUTH);
        this.toolOptionsPanel.add(this.sliderPanel);
        this.toolOptionsPanel.add(this.squareShapePanel);
    }

    /**
     * Handles the action events triggered by buttons and text field.
     *
     * @param e the ActionEvent object representing the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Enregistrer les options d'outil ou sélectionner le nouvel outil
        if (e.getSource() == this.squarePencilShapeButton || e.getSource() == this.circlePencilShapeButton) {
            this.toolbox.setIsSquareShape(this.squarePencilShapeButton.isSelected());
        } else if (e.getSource() == this.rectangleShapeButton || e.getSource() == this.circleShapeButton || e.getSource() == this.lineShapeButton) {
            if (e.getSource() == this.rectangleShapeButton) {
                this.toolbox.setShapeType(ShapeTypes.RECTANGLE);
            } else if (e.getSource() == this.circleShapeButton) {
                this.toolbox.setShapeType(ShapeTypes.OVAL);
            } else {
                this.toolbox.setShapeType(ShapeTypes.LINE);
            }
        } else if (e.getSource() == this.strokeButton || e.getSource() == this.fillButton) {
            this.toolbox.setIsFilledShape(this.fillButton.isSelected());
        } else {
            for (int i = 0; i < this.toolbox.getTools().size(); i++) {
                if (e.getSource() == this.toolbox.getToolsButtons().get(i) && this.activeTool != i) {
                    this.toolbox.getToolsButtons().get(this.activeTool).setBackground(null);
                    this.activeTool = i;
                    this.toolbox.getToolsButtons().get(i).setBackground(Color.red);
                    this.toolbox.setActiveTool(this.activeTool);

                    // Traitement des options possibles pour les outils (taille de l'outil, forme, stroke ou fill)
                    this.toolOptionsPanel.setVisible(true);
                    if (!this.toolbox.getActiveTool().getIsResizable() && !this.toolbox.getActiveTool().getIsSquareRoundShape()) {
                        this.toolOptionsPanel.setVisible(false);
                        this.toolOptionsPanel.removeAll();
                        this.setSize(148, 315);
                    } else if (!this.toolbox.getActiveTool().getIsResizable() && this.toolbox.getActiveTool().getIsSquareRoundShape()) {
                        this.toolOptionsPanel.setLayout(new GridLayout(1, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.squareShapePanel);
                        this.setSize(148, 335);
                    } else if (this.toolbox.getActiveTool().getIsResizable() && !this.toolbox.getActiveTool().getIsSquareRoundShape()) {
                        this.toolOptionsPanel.setLayout(new GridLayout(1, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.sliderPanel);
                        this.setSize(148, 335);
                    } else {
                        this.toolOptionsPanel.setLayout(new GridLayout(2, 1));
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.sliderPanel);
                        this.toolOptionsPanel.add(this.squareShapePanel);
                        this.setSize(148, 363);
                    }

                    if (this.toolbox.getActiveTool().getHasShapeSelection()) {
                        this.toolOptionsPanel.setVisible(true);
                        this.toolOptionsPanel.setLayout(new BorderLayout());
                        this.toolOptionsPanel.removeAll();
                        this.toolOptionsPanel.add(this.shapePanel, BorderLayout.NORTH);
                        this.toolOptionsPanel.add(this.fillOptionPanel, BorderLayout.CENTER);
                        this.toolOptionsPanel.add(this.sliderPanel, BorderLayout.SOUTH);
                        this.setSize(148, 496);
                    }
                }
            }
        }
    }

    /**
     * Handles the state change events triggered by the slider.
     *
     * @param e the ChangeEvent object representing the event
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.sliderSize) {
            // Mettre à jour le label en fonction de la valeur du glisseur de taille
            this.sizeLabel.getDocument().removeDocumentListener(this);
            this.sizeLabel.setText(String.valueOf(this.sliderSize.getValue()));
            this.sizeLabel.getDocument().addDocumentListener(this);
            this.toolbox.setToolSize(this.sliderSize.getValue());
        }
    }

    /**
     * Gives notification that there was an insert into the document. The range given by
     * the DocumentEvent bounds the freshly inserted region.
     *
     * @param e the document event
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (runnableInsert == null) {
            // Utilisation d'un runnable car on ne peut pas modifier un JTextField au même moment où on écrit dedans,
            // on doit donc retarder un peu la modification
            runnableInsert = new Runnable() {
                @Override
                public void run() {
                    // Vérification de la validité du label
                    if (!sizeLabel.getText().matches("^[0-9]+$")) {
                        sizeLabel.setText(sizeLabel.getText().substring(0, sizeLabel.getText().length() - 1));
                    }

                    if (Integer.parseInt(sizeLabel.getText()) > 1000) {
                        sizeLabel.setText("1000");
                    }

                    sliderSize.setValue(Integer.parseInt(sizeLabel.getText()));
                    toolbox.setToolSize(Integer.parseInt(sizeLabel.getText()));
                }
            };
        }
        SwingUtilities.invokeLater(runnableInsert);
    }

    /**
     * Gives notification that a portion of the document has been removed. The range is
     * given in terms of what the view last saw (that is, before updating sticky positions).
     *
     * @param e the document event
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        if (runnableInsert == null) {
            // Utilisation d'un runnable car on ne peut pas modifier un JTextField au même moment où on écrit dedans,
            // on doit donc retarder un peu la modification
            runnableInsert = new Runnable() {
                @Override
                public void run() {
                    // Vérification de la validité du label
                    if (sizeLabel.getText().length() == 0) {
                        sizeLabel.setText("1");
                    }

                    sliderSize.setValue(Integer.parseInt(sizeLabel.getText()));
                    toolbox.setToolSize(Integer.parseInt(sizeLabel.getText()));
                }
            };
        }
        SwingUtilities.invokeLater(runnableInsert);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
