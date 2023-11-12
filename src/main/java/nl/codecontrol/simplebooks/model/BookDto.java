package nl.codecontrol.simplebooks.model;

import lombok.Data;

@Data
public class BookDto {

    private long id;
    private String title;
    private String author;
}
