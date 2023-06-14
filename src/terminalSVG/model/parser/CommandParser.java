package terminalSVG.model.parser;

import java.util.Map;
/**The CommandParser interface defines a contract for parsing commands represented by an array of elements.
 * The parseCommand method takes an array of elements representing the command and
 * returns a map containing the parsed command. It throws an IllegalArgumentException
 * if the command is invalid or contains errors.
 */
public interface CommandParser {
    /**
     * Parses a command represented by an array of elements.
     *
     * @param elements The array of elements representing the command.
     * @return A map containing the parsed command.
     * @throws IllegalArgumentException If the command is invalid or contains errors.
     */
    Map<String, Object> parseCommand(String[] elements) throws IllegalArgumentException;
}
