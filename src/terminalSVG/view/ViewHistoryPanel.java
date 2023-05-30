package terminalSVG.view;


import terminalSVG.model.History;
import terminalSVG.model.Command;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ViewHistoryPanel extends JScrollPane implements Observer {

    private final JList<Command> messages;
    private final DefaultListModel<Command> listModel;

    public ViewHistoryPanel() {
        super();

        this.listModel = new DefaultListModel<>();
        this.messages = new JList<>(listModel);

        this.add(this.messages);

        this.setViewportView(this.messages);
        this.setLayout(new ScrollPaneLayout());
    }

    @Override
    public void update(Observable o, Object arg) {
        listModel.addElement(((History) o).getCommands().get(((History) o).getCommands().size()-1));
        messages.ensureIndexIsVisible(listModel.getSize() - 1);
    }
}