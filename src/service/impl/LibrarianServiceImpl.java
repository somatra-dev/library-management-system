package service.impl;

import domain.Book;
import domain.Librarian;
import domain.Loan;
import domain.Member;
import service.LibrarianService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarianServiceImpl implements LibrarianService {

    private final List<Librarian> librarians = new ArrayList<>();

    private Integer nextId = 1;

    @Override
    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }

    @Override
    public void updateLibrarian(Integer id, Librarian librarian) {

    }

    @Override
    public void deleteLibrarian(Integer id) {

    }

    @Override
    public Librarian findLibrarianById(Integer id) {
        return null;
    }

    @Override
    public List<Librarian> findAllLibrarians() {
        return List.of();
    }

    @Override
    public List<Book> viewAllBooks() {
        return List.of();
    }

    @Override
    public Book viewBookById(Integer id) {
        return null;
    }

    @Override
    public List<Book> viewAvailableBooks() {
        return List.of();
    }

    @Override
    public List<Member> viewAllMembers() {
        return List.of();
    }

    @Override
    public Member viewMemberById(Integer id) {
        return null;
    }

    @Override
    public List<Loan> viewAllLoans() {
        return List.of();
    }

    @Override
    public Loan viewLoanById(Integer id) {
        return null;
    }

    @Override
    public List<Loan> viewActiveLoans() {
        return List.of();
    }

    @Override
    public List<Loan> viewOverdueLoans() {
        return List.of();
    }

    @Override
    public List<Loan> viewLoansByMember(Integer memberId) {
        return List.of();
    }
}
