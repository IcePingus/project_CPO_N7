package terminalSVG.model;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * The Command class represents a command with its content.
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