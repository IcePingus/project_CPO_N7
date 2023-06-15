package terminalSVG.model;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;
import terminalSVG.model.SVGCommand.DrawShapeAction;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
/**
 * The SVGPreview class represents the preview of an SVG image.
 * It provides methods for managing shapes, translating elements, updating the SVG document,
 * generating SVG code, and saving the SVG file.
 * It extends the Observable class to notify observers of changes.
 */
public class SVGPreview extends Observable {
    private SVGGraphics2D svgGraphics; // Contexte graphique pour dessiner des éléments SVG
    private SVGGeneratorContext svgGeneratorContext; // Contexte de génération SVG
    private SVGDocument svgDocument; // Document SVG
    private Map<String, DrawShapeAction> shapeList;
    private Color defaultColor;
    /**
     * Constructs an SVGPreview object.
     * Initializes the SVG document, graphics context, and shape list.
     */
    public SVGPreview() {
        // Créer un document SVG
        this.svgDocument = createSVGDocument();
        this.svgGeneratorContext = SVGGeneratorContext.createDefault(svgDocument);
        this.svgGraphics = new SVGGraphics2D(svgGeneratorContext, true);
        this.shapeList = new Hashtable<>();
    }
    private SVGDocument createSVGDocument() {
        DOMImplementation domImpl = org.apache.batik.anim.dom.SVGDOMImplementation.getDOMImplementation();
        String svgNS = org.apache.batik.anim.dom.SVGDOMImplementation.SVG_NAMESPACE_URI;
        return (SVGDocument) domImpl.createDocument(svgNS, "svg", null);
    }
    /**
     * Adds a shape to the SVG preview.
     *
     * @param shapeName The name of the shape.
     * @param shape     The shape to be added.
     */
    public void addElement(String shapeName,DrawShapeAction shape) {
        this.shapeList.put(shapeName,shape);
        buildShapes();
    }
    /**
     * Deletes a shape from the SVG preview.
     *
     * @param shapeName The name of the shape to be deleted.
     * @throws IllegalArgumentException if the shape does not exist.
     */
    public void delElement(String shapeName) throws IllegalArgumentException {
        if(!this.shapeList.containsKey(shapeName)){
            throw new IllegalArgumentException("Aucun élement SVG ne correspond à votre requête");
        }
        this.shapeList.remove(shapeName);
        buildShapes();
    }
    /**
     * Translates a shape in the SVG preview.
     *
     * @param shapeName The name of the shape to be translated.
     * @param dx        The translation distance in the x-axis.
     * @param dy        The translation distance in the y-axis.
     * @throws IllegalArgumentException if the shape does not exist.
     */
    public void translateElement(String shapeName, Double dx, Double dy)  {
        if(!this.shapeList.containsKey(shapeName)){
            throw new IllegalArgumentException("Aucun élement SVG ne correspond à votre requête");
        }
        DrawShapeAction shape = shapeList.get(shapeName);
        shape.translate(dx,dy);
        buildShapes();
    }

    public void resizeElement(String shapeName,  Map<String, Object> sizes)  {
        if(!this.shapeList.containsKey(shapeName)){
            throw new IllegalArgumentException("Aucun élement SVG ne correspond à votre requête");
        }
        DrawShapeAction shape = shapeList.get(shapeName);
        shape.resize(sizes);
        buildShapes();
    }

    /**
     * Builds the shapes in the SVG document.
     * Clears the SVG document, draws each shape, and updates the document.
     */
    public void buildShapes(){
        clearSVGDocument();
        for (Map.Entry<String, DrawShapeAction> shape : shapeList.entrySet()) {
            shape.getValue().draw(this);
            updateSVGDocument(shape.getValue().getClass().getSimpleName() + " : " + shape.getValue().getName());
        }
        updateSVGDocument("END");
    }
    /**
     * Clears the list of shapes.
     *
     * @throws IllegalArgumentException if the canvas is empty.
     */
    public void clearShapeList() throws IllegalArgumentException {
        if(this.shapeList.isEmpty()) {
            throw new IllegalArgumentException("Le canva est vide");
        }
        this.shapeList.clear();
        buildShapes();
    }
    /**
     * Updates the SVG document with a comment.
     *
     * @param comment The comment to be added to the SVG document.
     */
    public void updateSVGDocument(String comment) {
        // Mettre à jour le document SVG
        this.svgGeneratorContext.setComment(comment);
        Element root = svgDocument.getDocumentElement();
        this.svgGraphics.getRoot(root);
        this.svgGraphics = new SVGGraphics2D(svgGeneratorContext, true);
        this.setChanged();
        this.notifyObservers();
    }
    /**
     * Clears the SVG document.
     */
    public void clearSVGDocument() {
        Node root = svgDocument.getDocumentElement();
        while (root.hasChildNodes()) {
            root.removeChild(root.getFirstChild());
        }
    }
    /**
     * Renames a shape in the SVG preview.
     *
     * @param shapeName The current name of the shape.
     * @param newName   The new name for the shape.
     * @throws IllegalArgumentException if the shape does not exist.
     */
    public void renameElement(String shapeName, String newName) throws IllegalArgumentException {
        if(!this.shapeList.containsKey(shapeName)){
            throw new IllegalArgumentException("Aucun élement SVG ne correspond à votre requête");
        }
        shapeList.get(shapeName).setName(newName);
        addElement(newName,shapeList.get(shapeName));
        delElement(shapeName);
        buildShapes();
    }
    /**
     * Sets the canvas size of the SVG preview.
     *
     * @param width  The width of the canvas.
     * @param height The height of the canvas.
     */
    public void setCanvasSize(int width, int height) {
        svgGraphics.setSVGCanvasSize(new Dimension(width, height));
        this.updateSVGDocument("Changement de taille");
    }
    /**
     * Generates the SVG code for the SVG preview.
     *
     * @return The SVG code as a string.
     */
    public String getSVGCode() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // Définir les propriétés de sortie pour conserver la mise en forme du code SVG
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(svgDocument), new StreamResult(writer));
            return writer.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Saves the SVG preview as an SVG file.
     *
     * @param filename The name of the file to be saved.
     * @throws TransformerException if an error occurs during the transformation.
     */
    public void saveSVG(String filename) throws TransformerException {
        filename = filename.endsWith(".svg") ? filename : filename + ".svg";

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File("./export/" + filename));
        Source input = new DOMSource(svgDocument);
        transformer.transform(input, output);
    }
    /**
     * Returns the SVG document of the SVG preview.
     *
     * @return The SVG document.
     */
    public SVGDocument getSvgDocument() {
        return this.svgDocument;
    }
    /**
     * Returns the SVG graphics context of the SVG preview.
     *
     * @return The SVG graphics context.
     */
    public SVGGraphics2D getSVGGraphics() {
        return this.svgGraphics;
    }
    /**
     * Returns the default color for shapes in the SVG preview.
     *
     * @return The default color.
     */
    public Color getDefaultColor() {
        return defaultColor;
    }
    /**
     * Sets the default color for shapes in the SVG preview.
     *
     * @param defaultColor The default color to be set.
     */
    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }
    /**
     * Sets a new stroke and fill color for a shape in the SVG preview.
     *
     * @param scolor      The new stroke color.
     * @param fcolor      The new fill color.
     * @param elementName The name of the shape.
     */
    public void setNewColorShape(Color scolor, Color fcolor, String elementName) {
        DrawShapeAction shape = getShapeByName(elementName);
        shape.setStrokeColor(scolor);
        shape.setFillColor(fcolor);
        this.buildShapes();
    }
    /**
     * Returns the shape in the SVG preview with the given name.
     *
     * @param name The name of the shape.
     * @return The shape with the given name.
     * @throws IllegalArgumentException if the shape does not exist.
     */
    public DrawShapeAction getShapeByName(String name) {
        if (!(this.shapeList.containsKey(name))) throw new IllegalArgumentException("Aucun élement SVG ne correspond à votre requête");
        return this.shapeList.get(name);
    }
}