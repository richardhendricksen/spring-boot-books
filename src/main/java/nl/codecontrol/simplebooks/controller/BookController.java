package nl.codecontrol.simplebooks.controller;

import nl.codecontrol.simplebooks.exceptions.BookMismatchException;
import nl.codecontrol.simplebooks.exceptions.BookNotFoundException;
import nl.codecontrol.simplebooks.entity.Book;
import nl.codecontrol.simplebooks.model.BookDto;
import nl.codecontrol.simplebooks.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
            .map(this::convertToDto)
            .toList();
    }

    @GetMapping("/title/{bookTitle}")
    public List<BookDto> findByTitle(@PathVariable String bookTitle) {
        List<Book> books = bookRepository.findByTitle(bookTitle);

        return books.stream()
            .map(this::convertToDto)
            .toList();
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        Book book =  bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);

        return convertToDto(book);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BookDto bookDto) {
        Book book = bookRepository.save(convertToEntity(bookDto));

        return convertToDto(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);

        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody BookDto bookDto, @PathVariable Long id) {
        if (bookDto.getId() != id) {
            throw new BookMismatchException();
        }
        bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);

        Book book = bookRepository.save(convertToEntity(bookDto));

        return convertToDto(book);
    }

    private BookDto convertToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }
    private Book convertToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
