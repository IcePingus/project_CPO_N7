package terminalSVG.model.parser;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * The Parser class provides static methods for parsing input commands in SVG terminal.
 * It supports parsing both element-specific commands and general commands using reflection.
 *
 * @author Team 3
 */
public class Parser {
    private static Map<String, CommandParser> commandParsers = new HashMap<>();

    /**
     * Parses the input command and returns a map containing the parsed command.
     *
     * @param input        The input command to parse.
     * @param setterList   The list of setter commands.
     * @param modifierList The list of modifier commands.
     * @return A map containing the parsed command.
     * @throws IllegalArgumentException If the command is invalid or contains errors.
     */
    public static Map<String, Object> parse(String input, List<String> setterList, List<String> modifierList) throws IllegalArgumentException {
        for (String commands : setterList) {
            Parser.commandParsers.put(commands, new ElementCommandParser());
        }
        for (String commands : modifierList) {
            Parser.commandParsers.put(commands, new GeneralCommandParser());
        }

        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("La commande est vide.");
        }

        String[] elements = input.split(" ");
        String command = elements[0].trim();

        if (commandParsers.containsKey(command)) {
            CommandParser commandParser = commandParsers.get(command);
            return commandParser.parseCommand(elements);
        } else {
            throw new IllegalArgumentException("La commande n'est pas prise en charge : " + command);
        }
    }

    /**
     * A command parser for element-specific commands.
     */
    static class ElementCommandParser implements CommandParser {
        /**
         * Parses an element-specific command and returns a map containing the parsed command.
         *
         * @param elements The elements of the command.
         * @return A map containing the parsed command.
         * @throws IllegalArgumentException If the command is invalid or contains errors.
         */
        @Override
        public Map<String, Object> parseCommand(String[] elements) throws IllegalArgumentException {
            List<Double> coordinates = new ArrayList<>();
            Map<String, Object> instruction = new Hashtable<>();

            if (elements.length < 2) {
                throw new IllegalArgumentException("La commande doit spécifier un nom pour l'élément.");
            }

            instruction.put("elementAction", elements[0]);
            instruction.put("elementName", elements[1]);

            for (int i = 2; i < elements.length; i++) {
                String element = elements[i].trim();

                if (element.equals("-s") && i + 1 < elements.length) {
                    instruction.put("strokeColor", convertStringToColor(elements[i + 1].trim()));
                    i++;
                } else if (element.equals("-f") && i + 1 < elements.length) {
                    instruction.put("fillColor", convertStringToColor(elements[i + 1].trim()));
                    i++;
                } else {
                    try {
                        coordinates.add(Double.parseDouble(element));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("La commande contient un argument invalide : " + element);
                    }
                }
            }

            if (coordinates.isEmpty()) {
                throw new IllegalArgumentException("Les coordonnées sont manquantes pour la commande : " + elements[0].trim());
            }

            instruction.put("coords", coordinates);
            instruction.put("elementActionType", "setter");
            return instruction;

        }
    }

    /**
     * A command parser for general commands.
     */
    static class GeneralCommandParser implements CommandParser {
        /**
         * Parses a general command and returns a map containing the parsed command.
         *
         * @param elements The elements of the command.
         * @return A map containing the parsed command.
         * @throws IllegalArgumentException If the command is invalid or contains errors.
         */
        @Override
        public Map<String, Object> parseCommand(String[] elements) {
            Map<String, Object> instruction = new Hashtable<>();
            String command = elements[0].trim();
            instruction.put("elementAction", command);

            // Rechercher la classe de commande correspondante
            String className = command.substring(0, 1).toUpperCase() + command.substring(1);
            try {
                Class<?> commandClass = Class.forName("terminalSVG.model.SVGCommand." + className + "SVG");
                Method parseMethod = commandClass.getDeclaredMethod("parseCommand", Map.class, String[].class);

                try {
                    parseMethod.invoke(null, instruction, elements);
                    instruction.put("elementActionType", "modifier");
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof IllegalArgumentException) {
                        throw (IllegalArgumentException) cause;
                    } else {
                        throw new IllegalArgumentException("[Erreur] : " + command + ":" + cause.getMessage());
                    }
                }
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException e) {
                throw new IllegalArgumentException(command);
            }

            return instruction;
        }
    }

    /**
     * Converts a color name to a Color object.
     *
     * @param colorName The color name to convert.
     * @return The Color object representing the color.
     * @throws IllegalArgumentException If the provided color name does not exist.
     */
    public static Color convertStringToColor(String colorName) throws IllegalArgumentException {
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
                    throw new IllegalArgumentException("La couleur fournie n'existe pas.");
                }
                break;
        }
        return color;
    }
}