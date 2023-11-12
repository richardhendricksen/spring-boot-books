package nl.codecontrol.simplebooks.controller;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import nl.codecontrol.simplebooks.entity.Book;
import nl.codecontrol.simplebooks.model.BookDto;
import nl.codecontrol.simplebooks.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIT {

    @Autowired
    BookRepository bookRepository;

    @LocalServerPort
    int port;

    @BeforeAll
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void whenGetAllBooks_thenAllBooksAreReturned() {

        // given
        var book1 = new Book(1, "title1", "author1");
        var book2 = new Book(2, "title2", "author1");
        bookRepository.saveAll(List.of(book1, book2));

        // when
        List<BookDto> books =
            given().
            when().
                get("api/books").
            then().
                statusCode(HttpStatus.OK.value()).
                extract().as(new TypeRef<>() {});

        // then
        assertThat(books)
            .hasSize(2)
            .anySatisfy(book1Dto -> {
                assertThat(book1Dto.getId()).isEqualTo(book1.getId());
                assertThat(book1Dto.getTitle()).isEqualTo(book1.getTitle());
                assertThat(book1Dto.getAuthor()).isEqualTo(book1.getAuthor());
            })
            .anySatisfy(book2Dto -> {
                assertThat(book2Dto.getId()).isEqualTo(book2.getId());
                assertThat(book2Dto.getTitle()).isEqualTo(book2.getTitle());
                assertThat(book2Dto.getAuthor()).isEqualTo(book2.getAuthor());
            });
    }
}