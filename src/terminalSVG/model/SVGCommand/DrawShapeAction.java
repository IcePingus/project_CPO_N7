package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;

import java.awt.*;
import java.util.Map;

/**
 * The type Draw shape action.
 *
 * @author Team 3
 */
public abstract class DrawShapeAction implements SVGCommand {

    /**
     * The Name of the shape.
     */
    protected String name;
    /**
     * isFill the shape is filled or not.
     */
    protected boolean isFill;
    /**
     * The Stroke color.
     */
    protected Color strokeColor;
    /**
     * The Fill color.
     */
    protected Color fillColor;

    /**
     * Instantiates a new Draw shape action.
     *
     * @param name        the name of the shape
     * @param isFill      if the shape is going to be filled
     * @param strokeColor the stroke color
     * @param fillColor   the fill color
     */
    public DrawShapeAction(String name, boolean isFill, Color strokeColor, Color fillColor) {
        this.name = name;
        this.isFill = isFill;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    public DrawShapeAction() {
    }

    // Implémentez les méthodes de l'interface SVGCommand
    @Override
    public String getName() {
        return name;
    }

    @Override
    public abstract String getHelp();

    @Override
    public String execute(SVGPreview svgPreview) {
        svgPreview.addElement( this.getName(),this);
        return "[+] " + this.getName() + "\n";
    }

    /**
     * Draw the shape on the canva.
     *
     * @param svgPreview the svg preview
     */
    public abstract void draw(SVGPreview svgPreview);

    /**
     * Translate the shape following coodinates.
     *
     * @param dx the translation following x
     * @param dy the translation following y
     */
    public abstract void translate(Double dx, Double dy);

    public abstract void resize(Double newWidth, Double newHeight);

    /**
     * Sets a new name to the shape.
     *
     * @param newName the new name of the shape
     */

    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Gets stroke color.
     *
     * @return the stroke color
     */
    public Color getStrokeColor() {
        return strokeColor;
    }

    /**
     * Sets new stroke color of the shape.
     *
     * @param strokeColor the new stroke color
     */
    public void setStrokeColor(Color strokeColor) {
        if (strokeColor != null) this.strokeColor = strokeColor;
    }

    /**
     * Is the shape is filled.
     *
     * @return the boolean
     */
    public boolean isFill() {
        return isFill;
    }

    /**
     * Sets fill.
     *
     * @param fill the fill
     */
    public void setFill(boolean fill) {
        isFill = fill;
    }

    /**
     * Gets fill color.
     *
     * @return the fill color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets fill color.
     *
     * @param fillColor the fill color
     */
    public void setFillColor(Color fillColor) {
        if (fillColor != null) {
            this.fillColor = fillColor;
            this.isFill = true;
        }
    }
}