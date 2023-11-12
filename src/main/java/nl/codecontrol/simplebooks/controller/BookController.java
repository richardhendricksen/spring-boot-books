package nl.codecontrol.simplebooks.controller;

import nl.codecontrol.simplebooks.entity.Book;
import nl.codecontrol.simplebooks.model.BookDto;
import nl.codecontrol.simplebooks.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<BookDto> findAll() {
        List<Book> books = bookService.findAll();

        return books.stream()
            .map(this::convertToDto)
            .toList();
    }

    @GetMapping("/title/{bookTitle}")
    public List<BookDto> findByTitle(@PathVariable String bookTitle) {
        List<Book> books = bookService.findByTitle(bookTitle);

        return books.stream()
            .map(this::convertToDto)
            .toList();
    }

    @GetMapping("/{id}")
    public BookDto findById(@PathVariable Long id) {
        Book book =  bookService.findById(id);

        return convertToDto(book);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BookDto bookDto) {
        Book book = bookService.create(convertToEntity(bookDto));

        return convertToDto(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@RequestBody BookDto bookDto, @PathVariable Long id) {
        return convertToDto(bookService.update(convertToEntity(bookDto), id));
    }

    private BookDto convertToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }
    private Book convertToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
