package terminalSVG.model.SVGCommand;

/**
 * The type Point.
 */
public class Point {
    private double x;        // abscisse
    private double y;        // ordonnée

    /**
     * Instantiates a new Point.
     *
     * @param vx the coordinate x of the point
     * @param vy the coordinate y of the point
     */
    public Point(double vx, double vy) {
        this.x = vx;
        this.y = vy;
    }

    /**
     * Gets coordinate x.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets coordinate y.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets coordinate x.
     *
     * @param vx the vx
     */
    public void setX(double vx) {
        this.x = vx;
    }

    /**
     * Sets coordinate y.
     *
     * @param vy the vy
     */
    public void setY(double vy) {
        this.y = vy;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    /**
     * Translate the point following dx and dy.
     *
     * @param dx the x translation
     * @param dy the y translation
     */
    public void translater(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
}
