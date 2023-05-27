package graphic.model.canva;

import javax.swing.*;
import java.awt.*;

public class Pixel extends JButton {

    public Pixel(int width, int height, int panelWidth, int panelHeight) {
        this.setBackground(Color.WHITE);
        this.setModel(new StateModel());
        this.setSize(width, height, panelWidth, panelHeight);
    }

    public void setSize(int width, int height, int panelWidth, int panelHeight) {
        int size;
        if (width > height) {
            size = (int) (panelWidth / (width * 2.25));
        } else {
            size = (int) (panelHeight / (height * 2.25));
        }
        this.setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
    }

    public class StateModel extends DefaultButtonModel    {

        @Override
        public boolean isPressed() {
            return false;
        }

        @Override
        public boolean isRollover() {
            return false;
        }

        @Override
        public void setRollover(boolean b) {
            //NOOP
        }

    }

}
