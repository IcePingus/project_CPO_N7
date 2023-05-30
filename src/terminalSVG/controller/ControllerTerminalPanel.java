package terminalSVG.controller;

import terminalSVG.model.History;
import terminalSVG.model.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ControllerTerminalPanel extends JPanel implements ActionListener {

    private History history;
    private JTextArea textArea;
    private JButton send;

    public ControllerTerminalPanel(History vchat) {
        super();

        this.history = vchat;
        this.textArea = new JTextArea();
        this.send = new JButton("send");

        this.send.addActionListener(this);

        textArea.setLineWrap(true); // Empêche le retour à la ligne automatique
        textArea.setWrapStyleWord(true); // Coupe les mots longs pour s'adapter à la largeur

        JScrollPane formattedTextArea = new JScrollPane(textArea);

        // Création des conteneurs
        JPanel container = new JPanel(new BorderLayout());

        // Ajout de la zone de texte à gauche du panneau inférieur
        container.add(formattedTextArea, BorderLayout.CENTER);

        // Ajout du bouton à droite du panneau inférieur
        container.add(send, BorderLayout.EAST);

        // Utilisation du gestionnaire de disposition GridLayout
        this.setLayout(new GridLayout());

        // Ajout du conteneur principal au panneau
        this.add(container);
    }

    public void addCommand() {
        this.history.addCommand(new Command(new Date(), this.textArea.getText()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!this.textArea.getText().equals("")){
            this.addCommand();
            this.textArea.setText("");
        }
    }


}