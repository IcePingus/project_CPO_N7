package graphic.exception;

/**
 * The nullImageException class is a custom exception that is thrown when an image is null.
 *
 * @author Team 3
 */
public class NullImageException extends RuntimeException {

    /**
     * Constructs a new nullImageException with the specified detail message.
     *
     * @param message the detail message
     */
    public NullImageException(String message) {
        super(message);
    }

    /**
     * Constructs a new nullImageException with the specified cause.
     *
     * @param e the cause
     */
    public NullImageException(Exception e) {
        super(e);
    }
}