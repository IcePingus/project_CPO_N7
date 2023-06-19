package terminalSVG.model.SVGCommand;

import terminalSVG.model.SVGPreview;
/**
 * The SVGCommand interface represents a command for manipulating SVG elements.
 *
 * @author Team 3
 */
public interface SVGCommand {
    /**
     * Gets the name of the SVG command.
     *
     * @return The name of the SVG command.
     */
    String getName();
    /**
     * Gets the help information for using the SVG command.
     *
     * @return The help information for the SVG command.
     */
    String getHelp();
    /**
     * Executes the SVG command on the given SVGPreview object.
     *
     * @param svgPreview The SVGPreview object on which to execute the SVG command.
     * @param shapeName  The name of the shape to apply the command to.
     */
    String execute(SVGPreview svgPreview);
}