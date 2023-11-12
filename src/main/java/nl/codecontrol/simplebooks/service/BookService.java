package nl.codecontrol.simplebooks.service;

import nl.codecontrol.simplebooks.entity.Book;
import nl.codecontrol.simplebooks.exception.BookMismatchException;
import nl.codecontrol.simplebooks.exception.BookNotFoundException;
import nl.codecontrol.simplebooks.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Book findById(long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));

    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.deleteById(id);
    }

    public Book update(Book book, long id) {
        if (book.getId() != id) {
            throw new BookMismatchException(id);
        }
        bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(id));

        return bookRepository.save(book);
    }
}
