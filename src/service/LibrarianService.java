package service;

import domain.Book;
import domain.Librarian;
import domain.Loan;
import domain.Member;

import java.util.List;

public interface LibrarianService {

    // Librarian Account Management
    void addLibrarian(Librarian librarian);

    void updateLibrarian(Integer id, Librarian librarian);

    void deleteLibrarian(Integer id);

    Librarian findLibrarianById(Integer id);

    List<Librarian> findAllLibrarians();

    // View All Books
    List<Book> viewAllBooks();

    Book viewBookById(Integer id);

    List<Book> viewAvailableBooks();

    // View All Members
    List<Member> viewAllMembers();

    Member viewMemberById(Integer id);

    // View All Loans
    List<Loan> viewAllLoans();

    Loan viewLoanById(Integer id);

    List<Loan> viewActiveLoans();

    List<Loan> viewOverdueLoans();

    List<Loan> viewLoansByMember(Integer memberId);

}
