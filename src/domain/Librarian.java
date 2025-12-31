package domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Librarian {

    private Integer id;

    private String LibrarianName;


    //
    private Set<Book> books = new HashSet<>();

}
