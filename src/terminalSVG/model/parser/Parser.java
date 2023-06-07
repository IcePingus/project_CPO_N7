package terminalSVG.model.parser;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class Parser {
    private static final Map<String, CommandParser> commandParsers = new HashMap<>();

    static {
        //commandParsers.put("clear", new GeneralCommandParser());
        //commandParsers.put("color", new GeneralCommandParser());
        //commandParsers.put("size", new GeneralCommandParser());
        commandParsers.put("rectangle", new ElementCommandParser());
        commandParsers.put("circle", new ElementCommandParser());
        commandParsers.put("oval", new ElementCommandParser());
        commandParsers.put("square", new ElementCommandParser());
        commandParsers.put("polygon", new ElementCommandParser());

    }

    public static Map<String, Object> parse(String input) throws IllegalArgumentException {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("[Erreur] La commande est vide.");
        }

        String[] elements = input.split(" ");
        String command = elements[0].trim();

        if (commandParsers.containsKey(command)) {
            CommandParser commandParser = commandParsers.get(command);
            return commandParser.parseCommand(elements);
        } else {
            throw new IllegalArgumentException("[Erreur] La commande n'est pas prise en charge : " + command);
        }
    }

    static class ElementCommandParser implements CommandParser {
        @Override
        public Map<String, Object> parseCommand(String[] elements) throws IllegalArgumentException {
            List<Double> coordinates = new ArrayList<>();
            Map<String, Object> instruction = new Hashtable<>();

            if (elements.length < 2) {
                throw new IllegalArgumentException("[Erreur] La commande doit spécifier un nom pour l'élément.");
            }

            instruction.put("elementAction", elements[0]);
            instruction.put("elementName", elements[1]);

            for (int i = 2; i < elements.length; i++) {
                String element = elements[i].trim();

                if (element.equals("-s") && i + 1 < elements.length) {
                    instruction.put("strokeColor", convertStringToColor(elements[i + 1].trim()));
                    i++;  // Ignore the next argument since it was used for the stroke color
                } else if (element.equals("-f") && i + 1 < elements.length) {
                    instruction.put("fillColor", convertStringToColor(elements[i + 1].trim()));
                    i++;  // Ignore the next argument since it was used for the fill color
                } else {
                    try {
                        coordinates.add(Double.parseDouble(element));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("[Erreur] La commande contient un argument invalide : " + element);
                    }
                }
            }

            if (coordinates.isEmpty()) {
                throw new IllegalArgumentException("[Erreur] Les coordonnées sont manquantes pour la commande : " + elements[0].trim());
            }

            instruction.put("coords", coordinates);
            instruction.put("elementActionType","setter");
            System.out.println(instruction);
            return instruction;
        }
    }

    static class GeneralCommandParser implements CommandParser {
        @Override
        public Map<String, Object> parseCommand(String[] elements) throws IllegalArgumentException {
            Map<String, Object> instruction = new Hashtable<>();
            String command = elements[0].trim();

            instruction.put("elementAction", command);

            // Rechercher la classe de commande correspondante
            String className = command.substring(0, 1).toUpperCase() + command.substring(1);
            try {
                Class<?> commandClass = Class.forName("terminalSVG.model.SVGCommand." + className + "SVG");
                Method parseMethod = commandClass.getDeclaredMethod("parseCommand", Map.class, String[].class);
                parseMethod.invoke(null, instruction, elements);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException("[Erreur] La commande n'est pas prise en charge : " + command);
            }
            instruction.put("elementActionType","modifier");
            System.out.println(instruction);
            return instruction;
        }
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
