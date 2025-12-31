package domain;


import lombok.Data;

@Data
public class Book {

    private Integer id;

    private String title;

    private String author;

    private Data publicationDate;

    private String category;

    private Integer totalPages;

    private Integer totalCopies;

}
