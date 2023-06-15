package terminalSVG.controller;

import terminalSVG.model.*;
import terminalSVG.model.SVGCommand.*;

import terminalSVG.model.parser.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * The `ControllerTerminalPanel` class represents a JPanel that serves as the controller for the SVG terminal application.
 * It handles user input, command parsing, and execution of commands on the SVGPreview.
 */
public class ControllerTerminalPanel extends JPanel implements ActionListener {
    private History history;
    private JTextArea textArea;
    private JButton sendButton;
    private SVGPreview svgPreview;
    private final List<String> setterCommandList;
    private final List<String> modifierCommandList;
    /**
     * Constructs a new instance of the `ControllerTerminalPanel` class with the specified history and SVGPreview.
     *
     * @param h The history object to store the executed commands.
     * @param svgp The SVGPreview object to render the SVG commands.
     */
    public ControllerTerminalPanel(History h, SVGPreview svgp) {
        super();
        this.history = h;
        this.svgPreview = svgp;

        this.setterCommandList = new ArrayList<>();
        this.setterCommandList.add("rectangle");
        this.setterCommandList.add("circle");
        this.setterCommandList.add("oval");
        this.setterCommandList.add("square");
        this.setterCommandList.add("polygon");

        this.modifierCommandList = new ArrayList<>();
        this.modifierCommandList.add("clear");
        this.modifierCommandList.add("erase");
        this.modifierCommandList.add("color");
        this.modifierCommandList.add("save");
        this.modifierCommandList.add("rename");
        this.modifierCommandList.add("recolor");
        this.modifierCommandList.add("translate");
        this.modifierCommandList.add("resize");

        this.sendButton = new JButton("Entrer");
        this.sendButton.addActionListener(this);

        this.textArea = new JTextArea() {
            @Override
            public void replaceSelection(String content) {
                if (content != null && content.equals("\n")) {
                    super.replaceSelection("");
                } else {
                    super.replaceSelection(content);
                }
            }
        };
        this.textArea.setLineWrap(true); // Empêche le retour à la ligne automatique
        this.textArea.setWrapStyleWord(true); // Coupe les mots longs pour s'adapter à la largeur

        // Ajout de la détection de la touche "Entrée"
        this.textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    prepareCommand();
                }
            }
        });

        // Création d'un ScrollPane pour la zone de saisie
        JScrollPane formattedTextArea = new JScrollPane(this.textArea);

        // Création du conteneur global
        JPanel container = new JPanel(new BorderLayout());

        // Ajout de la zone de texte à gauche du panneau inférieur
        container.add(formattedTextArea, BorderLayout.CENTER);

        // Ajout du bouton à droite du panneau inférieur
        container.add(this.sendButton, BorderLayout.EAST);

        this.setLayout(new GridLayout());
        this.add(container);
    }

    /**
     * Adds the command to the history, clears the content of the text area, and prepares the command for execution.
     */
    public void prepareCommand() {
        String commandText = this.textArea.getText().trim(); // Retirer les espaces avant et après le texte

        if (!commandText.isEmpty()) {
            this.history.addCommand(new Command(commandText));
            try {
                // Appel de la méthode parse de la classe Parsing
                executeCommand(Parser.parse(commandText,this.setterCommandList,this.modifierCommandList));
            } catch (IllegalArgumentException e) {
                // Gérer l'exception IllegalArgumentException
                this.history.addCommand(new Command("[Erreur] : " + e.getMessage()));
            } catch (Exception e) {
                // Gérer toutes les autres exceptions
                this.history.addCommand(new Command("Erreur imprévue s'est produite : " + e.getMessage()));
            }
        }
        this.textArea.setText("");
    }

    /**
     * Retrieves the parsed map and handles the processing and execution of the command on the SVGPreview.
     * Constructs and instantiates the associated object dynamically using introspection.
     *
     * @param instruction The parsed command represented as a map.
     * @throws ClassNotFoundException If the specified class is not found.
     */
    public void executeCommand(Map<String, Object> instruction) throws ClassNotFoundException {
        String elementActionType = getString(instruction, "elementActionType");
        String elementAction = getString(instruction, "elementAction");
        String elementName = getString(instruction, "elementName");
        List<Double> coords = getList(instruction, "coords");
        Color strokeColor = getColor(instruction, "strokeColor", this.svgPreview.getDefaultColor());
        boolean isFill = false;
        Color fillColor = getColor(instruction, "fillColor", null);
        if (fillColor != null) {
            isFill = true;
        }
        try {
            String action = elementAction;
            action += "SVG";
            action = Character.toUpperCase(action.charAt(0)) + action.substring(1);
            SVGCommand svgCommand = null;
            Class<?> actionClass = Class.forName("terminalSVG.model.SVGCommand." + action);
            if (elementActionType.equals("setter")) {
                Constructor<?> shapeConstructor = actionClass.getDeclaredConstructor(String.class, List.class, boolean.class, Color.class, Color.class);
                shapeConstructor.setAccessible(true);
                svgCommand = (DrawShapeAction) shapeConstructor.newInstance(elementName, coords, isFill, strokeColor, fillColor);
            } else if (elementActionType.equals("modifier")) {
                Constructor<?> CommandConstructor = actionClass.getDeclaredConstructor(Map.class);
                CommandConstructor.setAccessible(true);
                svgCommand = (SVGCommand) CommandConstructor.newInstance(instruction);
            }
            if (svgCommand == null) {
                throw new IllegalArgumentException("ERROR");
            }
            svgCommand.execute(this.svgPreview,elementName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            this.history.addCommand(new Command("[Erreur] : Nombre d'arguments incorrect"));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Utility method to extract a string value from the map with a default value.
     *
     * @param map The map containing the values.
     * @param key The key to retrieve the value.
     * @return The extracted string value or an empty string if the key is not found.
     */
    // Méthode utilitaire pour extraire une chaîne de caractères du dictionnaire avec une valeur par défaut
    private String getString(Map<String, Object> map, String key) {
        return map.containsKey(key) ? (String) map.get(key) : "";
    }
    /**
     * Utility method to extract a list of double values from the map with a default value.
     *
     * @param map The map containing the values.
     * @param key The key to retrieve the value.
     * @return The extracted list of double values or null if the key is not found.
     */
    private List<Double> getList(Map<String, Object> map, String key) {
        return map.containsKey(key) ? (List<Double>) map.get(key) : null;
    }
    /**
     * Utility method to extract a color value from the map with a default value.
     *
     * @param map The map containing the values.
     * @param key The key to retrieve the value.
     * @param defaultValue The default color value.
     * @return The extracted color value or the default value if the key is not found.
     */
    private Color getColor(Map<String, Object> map, String key, Color defaultValue) {
        return map.containsKey(key) ? (Color) map.get(key) : defaultValue;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        prepareCommand();
    }
}