package terminalSVG.model;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.StringWriter;
import java.util.Observable;

public class SVGPreview extends Observable {
    private SVGGraphics2D svgGraphics; // Contexte graphique pour dessiner des éléments SVG
    private SVGGeneratorContext svgGeneratorContext; // Contexte de génération SVG
    private SVGDocument svgDocument; // Document SVG

    public SVGPreview() {
        // Créer un document SVG
        this.svgDocument = createSVGDocument();
        this.svgGeneratorContext = SVGGeneratorContext.createDefault(svgDocument);
        this.svgGeneratorContext.setComment("#Pinte Studio - TerminalSVG#");
        this.svgGraphics = new SVGGraphics2D(svgGeneratorContext, true);
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

    private void updateCanvas(String comment) {
        // Mettre à jour le document SVG
        Element root = svgDocument.getDocumentElement();
        this.svgGraphics.getRoot(root);

        this.svgGeneratorContext.setComment(comment);
        this.svgGraphics = new SVGGraphics2D(svgGeneratorContext, true);

        this.setChanged();
        this.notifyObservers();
    }

    // Parser temporaire (fonctionnel)
    public void command(String cmd) {
        if (cmd.contains("circle1")) {
            this.drawCircle(1000, 800, 50, 50, Color.red);
        }
        if (cmd.contains("circle2")) {
            this.drawCircle(60, 0, 50, 50, Color.green);
        }
        if (cmd.contains("clear")) {
            this.clear();
        }
    }

    // Element dessinable temporaire (fonctionnel)
    public void drawCircle(double x, double y, double width, double height, Color color) {
        Shape circle = new Ellipse2D.Double(x, y, width, height);
        this.svgGraphics.setPaint(color);
        this.svgGraphics.fill(circle);
        this.updateCanvas("Cercle");
    }

    public void clear() {
        // Supprimer tous les éléments SVG du document
        Node root = svgDocument.getDocumentElement();
        while (root.hasChildNodes()) {
            root.removeChild(root.getFirstChild());
        }

        this.setChanged();
        this.notifyObservers();
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
}
