package nl.codecontrol.simplebooks.controller;

import nl.codecontrol.simplebooks.entity.Book;
import nl.codecontrol.simplebooks.model.BookDto;
import nl.codecontrol.simplebooks.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    @Spy
    ModelMapper modelMapper;

    @Test
    public void whenGetAllBooks_thenAllBooksAreReturned() {

        // given
        Book book1 = new Book(1, "title1", "author1");
        Book book2 = new Book(2, "title2", "author2");
        when(bookService.findAll()).thenReturn(List.of(book1, book2));

        // when
        List<BookDto> books = bookController.findAll();

        // then
        assertThat(books)
            .hasSize(2)
            .anySatisfy(book -> {
                assertThat(book.getId()).isEqualTo(book1.getId());
                assertThat(book.getTitle()).isEqualTo(book1.getTitle());
                assertThat(book.getAuthor()).isEqualTo(book1.getAuthor());
            })
            .anySatisfy(book -> {
                assertThat(book.getId()).isEqualTo(book2.getId());
                assertThat(book.getTitle()).isEqualTo(book2.getTitle());
                assertThat(book.getAuthor()).isEqualTo(book2.getAuthor());
            });
    }

}