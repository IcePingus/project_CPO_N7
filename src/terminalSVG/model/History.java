package terminalSVG.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
/**
 * The History class represents the history of commands.
 * It extends the Observable class to notify observers of changes.
 */
public class History extends Observable {
    private List<Command> commands;
    /**
     * Constructs a History object with the specified list of commands.
     *
     * @param vcommands The list of commands to initialize the history.
     */
    public History(List<Command> vcommands) {
        this.commands = new ArrayList<>();
        if (vcommands != null) this.commands.addAll(vcommands);
    }
    /**
     * Adds a command to the history.
     * Notifies observers of the change.
     *
     * @param vcommand The command to be added.
     */
    public void addCommand(Command vcommand) {
        this.commands.add(vcommand);
        this.setChanged();
        this.notifyObservers();
    }
    /**
     * Returns the list of commands in the history.
     *
     * @return The list of commands in the history.
     */
    public List<Command> getCommands() {
        return this.commands;
    }

}