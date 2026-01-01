package service.impl;

import domain.Book;
import domain.Librarian;
import domain.Loan;
import domain.Member;
import service.BookService;
import service.LibrarianService;
import service.LoanService;
import service.MemberService;

import java.util.ArrayList;
import java.util.List;

public class LibrarianServiceImpl implements LibrarianService {

    private final List<Librarian> librarians = new ArrayList<>();
    private Integer nextId = 1;

    private final BookService bookService;
    private final MemberService memberService;
    private final LoanService loanService;

    public LibrarianServiceImpl(BookService bookService, MemberService memberService, LoanService loanService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.loanService = loanService;
    }

    // ===================== Authentication =====================
    @Override
    public Librarian login(String email, String password) {
        return librarians.stream()
                .filter(l -> l.getEmail().equals(email) && l.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void logout(Integer librarianId) {
        System.out.println("Librarian with ID " + librarianId + " has logged out.");
    }

    // ===================== Librarian =====================
    @Override
    public void addLibrarian(Librarian librarian) {
        librarian.setId(nextId++);
        librarian.setIsLibrarian(true);
        librarians.add(librarian);
    }

    @Override
    public Librarian findLibrarianById(Integer id) {
        return librarians.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // ===================== Book Management =====================
    @Override
    public void addBook(Book book) {
        bookService.addBook(book);
    }

    @Override
    public void updateBook(Integer id, Book book) {
        bookService.updateBook(id, book);
    }

    @Override
    public void deleteBook(Integer id) {
        bookService.deleteBook(id);
    }

    @Override
    public List<Book> viewAllBooks() {
        return bookService.findAllBooks();
    }

    // ===================== Member Management =====================
    @Override
    public void addMember(Member member) {
        memberService.addMember(member);
    }

    @Override
    public List<Member> viewAllMembers() {
        return memberService.findAllMembers();
    }

    // ===================== Loan Management =====================
    @Override
    public List<Loan> viewAllLoans() {
        return loanService.findAllLoans();
    }

    @Override
    public List<Loan> viewOverdueLoans() {
        return loanService.findOverdueLoans();
    }

    @Override
    public List<Loan> viewLoansByMember(Integer memberId) {
        return loanService.findLoansByMember(memberId);
    }

}
