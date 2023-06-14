package graphic.exception;

public class nullImageException extends RuntimeException {
    public nullImageException(String message) {
        super(message);
    }

    public nullImageException(Exception e) {
        super(e);
    }
}
