package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Integer id;
    private String title;
    private String author;
    private LocalDate publicationDate;
    private String category;
    private Integer totalPages;
    private Integer totalCopies;
    private Integer availableCopies;
    private Boolean isAvailable;
}
