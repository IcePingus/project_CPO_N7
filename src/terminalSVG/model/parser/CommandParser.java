package terminalSVG.model.parser;

import terminalSVG.model.Instruction;

import java.util.Map;

/**
 * The CommandParser interface defines a contract for parsing commands represented by an array of elements.
 * The parseCommand method takes an array of elements representing the command and
 * returns a map containing the parsed command.
 *
 * @author Team 3
 */
public interface CommandParser {
    /**
     * Parses a command represented by an array of elements.
     *
     * @param elements The array of elements representing the command.
     * @return A map containing the parsed command.
     * @throws IllegalArgumentException If the command is invalid or contains errors.
     */
    Instruction parseCommand(String[] elements) throws IllegalArgumentException;
}