package service;

import domain.Book;
import domain.Loan;
import domain.Member;

import java.util.List;

public interface LoanService {

    Loan borrowBook(Member member, List<Book> books);

    void returnBook(Integer loanId);

    Loan findLoanById(Integer id);

    List<Loan> findAllLoans();

    List<Loan> findLoansByMember(Integer memberId);

    List<Loan> findActiveLoans();

    List<Loan> findOverdueLoans();

}
