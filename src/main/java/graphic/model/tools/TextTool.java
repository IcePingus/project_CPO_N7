package graphic.model.tools;

import graphic.model.ToolContext;
import graphic.model.color.ColorModel;
import graphic.view.CanvaComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;

/**
 * The TextTool class represents a text tool in a graphics application.
 * It implements the ToolCommand, FocusListener, and KeyListener interfaces and provides functionality for adding text to the canvas.
 *
 * @author Team 3
 */
public class TextTool implements ToolCommand, FocusListener, KeyListener {
    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;
    private final boolean hasShapeSelection;
    private int size;
    private Graphics2D graphics2D;
    private CanvaComponent canvaComponent;
    private Color primaryColor;
    private Color secondaryColor;
    private boolean isPrimaryColor;
    private int currentX, currentY;
    private JTextField jtextField;
    private String text;

    /**
     * Constructs a new TextTool object with default values.
     */
    public TextTool() {
        this.name = "Text";
        this.image = new ImageIcon(getClass().getResource("/images/text.png"));
        // Options par défaut
        this.isResizable = true;
        this.isSquareRoundShape = false;
        this.hasShapeSelection = false;
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
        this.text = "";
        this.size = 10;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Icon getImage() {
        return this.image;
    }

    @Override
    public boolean getIsResizable() {
        return this.isResizable;
    }

    @Override
    public boolean getIsSquareRoundShape() {
        return this.isSquareRoundShape;
    }

    @Override
    public boolean getHasShapeSelection() {
        return this.hasShapeSelection;
    }

    @Override
    public void execute(ToolContext context) {
        this.canvaComponent = context.getCanvaComponent();
        this.graphics2D = context.getCanva().getGraphics2D();
        // Récupérer la couleur en fonction du type de clic
        Color color = null;
        if (context.getClick() == InputEvent.BUTTON1_DOWN_MASK) {
            this.isPrimaryColor = true;
            color = primaryColor;
        } else if (context.getClick() == InputEvent.BUTTON3_DOWN_MASK) {
            this.isPrimaryColor = false;
            color = secondaryColor;
        }
        if (color != null) {
            this.currentX = context.getCurrentX();
            this.currentY = context.getCurrentY();
            this.size = context.getSize();
            // Déclencher l'évènement focusLost si la JTextField n'est pas null
            if (this.jtextField != null) {
                focusLost(new FocusEvent(this.canvaComponent, 0));
            } else {
                // Créer une JTextField et la placer sur le canva à l'endroit souhaité
                this.jtextField = new JTextField();
                this.jtextField.setVisible(true);
                this.canvaComponent.setLayout(new FlowLayout());
                this.canvaComponent.add(this.jtextField);
                this.jtextField.addFocusListener(this);
                this.jtextField.addKeyListener(this);
                this.jtextField.requestFocus();
                this.jtextField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, size));
                this.jtextField.setForeground(color);
                this.jtextField.setBounds(currentX, currentY, size * 7, size);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ColorModel) {
            // Changer la couleur
            this.primaryColor = ((ColorModel) o).getPrimaryColor();
            this.secondaryColor = ((ColorModel) o).getSecondaryColor();
            // CHanger la taille du text
        } else if (o instanceof Toolbox) {
            this.size = ((Toolbox) o).getToolSize();
            if (this.jtextField != null && this.canvaComponent != null) {
                this.jtextField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, this.size));
                this.jtextField.setBounds(this.currentX, this.currentY, this.size * 7, this.size);
                this.jtextField.requestFocus();
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        // Vérifier que le JTextField ne soit pas null
        if (this.jtextField != null) {
            // Replacer la JTextField
            this.jtextField.setBounds(currentX, currentY, size * 7, size);
            // Mettre à jour la couleur de la JTextField
            if (this.isPrimaryColor) {
                this.jtextField.setForeground(this.primaryColor);
            } else {
                this.jtextField.setForeground(this.secondaryColor);
            }
            // Mettre sur le focus sur la JTextField
            this.jtextField.requestFocus();
        }
    }

    /**
     * Places the text on the canvas at the current text field position.
     */
    public void placeText() {
        // Placer le text de la JTextField sur le canva
        this.graphics2D.setFont(this.jtextField.getFont());
        this.graphics2D.setPaint(this.jtextField.getForeground());
        this.graphics2D.drawString(this.text, this.jtextField.getX(), this.jtextField.getY() + this.size);
        // Supprimer la JTextField et mettre à jour l'attribut à null pour pouvoir en placer des nouvelles
        this.canvaComponent.remove(this.jtextField);
        this.canvaComponent.repaint();
        this.jtextField = null;
        this.text = "";
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Vérifier que la JTextField est focus
        if (this.jtextField.isFocusable()) {
            // Ajoute un espace
            if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                this.text = "";
                this.canvaComponent.remove(this.jtextField);
                this.jtextField = null;
                this.canvaComponent.repaint();
            // Supprime un caractère
            } else if (e.getKeyChar() == '\u0008' || e.getKeyChar() == '\u007F') {
                if (this.text.length() != 0) {
                    this.text = this.text.substring(0, this.text.length() - 1);
                }
             // Si appui sur entrée : placer le texte
            } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                placeText();
            // Mettre à jour le texte
            } else if (Character.isLetter(e.getKeyChar()) || Character.isSpaceChar(e.getKeyChar())) {
                this.text += e.getKeyChar();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Returns the JTextField used for text input.
     *
     * @return the JTextField used for text input
     */
    public JTextField getJtextField() {
        return jtextField;
    }
}