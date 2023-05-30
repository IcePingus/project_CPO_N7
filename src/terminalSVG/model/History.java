package terminalSVG.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class History extends Observable {
    private List<Command> commands;

    public History(List<Command> vmessages) {
        this.commands = new ArrayList<>();
        if (vmessages != null) this.commands.addAll(vmessages);
    }

    public void addCommand(Command vmessage) {
        this.commands.add(vmessage);
        this.setChanged();
        this.notifyObservers();
    }

    public List<Command> getCommands() {
        return this.commands;
    }

}