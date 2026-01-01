package ui;

import domain.Librarian;
import domain.Member;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import service.BookService;
import service.LibrarianService;
import service.LoanService;
import service.MemberService;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner;
    private final MemberService memberService;
    private final LibrarianService librarianService;
    private final BookService bookService;
    private final LoanService loanService;

    private final MemberMenu memberMenu;
    private final LibrarianMenu librarianMenu;
    private final CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

    public MainMenu(MemberService memberService, LibrarianService librarianService,
                    BookService bookService, LoanService loanService) {
        this.scanner = new Scanner(System.in);
        this.memberService = memberService;
        this.librarianService = librarianService;
        this.bookService = bookService;
        this.loanService = loanService;

        this.memberMenu = new MemberMenu(scanner, memberService, bookService);
        this.librarianMenu = new LibrarianMenu(scanner, librarianService, bookService, loanService, memberService);
    }

    public void start() {
        boolean running = true;

        while (running) {
            printHeader();
            printLoginMenu();

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> loginAsMember();
                case 2 -> loginAsLibrarian();
                case 0 -> {
                    running = false;
                    System.out.println("\nThank you for using Library Management System. Goodbye!");
                }
                default -> System.out.println("\nInvalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private void printHeader() {
        System.out.println();
        Table header = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        header.addCell("LIBRARY MANAGEMENT SYSTEM", cellStyle);
        System.out.println(header.render());
    }

    private void printLoginMenu() {
        Table menu = new Table(1, BorderStyle.UNICODE_BOX, ShownBorders.ALL);
        menu.addCell("LOGIN MENU", cellStyle);
        menu.addCell("1. Login as Member");
        menu.addCell("2. Login as Librarian");
        menu.addCell("0. Exit");
        System.out.println(menu.render());
    }

    private void loginAsMember() {
        System.out.println("\n[ MEMBER LOGIN ]");

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Member member = memberService.login(email, password);

        if (member != null) {
            System.out.println("\nLogin successful! Welcome, " + member.getName());
            memberMenu.showMenu(member);
        } else {
            System.out.println("\nInvalid email or password. Please try again.");
        }
    }

    private void loginAsLibrarian() {
        System.out.println("\n[ LIBRARIAN LOGIN ]");

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Librarian librarian = librarianService.login(email, password);

        if (librarian != null) {
            System.out.println("\nLogin successful! Welcome, " + librarian.getName());
            librarianMenu.showMenu(librarian);
        } else {
            System.out.println("\nInvalid email or password. Please try again.");
        }
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
