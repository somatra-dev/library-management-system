package service.impl;

import domain.*;
import service.LoanService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LoanServiceImpl implements LoanService {

    private final List<Loan> loanDatabase = new ArrayList<>();
    private int nextId = 1;
    private static final int LOAN_DURATION_DAYS = 14;

    public Object executeAction(int option, Member member, List<Book> books, Integer loanId, Integer id, Integer memberId) {
        printMenu();
        return switch (option) {
            case 1 -> borrowBook(member, books);
            case 2 -> returnBookWithStatus(loanId);
            case 3 -> findActiveLoans();
            case 4 -> findOverdueLoans();
            case 5 -> findAllLoans();
            case 6 -> findLoanById(id);
            case 7 -> findLoansByMember(memberId);
            default -> "Invalid Action Selected";
        };
    }

    @Override
    public Loan borrowBook(Member member, List<Book> books) {
        if (books == null || books.isEmpty()) {
            throw new IllegalArgumentException("Cannot create a loan with no books.");
        }

        for (Book book : books) {
            if (book.getIsAvailable()) {
                throw new IllegalStateException("Book is currently unavailable: " + book.getTitle());
            }
        }

        books.forEach(b -> b.setIsAvailable(false));

        Loan loan = new Loan();
        loan.setId(nextId++);
        loan.setMember(member);
        loan.setBooks(new ArrayList<>(books));
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(LOAN_DURATION_DAYS));
        loan.setStatus(Loan.LoanStatus.ACTIVE);

        loanDatabase.add(loan);
        return loan;
    }

    @Override
    public void returnBook(Integer loanId) {
        Optional.ofNullable(findLoanById(loanId))
                .filter(loan -> loan.getStatus() == Loan.LoanStatus.ACTIVE)
                .ifPresent(loan -> {
                    loan.setReturnDate(LocalDate.now());
                    loan.setStatus(Loan.LoanStatus.RETURNED);
                    loan.getBooks().forEach(book -> book.setIsAvailable(true));
                });
    }

    @Override
    public Loan findLoanById(Integer id) {
        return loanDatabase.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Loan> findAllLoans() {
        return new ArrayList<>(loanDatabase);
    }

    @Override
    public List<Loan> findLoansByMember(Integer memberId) {
        return loanDatabase.stream()
                .filter(l -> l.getMember() != null && l.getMember().getId().equals(memberId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findActiveLoans() {
        return loanDatabase.stream()
                .filter(l -> l.getStatus() == Loan.LoanStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findOverdueLoans() {
        LocalDate today = LocalDate.now();
        return loanDatabase.stream()
                .filter(l -> l.getStatus() == Loan.LoanStatus.ACTIVE && today.isAfter(l.getDueDate()))
                .collect(Collectors.toList());
    }

    private void printMenu() {
        System.out.println("\n=== LOAN SERVICE MENU ===");
        System.out.println("1. BORROW BOOK");
        System.out.println("2. RETURN BOOK");
        System.out.println("3. FIND ACTIVE LOAN");
        System.out.println("4. FIND OVERDUE LOAN");
        System.out.println("5. FIND ALL LOAN");
        System.out.println("6. FIND LOAN BY ID");
        System.out.println("7. FIND LOAN BY MEMBER\n");
    }

    private String returnBookWithStatus(Integer loanId) {
        Loan loan = findLoanById(loanId);
        if (loan == null) return "Error: Loan ID not found.";
        if (loan.getStatus() == Loan.LoanStatus.RETURNED) return "Notice: Book already returned.";


        returnBook(loanId);
        return "Book returned successfully!";
    }
}
