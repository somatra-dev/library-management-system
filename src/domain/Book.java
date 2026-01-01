package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String id;

    private String title;

    private String author;

    private LocalDate publicationDate;

    private String category;

    private int totalPages;

    private int totalCopies;

    private int availableCopies;

    private Boolean isAvailable;

}
