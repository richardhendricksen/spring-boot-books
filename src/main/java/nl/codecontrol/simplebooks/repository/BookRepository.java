package nl.codecontrol.simplebooks.repository;

import nl.codecontrol.simplebooks.entity.Book;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface BookRepository extends ListCrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
