package terminalSVG.controller;

import terminalSVG.model.*;
import terminalSVG.model.SVGElement.*;

import org.apache.batik.svggen.SVGGraphics2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ControllerTerminalPanel extends JPanel implements ActionListener {
    private History history;
    private JTextArea textArea;
    private JButton sendButton;
    private SVGPreview svgPreview;
    private List<String> simpleCommands;

    public ControllerTerminalPanel(History h, SVGPreview svgp) {
        super();
        this.history = h;
        this.svgPreview = svgp;
        this.simpleCommands = collectMethodContents("terminalSVG.model.SVGElement");

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

    //methode avec switch pour choisir le traitement de la forme souhaité
    //il serait plus judicieux de lui passer l'objet SVGGraphics2D
    public void addElement(Map<String, Object> instruction, SVGGraphics2D g2d) {
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
            switch (elementAction) {
                case "circle":
                    if (coords != null && coords.size() == 3) {
                        // Créer et dessiner un cercle avec le SVGGraphics2D
                        CircleSVG cercle = new CircleSVG(elementName, coords.get(0), coords.get(1), coords.get(2), isFill, strokeColor, fillColor);
                        cercle.draw(g2d);
                    } else {
                        throw new IllegalArgumentException(">> Nombre d'arguments incorrect pour : " + elementAction);
                    }
                    break;
                case "square":
                    if (coords != null && coords.size() == 3) {
                        // Créer et dessiner un cercle avec le SVGGraphics2D
                        SquareSVG carre = new SquareSVG(elementName, coords.get(0), coords.get(1), coords.get(2), isFill, strokeColor, fillColor);
                        carre.draw(g2d);
                    } else {
                        throw new IllegalArgumentException(">> Nombre d'arguments incorrect pour : " + elementAction);
                    }
                    break;
                case "polygon":
                    if (coords != null && coords.size() >= 4) {
                        // Créer et dessiner un polygone avec le SVGGraphics2D
                        PolygonSVG polygone = new PolygonSVG(elementName, isFill, strokeColor, fillColor, coords);
                        polygone.draw(g2d);
                    } else {
                        throw new IllegalArgumentException("Nombre d'arguments incorrect pour : " + elementAction);
                    }
                case "rectangle":
                    if (coords != null && coords.size() == 4) {
                        // Créer et dessiner un polygone avec le SVGGraphics2D
                        RectangleSVG rectangle = new RectangleSVG(elementName,coords.get(0), coords.get(1), coords.get(2),coords.get(3),isFill,fillColor,strokeColor);
                        rectangle.draw(g2d);
                    } else {
                        throw new IllegalArgumentException("Nombre d'arguments incorrect pour : " + elementAction);
                    }
                    break;
                case "oval":
                    if (coords != null && coords.size() == 4) {
                        // Créer et dessiner un polygone avec le SVGGraphics2D
                        OvalSVG oval = new OvalSVG(elementName,coords.get(0), coords.get(1), coords.get(2),coords.get(3),isFill,fillColor,strokeColor);
                        oval.draw(g2d);
                    } else {
                        throw new IllegalArgumentException("Nombre d'arguments incorrect pour : " + elementAction);
                    }
                    break;
                case "clear":
                    this.svgPreview.clear();
                    break;
                default:
                    System.out.println("Default");
            }
            this.svgPreview.updateCanvas(elementAction + "-" + elementName);
        } catch (IllegalArgumentException e) {
            this.history.addCommand(new Command(new Date(), e.getMessage()));
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

        public static List<String> collectMethodContents(String packageName) {
            List<String> methodContents = new ArrayList<>();
            List<Class<?>> classes = getClassesInPackage(packageName);

            for (Class<?> clazz : classes) {
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equals("getCommandName") && Modifier.isPublic(method.getModifiers())) {
                        try {
                            Object instance = clazz.getDeclaredConstructor().newInstance();
                            String methodContent = (String) method.invoke(instance);
                            methodContents.add(methodContent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return methodContents;
        }

        private static List<Class<?>> getClassesInPackage(String packageName) {
            List<Class<?>> classes = new ArrayList<>();
            String packagePath = packageName.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try {
                java.net.URL packageURL = classLoader.getResource(packagePath);
                java.io.File packageDir = new java.io.File(packageURL.toURI());
                if (packageDir.exists()) {
                    String[] files = packageDir.list();
                    for (String file : files) {
                        if (file.endsWith(".class")) {
                            String className = packageName + '.' + file.substring(0, file.length() - 6);
                            Class<?> clazz = Class.forName(className);
                            classes.add(clazz);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return classes;
        }


    /**
     * Ajoute la commande à l'historique et efface le contenu de la zone de texte.
     * Envoie la commande en traitement pour le SVGPreview.
     */
    public void addCommand() {
        String commandText = this.textArea.getText().trim(); // Retirer les espaces avant et après le texte

        if (!commandText.isEmpty()) {
            this.history.addCommand(new Command(new Date(), commandText));

            try {
                // Appel de la méthode parse de la classe Parsing
                Map<String, Object> instruction = Parser.parse(commandText, (ArrayList<String>) simpleCommands);
                addElement(instruction, this.svgPreview.getSVGGraphics());

                // Traitement des instructions
                // ...
            } catch (IllegalArgumentException e) {
                // Gérer l'exception IllegalArgumentException
                this.history.addCommand(new Command(new Date(), ">> Erreur : " + e.getMessage()));
            } catch (Exception e) {
                // Gérer toutes les autres exceptions
                this.history.addCommand(new Command(new Date(), ">> Une erreur s'est produite : " + e.getMessage()));
            }
        }
        this.textArea.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addCommand();
    }
}
