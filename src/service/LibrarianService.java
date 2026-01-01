package service;

import domain.Book;
import domain.Librarian;
import domain.Loan;
import domain.Member;

import java.util.List;

public interface LibrarianService {

    Librarian login(String email, String password);

    void logout(Integer librarianId);

    // Librarian
    void addLibrarian(Librarian librarian);

    Librarian findLibrarianById(Integer id);

    // Book Management
    void addBook(Book book);

    void updateBook(Integer id, Book book);

    void deleteBook(Integer id);

    List<Book> viewAllBooks();

    // Member Management
    void addMember(Member member);

    List<Member> viewAllMembers();

    // Loan Management

    List<Loan> viewAllLoans();

    List<Loan> viewOverdueLoans();

    List<Loan> viewLoansByMember(Integer memberId);

}
