package service.impl;

import domain.Book;
import domain.Loan;
import domain.Member;
import service.LoanService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanServiceImpl implements LoanService {

    private final List<Loan> loans = new ArrayList<>();
    private Integer nextId = 1;

    private static final int LOAN_PERIOD_DAYS = 14;

    @Override
    public Loan borrowBook(Member member, List<Book> books) {
        // Check if all books are available
        for (Book book : books) {
            if (book.getAvailableCopies() <= 0) {
                System.out.println("Book not available: " + book.getTitle());
                return null;
            }
        }

        // Decrease available copies for each book
        for (Book book : books) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            book.setIsAvailable(book.getAvailableCopies() > 0);
        }

        // Create loan
        Loan loan = new Loan();
        loan.setId(nextId++);
        loan.setMember(member);
        loan.setBooks(new ArrayList<>(books));
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(LOAN_PERIOD_DAYS));
        loan.setStatus(Loan.LoanStatus.ACTIVE);

        // Add loan to member's loan list
        member.getLoans().add(loan);

        loans.add(loan);
        return loan;
    }

    @Override
    public void returnBook(Integer loanId) {
        Loan loan = findLoanById(loanId);
        if (loan != null && loan.getStatus() == Loan.LoanStatus.ACTIVE) {
            loan.setReturnDate(LocalDate.now());
            loan.setStatus(Loan.LoanStatus.RETURNED);

            // Increase available copies for each book
            for (Book book : loan.getBooks()) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                book.setIsAvailable(true);
            }
        }
    }

    @Override
    public Loan findLoanById(Integer id) {
        return loans.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Loan> findAllLoans() {
        updateOverdueStatus();
        return new ArrayList<>(loans);
    }

    @Override
    public List<Loan> findLoansByMember(Integer memberId) {
        updateOverdueStatus();
        return loans.stream()
                .filter(l -> l.getMember().getId().equals(memberId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findActiveLoans() {
        updateOverdueStatus();
        return loans.stream()
                .filter(l -> l.getStatus() == Loan.LoanStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findOverdueLoans() {
        updateOverdueStatus();
        return loans.stream()
                .filter(l -> l.getStatus() == Loan.LoanStatus.OVERDUE)
                .collect(Collectors.toList());
    }

    private void updateOverdueStatus() {
        LocalDate today = LocalDate.now();
        for (Loan loan : loans) {
            if (loan.getStatus() == Loan.LoanStatus.ACTIVE && loan.getDueDate().isBefore(today)) {
                loan.setStatus(Loan.LoanStatus.OVERDUE);
            }
        }
    }

}
