package graphic.model.tools;

import graphic.model.color.ColorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class TextTool implements ToolCommand, FocusListener, KeyListener {
    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final boolean isSquareRoundShape;
    private int size;
    private Graphics2D graphics2D;
    private JComponent jComponent;
    private Color primaryColor;
    private Color secondaryColor;
    private boolean isPrimaryColor;
    private int currentX, currentY;
    private JTextField jtextField;
    private String text;

    public TextTool() {
        this.name = "Text";
        this.image = new ImageIcon(getClass().getResource("/assets/images/text.png"));
        this.isResizable = true;
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;
        this.text = "";
        this.size = 10;
        this.isSquareRoundShape = false;
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
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square, JComponent jComponent) {
        this.jComponent = jComponent;
        this.graphics2D = graphics2D;
        Color color = null;
        if (click == InputEvent.BUTTON1_DOWN_MASK) {
            this.isPrimaryColor = true;
            color = primaryColor;
        } else if (click == InputEvent.BUTTON3_DOWN_MASK) {
            this.isPrimaryColor = false;
            color = secondaryColor;
        }
        if (color != null) {
            this.currentX = currentX;
            this.currentY = currentY;
            this.size = size;
            if (this.jtextField != null) {
                focusLost(new FocusEvent(this.jComponent, 0));
            } else {
                this.jtextField = new JTextField();
                this.jtextField.setVisible(true);
                jComponent.setLayout(new FlowLayout());
                jComponent.add(this.jtextField);
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
            this.primaryColor = ((ColorModel) o).getPrimaryColor();
            this.secondaryColor = ((ColorModel) o).getSecondaryColor();
        } else if (o instanceof Toolbox) {
            this.size = ((Toolbox) o).getToolSize();
            if (this.jtextField != null && this.jComponent != null) {
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
        if (this.jtextField != null) {
            this.jtextField.setBounds(currentX, currentY, size * 7, size);
            if (this.isPrimaryColor) {
                this.jtextField.setForeground(this.primaryColor);
            } else {
                this.jtextField.setForeground(this.secondaryColor);
            }
            this.jtextField.requestFocus();
        }
    }

    public void placeText() {
        this.graphics2D.setFont(this.jtextField.getFont());
        this.graphics2D.setPaint(this.jtextField.getForeground());
        this.graphics2D.drawString(this.text, this.jtextField.getX(), this.jtextField.getY() + this.size);
        this.jComponent.remove(this.jtextField);
        this.jComponent.repaint();
        this.jtextField = null;
        this.text = "";
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (this.jtextField.isFocusable()) {
            if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                this.text = "";
                this.jComponent.remove(this.jtextField);
                this.jtextField = null;
                this.jComponent.repaint();
            } else if (e.getKeyChar() == '\u0008' || e.getKeyChar() == '\u007F') {
                if (this.text.length() != 0) {
                    this.text = this.text.substring(0, this.text.length() - 1);
                }
            } else if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                placeText();
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

    public JTextField getJtextField() {
        return jtextField;
    }
}