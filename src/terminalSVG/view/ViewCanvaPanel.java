package terminalSVG.view;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import org.apache.batik.swing.*;
import org.apache.batik.svggen.*;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.*;
import org.w3c.dom.svg.*;

public class ViewCanvaPanel extends JPanel {
    private SVGGraphics2D g;
    private SVGDocument doc;
    private JSVGCanvas canvas;

    public ViewCanvaPanel() {
        // Create an SVG document.
        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        doc = (SVGDocument) impl.createDocument(svgNS, "svg", null);

        // Create a converter for this document.
        g = new SVGGraphics2D(doc);

        // Create SVG canvas.
        canvas = new JSVGCanvas();
        this.add(canvas);
        this.setLayout(new GridLayout());
    }

    public void drawCircle(double x, double y, double width, double height, Color color) {
        Shape circle = new Ellipse2D.Double(x, y, width, height);
        g.setPaint(color);
        g.fill(circle);
        updateCanvas();
    }

    public void clear() {
        g = new SVGGraphics2D(doc);
        updateCanvas();
    }

    public void setCanvasSize(int width, int height) {
        g.setSVGCanvasSize(new Dimension(width, height));
        updateCanvas();
    }

    private void updateCanvas() {
        // Update the SVG document.
        Element root = doc.getDocumentElement();
        g.getRoot(root);

        // Update the SVG canvas.
        canvas.setSVGDocument(doc);
    }

    public static void main(String[] args) throws InterruptedException {
        ViewCanvaPanel demo = new ViewCanvaPanel();
        JFrame f = new JFrame();
        demo.setCanvasSize(500,500);
        f.getContentPane().add(demo);
        f.pack();
        f.setVisible(true);

        demo.drawCircle(0, 0, 50, 50, Color.red);
        demo.drawCircle(60, 0, 50, 50, Color.green);
        demo.drawCircle(120, 0, 50, 50, Color.blue);

        Thread.sleep(1000);
        demo.drawCircle(120, 10, 50, 50, Color.YELLOW);
        demo.updateCanvas();
    }
}