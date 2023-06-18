package graphic.exception;

/**
 * The BadFormatException class is a custom exception that is thrown when a bad format of a file is encountered.
 *
 * @author Team 3
 */
public class BadFormatException extends RuntimeException {

    /**
     * Constructs a new BadFormatException with the specified detail message.
     *
     * @param message the detail message
     */
    public BadFormatException(String message) {
        super(message);
    }
}