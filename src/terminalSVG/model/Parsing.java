package terminalSVG.model;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import org.apache.batik.svggen.SVGGraphics2D;

import terminalSVG.model.SVGElement.CercleSVG;
import terminalSVG.model.SVGElement.PolygoneSVG;

public class Parsing {
	private List<String> parsedCommand;
	private String elementAction;
	private String elementName;
	private List<Double> coords;
	private String fillColor = "black"; //FAUDRA LES CONVERTIR EN COLOR
	private String strokeColor;
	private boolean fill = false;
	private SVGPreview svgPreview;

	public Parsing(SVGPreview svgp) {
		this.svgPreview = svgp;
	}

	public void parse(String input) {
		List<String> result = new ArrayList<>();
		List<Double> coordonnees = new ArrayList<>();


		if (input != null && !input.isEmpty()) {
			String[] elements = input.split(" ");
			for (int i = 0; i < elements.length; i++) {
				String element = elements[i].trim();
				result.add(element);
				if (element.equals("-s") && i + 1 < elements.length) {
					this.strokeColor = elements[i + 1].trim();
				}
				if (element.equals("-f") && i + 1 < elements.length) {
					this.fillColor = elements[i + 1].trim();
					this.fill = true;
				}
				//le mettre a false sinon (pb pour plusieurs utilisation de l'objet dans l'autre cas)
			}
		}

		this.parsedCommand = result;
		if (this.parsedCommand.size() > 1) {
			this.elementAction = result.get(0);
			this.elementName = result.get(1);
		}
		else {
			this.elementAction = result.get(0);
		}

		// Parcourir les éléments pour trouver les coordonnées
		for (String element : result) {
			try {
				double coordonnee = Double.parseDouble(element);
				coordonnees.add(coordonnee);
			} catch (NumberFormatException e) {
				// Ignorer les éléments qui ne sont pas des coordonnées
			}
		}
		this.coords = coordonnees;

		this.choix(svgPreview.getSVGGraphics());
	}

	public String getElementAction() {
		return elementAction;
	}

	public void setElementAction(String elementAction) {
		this.elementAction = elementAction;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public List<String> getParsedCommand() {
		return parsedCommand;
	}

	public List<Double> getCoords() {
		return coords;
	}

	public void setCoords(List<Double> coords) {
		this.coords = coords;
	}

	public void setParsedCommand(List<String> parsedCommand) {
		this.parsedCommand = parsedCommand;
	}

	public static Color convertStringToColor(String colorName) {
        Color color;
        
        // Conversion des noms de couleur prédéfinis
        switch (colorName.toLowerCase()) {
            case "black":
                color = Color.BLACK;
                break;
            case "white":
                color = Color.WHITE;
                break;
            case "red":
                color = Color.RED;
                break;
            case "green":
                color = Color.GREEN;
                break;
            case "blue":
                color = Color.BLUE;
                break;
            // Ajoutez d'autres noms de couleur prédéfinis si nécessaire
            
            default:
                // Si le nom de couleur n'est pas prédéfini, vous pouvez essayer de le convertir à partir de son code hexadécimal
                try {
                    color = Color.decode(colorName);
                } catch (NumberFormatException e) {
                    // En cas d'erreur de conversion, utilisez une valeur par défaut (par exemple, noir)
                    color = Color.BLACK;
                }
                break;
        }
        
        return color;
    }
	
	// <form> <nom> n*<coordonne> <-s> <couleur> <-f> <couleur>

	
	//methode avec switch pour choisir le traitement de la forme souhaité
	//il serait plus judicieux de lui passer l'objet SVGGraphics2D
		public void choix(SVGGraphics2D g2d) {
			switch (getElementAction()) {

			case "circle":	
				
				// Créer et dessiner un cercle avec le SVGGraphics2D
				CercleSVG cercle = new CercleSVG(getElementName(), coords.get(0), coords.get(1), coords.get(2),
						isFill(), convertStringToColor(getStrokeColor()), convertStringToColor(getFillColor()));
				//les coordonnées du centre puis la taille du rayon
				cercle.Dessiner(g2d);

				break;
			case "polygon":
				//Pour créé un polygone quelconque
				PolygoneSVG polygone = new PolygoneSVG(getElementName(),isFill(),convertStringToColor(getStrokeColor()),
						convertStringToColor(getFillColor()), getCoords());
				polygone.dessiner(g2d);
				break;

			case "clear":
				this.svgPreview.clear();
				break;

			case "help":
				//s'occuper de l'help simple si forme pas specifier
				break;
			default:
				//throw new ConfigurationException("Cette stratégie n'existe pas");
				System.out.println("Default");
			}
			this.svgPreview.updateCanvas(this.getElementAction()+ "-" +this.getElementName());
		}
}
