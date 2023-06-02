package terminalSVG.view;

import terminalSVG.model.History;
import terminalSVG.model.Command;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ViewHistoryPanel extends JScrollPane implements Observer {

    private final JList<Command> commands;
    private final DefaultListModel<Command> listModel;

    public ViewHistoryPanel() {
        super();

        this.listModel = new DefaultListModel<>();
        this.commands = new JList<>(listModel);

        this.add(this.commands);

        this.setViewportView(this.commands);
        this.setLayout(new ScrollPaneLayout());
    }

    @Override
    public void update(Observable o, Object arg) {
        listModel.addElement(((History) o).getCommands().get(((History) o).getCommands().size() - 1));
        commands.ensureIndexIsVisible(listModel.getSize() - 1);
    }
}