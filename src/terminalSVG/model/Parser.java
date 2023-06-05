package terminalSVG.model;

import java.awt.Color;
import java.util.*;

public class Parser {

    public static Map<String, Object> parse(String input, ArrayList<String> simpleCommands) throws IllegalArgumentException {
        List<Double> coordonnees = new ArrayList<>();
        Map<String, Object> instruction = new Hashtable<>();

        if (input != null && !input.isEmpty()) {
            String[] elements = input.split(" ");
            if (simpleCommands.contains(elements[0])) {
                if (elements.length < 2) {
                    throw new IllegalArgumentException("La commande doit spécifier un nom pour l'élément.");
                }

                instruction.put("elementAction", elements[0]);
                instruction.put("elementName", elements[1]);

                for (int i = 2; i < elements.length; i++) {
                    String element = elements[i].trim();

                    if (element.equals("-s") && i + 1 < elements.length) {
                        instruction.put("strokeColor", convertStringToColor(elements[i + 1].trim()));
                        i++;  // Ignorer l'argument suivant puisqu'il a été utilisé pour la couleur de contour
                    } else if (element.equals("-f") && i + 1 < elements.length) {
                        instruction.put("fillColor", convertStringToColor(elements[i + 1].trim()));
                        i++;  // Ignorer l'argument suivant puisqu'il a été utilisé pour la couleur de remplissage
                    } else {
                        try {
                            coordonnees.add(Double.parseDouble(element));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("La commande contient un argument invalide : " + element);
                        }
                    }
                }

                if (coordonnees.isEmpty()) {
                    throw new IllegalArgumentException("Les coordonnées sont manquantes pour la commande : " + elements[0].trim());
                }

                instruction.put("coords", coordonnees);
            } else {
                instruction.put("elementAction", elements[0].trim());
                switch (elements[0].trim()) {
                    case "clear":
                        break;
                    case "color":
                        if (elements.length != 2) {
                            throw new IllegalArgumentException("La commande doit spécifier une couleur.");
                        }
                        instruction.put("fillColor", convertStringToColor(elements[1].trim()));
                        break;
                    case "size":
                        if (elements.length != 3) {
                            throw new IllegalArgumentException("La commande doit spécifier une taille.");
                        }
                        instruction.put("newSizeWidth", elements[1].trim());
                        instruction.put("newSizeHeight", elements[2].trim());
                        break;
                    default:
                        throw new IllegalArgumentException("La commande n'est pas prise en charge : " + elements[0].trim());
                }
            }
        } else {
            throw new IllegalArgumentException("La commande est vide.");
        }
        return instruction;
    }

    public static Color convertStringToColor(String colorName) {
        Color color;

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
            case "yellow":
                color = Color.YELLOW;
                break;
            case "cyan":
                color = Color.CYAN;
                break;
            case "magenta":
                color = Color.MAGENTA;
                break;
            case "gray":
                color = Color.GRAY;
                break;
            case "darkgray":
                color = Color.DARK_GRAY;
                break;
            case "lightgray":
                color = Color.LIGHT_GRAY;
                break;
            case "orange":
                color = Color.ORANGE;
                break;
            case "pink":
                color = Color.PINK;
                break;
            default:
                try {
                    color = Color.decode(colorName);
                } catch (NumberFormatException e) {
                    color = Color.BLACK;
                }
                break;
        }
        return color;
    }
}
