package graphic.model.canva;

public class Canva {

    private Pixel[][] toile;

    public Canva(int width, int height, int panelWidth, int panelHeight) {

        this.toile = new Pixel[width][height];

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                this.toile[w][h] = new Pixel(width, height, panelWidth, panelHeight);
            }
        }

    }

    public Pixel getPixel(int w, int h) {
        return this.toile[w][h];
    }

}
