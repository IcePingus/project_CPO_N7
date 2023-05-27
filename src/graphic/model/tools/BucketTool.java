package graphic.model.tools;

import graphic.model.canva.Pixel;

import javax.swing.*;

public class BucketTool implements ToolCommand {

    private String name;
    private Icon image;

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
    public void execute(Pixel pixel) {
        System.out.println("Salut bucket");
    }
}