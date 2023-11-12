package nl.codecontrol.simplebooks.service;

import nl.codecontrol.simplebooks.entity.Book;
import nl.codecontrol.simplebooks.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    public void whenFindAll_thenAllBooksAreReturned() {

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

}