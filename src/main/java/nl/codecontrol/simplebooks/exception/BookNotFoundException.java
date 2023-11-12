package nl.codecontrol.simplebooks.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(long id) {
        super("Book not found: %s".formatted(id));
    }
}
