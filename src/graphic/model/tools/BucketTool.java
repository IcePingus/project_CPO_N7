package graphic.model.tools;

import javax.swing.*;
import java.awt.*;

public class BucketTool implements ToolCommand {

    private final String name;
    private final Icon image;

    public BucketTool() {
        this.name = "Bucket";
        this.image = new ImageIcon(getClass().getResource("/assets/images/bucket.png"));
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
    public void execute(int oldX, int oldY, int currentX, int currentY, Graphics2D graphics2D) {
        System.out.println("Salut bucket");
    }
}