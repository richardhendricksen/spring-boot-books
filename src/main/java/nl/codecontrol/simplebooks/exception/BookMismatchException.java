package nl.codecontrol.simplebooks.exception;

public class BookMismatchException extends RuntimeException {
    public BookMismatchException(long s) {
        super("Books mismatch: %s".formatted(s));
    }

}
