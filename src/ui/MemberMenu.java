package ui;

import domain.Book;
import domain.Loan;
import domain.Member;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import service.BookService;
import service.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberMenu {

    private final Scanner scanner;
    private final MemberService memberService;
    private final BookService bookService;
    private final CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

    public MemberMenu(Scanner scanner, MemberService memberService, BookService bookService) {
        this.scanner = scanner;
        this.memberService = memberService;
        this.bookService = bookService;
    }

    public void showMenu(Member member) {
        boolean loggedIn = true;

        while (loggedIn) {
            printMemberMenu(member);

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> viewAvailableBooks(member);
                case 2 -> searchBooks();
                case 3 -> borrowBooks(member);
                case 4 -> returnBook(member);
                case 5 -> viewMyLoans(member);
                case 6 -> updateProfile(member);
                case 0 -> {
                    loggedIn = false;
                    memberService.logout(member.getId());
                    System.out.println("\nLogged out successfully.");
                }
                default -> System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private void printMemberMenu(Member member) {
        System.out.println();
        Table header = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        header.addCell("MEMBER DASHBOARD - Welcome: " + member.getName(), cellStyle);
        System.out.println(header.render());

        Table menu = new Table(1, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        menu.addCell("MENU OPTIONS", cellStyle);
        menu.addCell("1. View Available Books");
        menu.addCell("2. Search Books");
        menu.addCell("3. Borrow Books");
        menu.addCell("4. Return Book");
        menu.addCell("5. View My Loans");
        menu.addCell("6. Update My Profile");
        menu.addCell("0. Logout");
        System.out.println(menu.render());
    }

    private void viewAvailableBooks(Member member) {
        System.out.println("\n[ AVAILABLE BOOKS ]");

        List<Book> books = memberService.viewAvailableBooks(member.getId());

        if (books.isEmpty()) {
            System.out.println("No books available at the moment.");
            return;
        }

        printBookTable(books);
    }

    private void searchBooks() {
        System.out.println("\n[ SEARCH BOOKS ]");

        Table searchMenu = new Table(1, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        searchMenu.addCell("Search By", cellStyle);
        searchMenu.addCell("1. Title");
        searchMenu.addCell("2. Author");
        searchMenu.addCell("3. Category");
        System.out.println(searchMenu.render());

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1 -> {
                System.out.print("Enter title: ");
                String title = scanner.nextLine();
                Book book = bookService.searchBooksByTitle(title);
                if (book != null) {
                    printBookTable(List.of(book));
                } else {
                    System.out.println("No book found with that title.");
                }
            }
            case 2 -> {
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
                List<Book> books = bookService.searchBooksByAuthor(author);
                if (!books.isEmpty()) {
                    printBookTable(books);
                } else {
                    System.out.println("No books found by that author.");
                }
            }
            case 3 -> {
                System.out.print("Enter category: ");
                String category = scanner.nextLine();
                List<Book> books = bookService.searchBooksByCategory(category);
                if (!books.isEmpty()) {
                    printBookTable(books);
                } else {
                    System.out.println("No books found in that category.");
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private void borrowBooks(Member member) {
        System.out.println("\n[ BORROW BOOKS ]");

        List<Book> availableBooks = memberService.viewAvailableBooks(member.getId());

        if (availableBooks.isEmpty()) {
            System.out.println("No books available to borrow.");
            return;
        }

        printBookTable(availableBooks);

        System.out.print("\nEnter book IDs to borrow (comma-separated, e.g., 1,2,3): ");
        String input = scanner.nextLine();

        List<Book> booksToBorrow = new ArrayList<>();
        String[] ids = input.split(",");

        for (String idStr : ids) {
            try {
                int id = Integer.parseInt(idStr.trim());
                Book book = bookService.findBookById(id);
                if (book != null && book.getAvailableCopies() > 0) {
                    booksToBorrow.add(book);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID: " + idStr);
            }
        }

        if (booksToBorrow.isEmpty()) {
            System.out.println("\nNo valid books selected.");
            return;
        }

        Loan loan = memberService.borrowBook(member.getId(), booksToBorrow);

        if (loan != null) {
            System.out.println("\nSuccessfully borrowed " + booksToBorrow.size() + " book(s)!");
            System.out.println("Loan ID: " + loan.getId());
            System.out.println("Due Date: " + loan.getDueDate());
        } else {
            System.out.println("\nFailed to borrow books.");
        }
    }

    private void returnBook(Member member) {
        System.out.println("\n[ RETURN BOOK ]");

        List<Loan> loans = memberService.viewOwnLoans(member.getId());
        List<Loan> activeLoans = loans.stream()
                .filter(l -> l.getStatus() != Loan.LoanStatus.RETURNED)
                .toList();

        if (activeLoans.isEmpty()) {
            System.out.println("You have no active loans to return.");
            return;
        }

        printLoanTable(activeLoans);

        int loanId = getIntInput("\nEnter Loan ID to return: ");
        memberService.returnBook(member.getId(), loanId);
        System.out.println("\nBook(s) returned successfully!");
    }

    private void viewMyLoans(Member member) {
        System.out.println("\n[ MY LOANS ]");

        List<Loan> loans = memberService.viewOwnLoans(member.getId());

        if (loans.isEmpty()) {
            System.out.println("You have no loans.");
            return;
        }

        printLoanTable(loans);
    }

    private void updateProfile(Member member) {
        System.out.println("\n[ UPDATE MY PROFILE ]");

        System.out.println("Current Name: " + member.getName());
        System.out.println("Current Email: " + member.getEmail());
        System.out.println("Current Phone: " + member.getPhoneNumber());
        System.out.println("Current Address: " + member.getAddress());

        System.out.println("\nEnter new values (press Enter to keep current):\n");

        System.out.print("New Name [" + member.getName() + "]: ");
        String name = scanner.nextLine();

        System.out.print("New Email [" + member.getEmail() + "]: ");
        String email = scanner.nextLine();

        System.out.print("New Password: ");
        String password = scanner.nextLine();

        System.out.print("New Phone [" + member.getPhoneNumber() + "]: ");
        String phone = scanner.nextLine();

        System.out.print("New Address [" + member.getAddress() + "]: ");
        String address = scanner.nextLine();

        Member updatedInfo = new Member();
        updatedInfo.setName(name.isEmpty() ? member.getName() : name);
        updatedInfo.setEmail(email.isEmpty() ? member.getEmail() : email);
        updatedInfo.setPassword(password.isEmpty() ? member.getPassword() : password);
        updatedInfo.setPhoneNumber(phone.isEmpty() ? member.getPhoneNumber() : phone);
        updatedInfo.setAddress(address.isEmpty() ? member.getAddress() : address);

        memberService.updateOwnProfile(member.getId(), updatedInfo);
        System.out.println("\nProfile updated successfully!");
    }

    private void printBookTable(List<Book> books) {
        Table table = new Table(5, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        table.addCell("ID", cellStyle);
        table.addCell("Title", cellStyle);
        table.addCell("Author", cellStyle);
        table.addCell("Category", cellStyle);
        table.addCell("Available", cellStyle);

        for (Book book : books) {
            table.addCell(String.valueOf(book.getId()), cellStyle);
            table.addCell(book.getTitle(), cellStyle);
            table.addCell(book.getAuthor(), cellStyle);
            table.addCell(book.getCategory(), cellStyle);
            table.addCell(String.valueOf(book.getAvailableCopies()), cellStyle);
        }

        System.out.println(table.render());
    }

    private void printLoanTable(List<Loan> loans) {
        Table table = new Table(5, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        table.addCell("ID", cellStyle);
        table.addCell("Books", cellStyle);
        table.addCell("Loan Date", cellStyle);
        table.addCell("Due Date", cellStyle);
        table.addCell("Status", cellStyle);

        for (Loan loan : loans) {
            String bookTitles = loan.getBooks().stream()
                    .map(Book::getTitle)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("N/A");

            table.addCell(String.valueOf(loan.getId()), cellStyle);
            table.addCell(bookTitles, cellStyle);
            table.addCell(String.valueOf(loan.getLoanDate()), cellStyle);
            table.addCell(String.valueOf(loan.getDueDate()), cellStyle);
            table.addCell(String.valueOf(loan.getStatus()), cellStyle);
        }

        System.out.println(table.render());
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
