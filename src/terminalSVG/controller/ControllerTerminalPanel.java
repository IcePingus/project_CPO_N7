package terminalSVG.controller;

import terminalSVG.model.History;
import terminalSVG.model.Command;
import terminalSVG.model.Parsing;
import terminalSVG.model.SVGPreview;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

public class ControllerTerminalPanel extends JPanel implements ActionListener {
    private History history;
    private JTextArea textArea;
    private JButton sendButton;
    private Parsing parser;
    private SVGPreview svgPreview;

    public ControllerTerminalPanel(History h, SVGPreview svgp) {
        super();

        this.history = h;
        this.svgPreview = svgp;
        this.parser = new Parsing(this.svgPreview);

        this.sendButton = new JButton("Entrer");
        this.sendButton.addActionListener(this);

        this.textArea = new JTextArea();
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

    /**
     * Ajoute la commande à l'historique et efface le contenu de la zone de texte.
     * Envoie la commande en traitement pour le SVGPreview.
     */
    public void addCommand() {
        if (!this.textArea.getText().isEmpty()) {
            this.history.addCommand(new Command(new Date(), this.textArea.getText()));
            this.parser.parse(this.textArea.getText());
            this.textArea.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addCommand();
    }
}
