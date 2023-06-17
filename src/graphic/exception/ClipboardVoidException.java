package graphic.exception;

/**
 * The ClipboardVoidException class is a custom exception that is thrown when the clipboard doesn't contain an image.
 *
 * @author Team 3
 */
public class ClipboardVoidException extends RuntimeException {

    /**
     * Constructs a new ClipboardVoidException with the specified detail message.
     *
     * @param message the detail message
     */
    public ClipboardVoidException(String message) {
        super(message);
    }
}