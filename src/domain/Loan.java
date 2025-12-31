package domain;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class Loan {

    private Integer id;

    private Date loanDate;

    private Date returnDate;

    //
    private Member member;

    private Set<Book> books =  new HashSet<>();
}
