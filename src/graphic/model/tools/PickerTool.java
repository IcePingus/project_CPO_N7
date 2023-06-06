package graphic.model.tools;

import graphic.controller.ColorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class PickerTool implements ToolCommand {

    private final String name;
    private final Icon image;
    private final boolean isResizable;
    private final ColorController colorController;

    public PickerTool(ColorController colorController) {
        this.name = "Picker";
        this.image = new ImageIcon(getClass().getResource("/assets/images/picker.png"));
        this.isResizable = false;
        this.colorController = colorController;
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
    public void execute(int oldX, int oldY, int currentX, int currentY, BufferedImage bufferedImage, Graphics2D graphics2D, int click, int size, boolean square) {
        Color color = new Color(bufferedImage.getRGB(currentX, currentY));
        if(click == InputEvent.BUTTON1_DOWN_MASK) {
            this.colorController.setPrimaryColor(color);
        } else if(click == InputEvent.BUTTON3_DOWN_MASK) {
            this.colorController.setSecondaryColor(color);
        }
    }

    @Override
    public void update(Observable o, Object arg) { }
}