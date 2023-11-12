package nl.codecontrol.simplebooks.service;

import nl.codecontrol.simplebooks.entity.Book;
import nl.codecontrol.simplebooks.exception.BookMismatchException;
import nl.codecontrol.simplebooks.exception.BookNotFoundException;
import nl.codecontrol.simplebooks.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    void testFindAllShouldReturnAllBooks() {

        // given
        Book book1 = new Book(1, "title1", "author1");
        Book book2 = new Book(2, "title2", "author2");
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        // when
        List<Book> books = bookService.findAll();

        // then
        assertThat(books)
            .hasSize(2)
            .containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    void testFindByTitleShouldReturnBooksWithTheSameTitle() {

        // given
        Book book1 = new Book(1, "title1", "author1");
        Book book2 = new Book(2, "title1", "author2");
        when(bookRepository.findByTitle(anyString())).thenReturn(List.of(book1, book2));

        // when
        List<Book> books = bookService.findByTitle("title");

        // then
        assertThat(books)
            .hasSize(2)
            .containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    void testFindByIdShouldReturnBookWithId() {

        // given
        Book book = new Book(1, "title1", "author1");
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        // when
        Book returnedBook = bookService.findById(1);

        // then
        assertThat(returnedBook).isEqualTo(book);
    }

    @Test
    void testFindByIdShouldThrowExceptionWhenIdDoesntExist() {

        // given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> bookService.findById(3)).isInstanceOf(BookNotFoundException.class)
            .hasMessageContaining("Book not found: 3");
    }

    @Test
    void testCreateShouldCreateBook() {

        // given
        Book book = new Book(1, "title1", "author1");
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // when
        Book createdBook = bookService.create(book);

        // then
        assertThat(createdBook).isEqualTo(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testDeleteShouldDeleteBook() {

        // given
        Book book = new Book(1, "title1", "author1");
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        // when
        bookService.delete(book.getId());

        // then
        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    void testDeleteShouldThrowExceptionWhenBookDoesntExist() {

        // given
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> bookService.delete(3L)).isInstanceOf(BookNotFoundException.class)
            .hasMessageContaining("Book not found: 3");
    }

    @Test
    void testUpdateShouldUpdateBook() {

        // given
        Book book = new Book(1, "title1", "author1");
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        // when
        bookService.update(book, 1L);

        // then
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateShouldThrowExceptionWhenIdsDontMatch() {

        // given
        Book book = new Book(1, "title1", "author1");

        // when
        assertThatThrownBy(() -> bookService.update(book, 2L)).isInstanceOf(BookMismatchException.class)
            .hasMessageContaining("Books mismatch: 2");

    }

    @Test
    void testUpdateShouldThrowExceptionWhenBookDoesntExist() {

        // given
        Book book = new Book(1, "title1", "author1");
        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> bookService.update(book, 1L)).isInstanceOf(BookNotFoundException.class)
            .hasMessageContaining("Book not found: 1");
    }
}