package graphic.model;

import graphic.model.canva.Canva;

public class ToolContext {

    private int oldX;
    private int oldY;
    private int currentX;
    private int currentY;
    private int click;
    private int size;
    private boolean isSquare;
    private boolean isFirstPoint;
    private Canva canva;

    public int getOldX() {
        return this.oldX;
    }

    public int getOldY() {
        return this.oldY;
    }

    public int getCurrentX() {
        return this.currentX;
    }

    public int getCurrentY() {
        return this.currentY;
    }

    public int getClick() {
        return this.click;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isSquare() {
        return this.isSquare;
    }

    public boolean isFirstPoint() {
        return this.isFirstPoint;
    }

    public Canva getCanva() {
        return this.canva;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSquare(boolean square) {
        this.isSquare = square;
    }

    public void setFirstPoint(boolean firstPoint) {
        this.isFirstPoint = firstPoint;
    }

    public void setCanva(Canva canva) {
        this.canva = canva;
    }
}
