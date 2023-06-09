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

public class ControllerTerminalPanel extends JPanel implements ActionListener {
    private History history;
    private JTextArea textArea;
    private JButton sendButton;
    private SVGPreview svgPreview;
    private List<String> setterList;
    private List<String> modifierList;
    public ControllerTerminalPanel(History h, SVGPreview svgp) {
        super();
        this.history = h;
        this.svgPreview = svgp;

        this.setterList = new ArrayList<>();
        this.setterList.add("rectangle");
        this.setterList.add("circle");
        this.setterList.add("oval");
        this.setterList.add("square");
        this.setterList.add("polygon");

        this.modifierList = new ArrayList<>();
        this.modifierList.add("clear");
        this.modifierList.add("erase");

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
                    addCommand();
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

    public void addElement(Map<String, Object> instruction) throws ClassNotFoundException {
        String elementActionType = getString(instruction, "elementActionType");
        String elementAction = getString(instruction, "elementAction");
        String elementName = getString(instruction, "elementName");
        List<Double> coords = getList(instruction, "coords");
        Color strokeColor = getColor(instruction, "strokeColor", Color.BLACK);
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
                throw new IllegalArgumentException("Commande marche po");
            }
            svgCommand.execute(this.svgPreview,elementName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            this.history.addCommand(new Command("[Erreur] Nombre d'arguments incorrect"));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode utilitaire pour extraire une chaîne de caractères du dictionnaire avec une valeur par défaut
    private String getString(Map<String, Object> map, String key) {
        return map.containsKey(key) ? (String) map.get(key) : "";
    }

    // Méthode utilitaire pour extraire une liste de valeurs du dictionnaire avec une valeur par défaut
    private List<Double> getList(Map<String, Object> map, String key) {
        return map.containsKey(key) ? (List<Double>) map.get(key) : null;
    }

    // Méthode utilitaire pour extraire une couleur du dictionnaire avec une valeur par défaut
    private Color getColor(Map<String, Object> map, String key, Color defaultValue) {
        return map.containsKey(key) ? (Color) map.get(key) : defaultValue;
    }

    /**
     * Ajoute la commande à l'historique et efface le contenu de la zone de texte.
     * Envoie la commande en traitement pour le SVGPreview.
     */
    public void addCommand() {
        String commandText = this.textArea.getText().trim(); // Retirer les espaces avant et après le texte

        if (!commandText.isEmpty()) {
            this.history.addCommand(new Command(commandText));

            try {
                // Appel de la méthode parse de la classe Parsing
                addElement(Parser.parse(commandText,this.setterList,this.modifierList));
            } catch (IllegalArgumentException e) {
                // Gérer l'exception IllegalArgumentException
                this.history.addCommand(new Command("Erreur : " + e.getMessage()));
            } catch (Exception e) {
                // Gérer toutes les autres exceptions
                this.history.addCommand(new Command("Erreur imprévue s'est produite : " + e.getMessage()));
                e.printStackTrace();
            }
        }
        this.textArea.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addCommand();
    }
}
