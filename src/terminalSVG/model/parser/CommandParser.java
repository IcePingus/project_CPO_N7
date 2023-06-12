package terminalSVG.model.parser;

import java.util.Map;

public interface CommandParser {
    Map<String, Object> parseCommand(String[] elements) throws IllegalArgumentException;
}
