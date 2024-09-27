package terminalSVG.view;

import terminalSVG.model.History;
import terminalSVG.model.Command;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * The panel that displays the command history.
 * It observes the History model and updates the displayed commands accordingly.
 *
 * @author Team 3
 */
public class ViewHistoryPanel extends JScrollPane implements Observer {

    private final JList<Command> commands;
    private final DefaultListModel<Command> listModel;

    /**
     * Creates a new instance of ViewHistoryPanel.
     * Initializes the list model and the JList for displaying commands.
     */
    public ViewHistoryPanel() {
        super();

        this.listModel = new DefaultListModel<>();
        this.commands = new JList<>(listModel);

        this.add(this.commands);

        this.setViewportView(this.commands);

        this.setLayout(new ScrollPaneLayout());

    }

    /**
     * Updates the view with the latest command added to the history.
     *
     * @param o   The Observable object (History model).
     * @param arg The argument passed by the Observable (not used in this case).
     */
    @Override
    public void update(Observable o, Object arg) {
        listModel.addElement(((History) o).getCommands().get(((History) o).getCommands().size() - 1));
        commands.ensureIndexIsVisible(listModel.getSize() - 1);
    }
}