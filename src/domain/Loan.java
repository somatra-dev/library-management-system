package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    private Integer id;

    private LocalDate loanDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private LoanStatus status;

    private Member member;

    private List<Book> books = new ArrayList<>();

    public enum LoanStatus {
        ACTIVE,
        RETURNED,
        OVERDUE
    }
}
