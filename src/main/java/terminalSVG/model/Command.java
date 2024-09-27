package terminalSVG.model;

/**
 * The Command class represents a command with its content.
 *
 * @author Team 3
 */
public class Command {
    private String content;

    /**
     * Constructs a Command object with the specified content.
     *
     * @param vcontent The content of the command.
     */
    public Command(String vcontent) {
        this.content = vcontent;
    }

    /**
     * Returns the content of the command.
     *
     * @return The content of the command.
     */
    @Override
    public String toString() {
        return this.content;
    }
}