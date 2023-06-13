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
import java.util.Map;
import java.util.Observable;

public class SVGPreview extends Observable {
    private SVGGraphics2D svgGraphics; // Contexte graphique pour dessiner des éléments SVG
    private SVGGeneratorContext svgGeneratorContext; // Contexte de génération SVG
    private SVGDocument svgDocument; // Document SVG
    private Map<String, DrawShapeAction> shapeList;
    private Color defaultColor;

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

    public void addElement(String shapeName, DrawShapeAction shape) {
        this.shapeList.put(shapeName, shape);
        buildShapes();
    }

    public void delElement(String shapeName) throws IllegalArgumentException {
        if (!this.shapeList.containsKey(shapeName)) {
            throw new IllegalArgumentException("Aucun élement SVG ne correspond à votre requête");
        }
        this.shapeList.remove(shapeName);
        buildShapes();
    }

    public void buildShapes() {
        clearSVGDocument();
        for (Map.Entry<String, DrawShapeAction> shape : shapeList.entrySet()) {
            shape.getValue().draw(this);
            updateSVGDocument(shape.getValue().getClass().getSimpleName() + " : " + shape.getValue().getName());
        }
        updateSVGDocument("END");
    }

    public void clearShapeList() throws IllegalArgumentException {
        if (this.shapeList.isEmpty()) {
            throw new IllegalArgumentException("Le canva est vide");
        }
        this.shapeList.clear();
        buildShapes();
    }

    public void updateSVGDocument(String comment) {
        // Mettre à jour le document SVG
        this.svgGeneratorContext.setComment(comment);
        Element root = svgDocument.getDocumentElement();
        this.svgGraphics.getRoot(root);
        this.svgGraphics = new SVGGraphics2D(svgGeneratorContext, true);
        this.setChanged();
        this.notifyObservers();
    }

    public void clearSVGDocument() {
        Node root = svgDocument.getDocumentElement();
        while (root.hasChildNodes()) {
            root.removeChild(root.getFirstChild());
        }
    }

    public void setCanvasSize(int width, int height) {
        svgGraphics.setSVGCanvasSize(new Dimension(width, height));
        this.updateSVGDocument("Changement de taille");
    }

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

    public void saveSVG(String filename) throws TransformerException {
        filename = filename.endsWith(".svg") ? filename : filename + ".svg";

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File("./export/" + filename));
        Source input = new DOMSource(svgDocument);
        transformer.transform(input, output);
    }

    public SVGDocument getSvgDocument() {
        return this.svgDocument;
    }

    public SVGGraphics2D getSVGGraphics() {
        return this.svgGraphics;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }
}