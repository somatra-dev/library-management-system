package ui;

import domain.Book;
import domain.Librarian;
import domain.Loan;
import domain.Member;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import service.BookService;
import service.LibrarianService;
import service.LoanService;
import service.MemberService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class LibrarianMenu {

    private final Scanner scanner;
    private final LibrarianService librarianService;
    private final BookService bookService;
    private final LoanService loanService;
    private final MemberService memberService;
    private final CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

    public LibrarianMenu(Scanner scanner, LibrarianService librarianService,
                         BookService bookService, LoanService loanService, MemberService memberService) {
        this.scanner = scanner;
        this.librarianService = librarianService;
        this.bookService = bookService;
        this.loanService = loanService;
        this.memberService = memberService;
    }

    public void showMenu(Librarian librarian) {
        boolean loggedIn = true;

        while (loggedIn) {
            printLibrarianMenu(librarian);

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> viewAllBooks();
                case 2 -> searchBooks();
                case 3 -> addBook();
                case 4 -> updateBook();
                case 5 -> deleteBook();
                case 6 -> viewAllMembers();
                case 7 -> searchMembers();
                case 8 -> addMember();
                case 9 -> updateMember();
                case 10 -> deleteMember();
                case 11 -> viewAllLoans();
                case 12 -> viewOverdueLoans();
                case 13 -> viewLoansByMember();
                case 0 -> {
                    loggedIn = false;
                    librarianService.logout(librarian.getId());
                    System.out.println("\nLogged out successfully.");
                }
                default -> System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private void printLibrarianMenu(Librarian librarian) {
        System.out.println();
        Table header = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        header.addCell("LIBRARIAN DASHBOARD - Welcome: " + librarian.getName(), cellStyle);
        System.out.println(header.render());

        Table menu = new Table(3, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        menu.addCell("BOOK MANAGEMENT", cellStyle);
        menu.addCell("MEMBER MANAGEMENT", cellStyle);
        menu.addCell("LOAN MANAGEMENT", cellStyle);

        menu.addCell("1. View All Books");
        menu.addCell("6. View All Members");
        menu.addCell("11. View All Loans");

        menu.addCell("2. Search Books");
        menu.addCell("7. Search Members");
        menu.addCell("12. View Overdue Loans");

        menu.addCell("3. Add Book");
        menu.addCell("8. Add Member");
        menu.addCell("13. View Loans by Member");

        menu.addCell("4. Update Book");
        menu.addCell("9. Update Member");
        menu.addCell("");

        menu.addCell("5. Delete Book");
        menu.addCell("10. Delete Member");
        menu.addCell("");

        menu.addCell("0. Logout", cellStyle, 3);

        System.out.println(menu.render());
    }

    // ===================== Book Management =====================
    private void viewAllBooks() {
        System.out.println("\n[ ALL BOOKS ]");

        List<Book> books = librarianService.viewAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books in the library.");
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

    private void addBook() {
        System.out.println("\n[ ADD NEW BOOK ]");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Category: ");
        String category = scanner.nextLine();

        int totalPages = getIntInput("Total Pages: ");
        int totalCopies = getIntInput("Total Copies: ");

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setPublicationDate(LocalDate.now());
        book.setTotalPages(totalPages);
        book.setTotalCopies(totalCopies);
        book.setAvailableCopies(totalCopies);

        librarianService.addBook(book);
        System.out.println("\nBook added successfully!");
    }

    private void updateBook() {
        System.out.println("\n[ UPDATE BOOK ]");

        viewAllBooks();

        int bookId = getIntInput("\nEnter Book ID to update: ");
        Book book = bookService.findBookById(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println("\nEnter new values (press Enter to keep current):\n");

        System.out.print("Title [" + book.getTitle() + "]: ");
        String title = scanner.nextLine();

        System.out.print("Author [" + book.getAuthor() + "]: ");
        String author = scanner.nextLine();

        System.out.print("Category [" + book.getCategory() + "]: ");
        String category = scanner.nextLine();

        Book updatedBook = new Book();
        updatedBook.setTitle(title.isEmpty() ? book.getTitle() : title);
        updatedBook.setAuthor(author.isEmpty() ? book.getAuthor() : author);
        updatedBook.setCategory(category.isEmpty() ? book.getCategory() : category);
        updatedBook.setPublicationDate(book.getPublicationDate());
        updatedBook.setTotalPages(book.getTotalPages());
        updatedBook.setTotalCopies(book.getTotalCopies());
        updatedBook.setAvailableCopies(book.getAvailableCopies());

        librarianService.updateBook(bookId, updatedBook);
        System.out.println("\nBook updated successfully!");
    }

    private void deleteBook() {
        System.out.println("\n[ DELETE BOOK ]");

        viewAllBooks();

        int bookId = getIntInput("\nEnter Book ID to delete: ");

        System.out.print("Are you sure? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            librarianService.deleteBook(bookId);
            System.out.println("\nBook deleted successfully!");
        } else {
            System.out.println("\nDelete cancelled.");
        }
    }

    // ===================== Member Management =====================
    private void viewAllMembers() {
        System.out.println("\n[ ALL MEMBERS ]");

        List<Member> members = librarianService.viewAllMembers();

        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        printMemberTable(members);
    }

    private void addMember() {
        System.out.println("\n[ ADD NEW MEMBER ]");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        member.setPhoneNumber(phone);
        member.setAddress(address);
        member.setMembershipDate(LocalDate.now());

        librarianService.addMember(member);
        System.out.println("\nMember added successfully!");
    }

    private void searchMembers() {
        System.out.println("\n[ SEARCH MEMBERS ]");

        System.out.print("Enter member name: ");
        String name = scanner.nextLine();

        List<Member> members = memberService.searchMembersByName(name);

        if (members.isEmpty()) {
            System.out.println("No members found with that name.");
            return;
        }

        printMemberTable(members);
    }

    private void updateMember() {
        System.out.println("\n[ UPDATE MEMBER ]");

        viewAllMembers();

        int memberId = getIntInput("\nEnter Member ID to update: ");
        Member member = memberService.findMemberById(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.println("\nEnter new values (press Enter to keep current):\n");

        System.out.print("Name [" + member.getName() + "]: ");
        String name = scanner.nextLine();

        System.out.print("Email [" + member.getEmail() + "]: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Phone [" + member.getPhoneNumber() + "]: ");
        String phone = scanner.nextLine();

        System.out.print("Address [" + member.getAddress() + "]: ");
        String address = scanner.nextLine();

        Member updatedMember = new Member();
        updatedMember.setName(name.isEmpty() ? member.getName() : name);
        updatedMember.setEmail(email.isEmpty() ? member.getEmail() : email);
        updatedMember.setPassword(password.isEmpty() ? member.getPassword() : password);
        updatedMember.setPhoneNumber(phone.isEmpty() ? member.getPhoneNumber() : phone);
        updatedMember.setAddress(address.isEmpty() ? member.getAddress() : address);
        updatedMember.setIsActive(member.getIsActive());

        memberService.updateMember(memberId, updatedMember);
        System.out.println("\nMember updated successfully!");
    }

    private void deleteMember() {
        System.out.println("\n[ DELETE MEMBER ]");

        viewAllMembers();

        int memberId = getIntInput("\nEnter Member ID to delete: ");

        System.out.print("Are you sure? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            memberService.deleteMember(memberId);
            System.out.println("\nMember deleted successfully!");
        } else {
            System.out.println("\nDelete cancelled.");
        }
    }

    // ===================== Loan Management =====================
    private void viewAllLoans() {
        System.out.println("\n[ ALL LOANS ]");

        List<Loan> loans = librarianService.viewAllLoans();

        if (loans.isEmpty()) {
            System.out.println("No loans found.");
            return;
        }

        printLoanTable(loans);
    }

    private void viewOverdueLoans() {
        System.out.println("\n[ OVERDUE LOANS ]");

        List<Loan> loans = librarianService.viewOverdueLoans();

        if (loans.isEmpty()) {
            System.out.println("No overdue loans.");
            return;
        }

        printLoanTable(loans);
    }

    private void viewLoansByMember() {
        System.out.println("\n[ LOANS BY MEMBER ]");

        viewAllMembers();

        int memberId = getIntInput("\nEnter Member ID: ");
        List<Loan> loans = librarianService.viewLoansByMember(memberId);

        if (loans.isEmpty()) {
            System.out.println("No loans found for this member.");
            return;
        }

        printLoanTable(loans);
    }

    // ===================== Table Printers =====================
    private void printBookTable(List<Book> books) {
        Table table = new Table(6, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        table.addCell("ID", cellStyle);
        table.addCell("Title", cellStyle);
        table.addCell("Author", cellStyle);
        table.addCell("Category", cellStyle);
        table.addCell("Total", cellStyle);
        table.addCell("Available", cellStyle);

        for (Book book : books) {
            table.addCell(String.valueOf(book.getId()), cellStyle);
            table.addCell(book.getTitle(), cellStyle);
            table.addCell(book.getAuthor(), cellStyle);
            table.addCell(book.getCategory(), cellStyle);
            table.addCell(String.valueOf(book.getTotalCopies()), cellStyle);
            table.addCell(String.valueOf(book.getAvailableCopies()), cellStyle);
        }

        System.out.println(table.render());
    }

    private void printMemberTable(List<Member> members) {
        Table table = new Table(6, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        table.addCell("ID", cellStyle);
        table.addCell("Name", cellStyle);
        table.addCell("Email", cellStyle);
        table.addCell("Phone", cellStyle);
        table.addCell("Member Since", cellStyle);
        table.addCell("Active", cellStyle);

        for (Member member : members) {
            table.addCell(String.valueOf(member.getId()), cellStyle);
            table.addCell(member.getName(), cellStyle);
            table.addCell(member.getEmail(), cellStyle);
            table.addCell(member.getPhoneNumber(), cellStyle);
            table.addCell(String.valueOf(member.getMembershipDate()), cellStyle);
            table.addCell(member.getIsActive() ? "Yes" : "No", cellStyle);
        }

        System.out.println(table.render());
    }

    private void printLoanTable(List<Loan> loans) {
        Table table = new Table(6, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        table.addCell("ID", cellStyle);
        table.addCell("Member", cellStyle);
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
            table.addCell(loan.getMember().getName(), cellStyle);
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
