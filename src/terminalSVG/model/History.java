package terminalSVG.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class History extends Observable {
    private List<Command> commands;

    public History(List<Command> vcommands) {
        this.commands = new ArrayList<>();
        if (vcommands != null) this.commands.addAll(vcommands);
    }

    public void addCommand(Command vcommand) {
        this.commands.add(vcommand);
        this.setChanged();
        this.notifyObservers();
    }

    public List<Command> getCommands() {
        return this.commands;
    }

}