package terminalSVG.model;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;
import terminalSVG.model.SVGCommand.DrawShapeAction;
import terminalSVG.model.SVGCommand.SVGCommand;

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
    private Map<String, DrawShapeAction> elementsList;

    public SVGPreview() {
        // Créer un document SVG
        this.svgDocument = createSVGDocument();
        this.svgGeneratorContext = SVGGeneratorContext.createDefault(svgDocument);
        this.svgGraphics = new SVGGraphics2D(svgGeneratorContext, true);
        this.elementsList = new Hashtable<>();
    }

    private SVGDocument createSVGDocument() {
        DOMImplementation domImpl = org.apache.batik.anim.dom.SVGDOMImplementation.getDOMImplementation();
        String svgNS = org.apache.batik.anim.dom.SVGDOMImplementation.SVG_NAMESPACE_URI;
        return (SVGDocument) domImpl.createDocument(svgNS, "svg", null);
    }

    public void setCanvasSize(int width, int height) {
        svgGraphics.setSVGCanvasSize(new Dimension(width, height));
        this.updateCanvas("Changement de taille");
    }

    public void addElement(String eltName,DrawShapeAction elt) {
        this.elementsList.put(eltName,elt);
        update();
    }

    public void delElement(String eltName) throws IllegalArgumentException {
        if(!this.elementsList.containsKey(eltName)){
            throw new IllegalArgumentException("Aucun élement SVG ne correspond à votre requête");
        }
        this.elementsList.remove(eltName);
        update();
    }

    public void update(){
        clearCanva();
        System.out.println(this.elementsList.toString());
        for (Map.Entry<String, DrawShapeAction> entry : elementsList.entrySet()) {
            System.out.println(entry);
            entry.getValue().draw(this);
            updateCanvas(  entry.getValue() + "-" + entry.getKey());
        }
        updateCanvas("END");
        System.out.println(getSVGCode());
    }

    public void updateCanvas(String comment) {
        // Mettre à jour le document SVG
        this.svgGeneratorContext.setComment(comment);
        Element root = svgDocument.getDocumentElement();
        this.svgGraphics.getRoot(root);
        this.svgGraphics = new SVGGraphics2D(svgGeneratorContext, true);


        this.setChanged();
        this.notifyObservers();
    }

    public void clearList() {
        this.elementsList.clear();
        update();
    }

    public void clearCanva() {
        Node root = svgDocument.getDocumentElement();
        while (root.hasChildNodes()) {
            root.removeChild(root.getFirstChild());
        }
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
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Result output = new StreamResult(new File(filename));
        Source input = new DOMSource(svgDocument);
        transformer.transform(input, output);
    }

    public SVGDocument getSvgDocument() {
        return this.svgDocument;
    }

    public SVGGraphics2D getSVGGraphics() {
        return this.svgGraphics;
    }

    public void setDefaultColor(Color color) {
    }
}
