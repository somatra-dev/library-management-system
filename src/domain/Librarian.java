package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Librarian {

    private Integer id;

    private String name;

    private String email;

    private String phoneNumber;

    private List<Book> managedBooks = new ArrayList<>();

}
