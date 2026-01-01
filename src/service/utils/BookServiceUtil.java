package service.utils;

import domain.Book;
import service.BookService;
import service.impl.BookServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class BookServiceUtil {
    static Scanner scanner = new Scanner(System.in);
    static BookService bookService = new BookServiceImpl();

    public static void searchMenu() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("           SEARCH BOOKS           ");
        System.out.println("=================================");
        System.out.println(" 1. Search by Title");
        System.out.println(" 2. Search by Author");
        System.out.println(" 3. Search by Category");
        System.out.println(" 4. Search by Book ID");
        System.out.println(" 5. Back to Book Service Menu");
        System.out.println("=================================");
        System.out.print("[+] Select an option: ");
    }

    public static void bookServiceMenu() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("        BOOK SERVICE MENU         ");
        System.out.println("=================================");
        System.out.println(" 1. Add Book");
        System.out.println(" 2. Delete Book");
        System.out.println(" 3. Update Book");
        System.out.println(" 4. List All Books");
        System.out.println(" 5. Search Book");
        System.out.println(" 6. Return to Main Menu");
        System.out.println("=================================");
        System.out.print("[+] Select an option: ");
    }

    public static void bookServiceUtilLoop() {
        boolean isRunning = true;
        while (isRunning) {
            bookServiceMenu();
            String opt = scanner.nextLine();

            switch (opt) {
                case "1": {
                    String bookTitle;
                    while (true) {
                        System.out.print("[+]Enter book title: ");
                        bookTitle = scanner.nextLine();
                        if (bookTitle.isEmpty() || bookTitle.matches("[0-9]+")) {
                            System.out.println("Invalid title...");
                            continue;
                        }
                        break;
                    }
                    String author;
                    while (true) {
                        System.out.print("[+]Enter author name: ");
                        author = scanner.nextLine();
                        if (author.isEmpty() || author.matches("[0-9]+")) {
                            System.out.println("Invalid author name...");
                            continue;
                        }
                        break;
                    }

                    LocalDate parsedDate;
                    while (true) {
                        System.out.print("[+] Enter a date (yyyy-MM-dd): ");
                        String dateString = scanner.nextLine();
                        try {
                            parsedDate = LocalDate.parse(dateString);

                            if (parsedDate.isAfter(LocalDate.now())) {
                                System.out.println("Invalid date. Cannot be after " + LocalDate.now());
                                continue;
                            }
                            break;

                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                        }
                    }

                    String category;
                    while (true) {
                        System.out.print("[+]Enter category: ");
                        category = scanner.nextLine();
                        if (category.isEmpty() || category.matches("[0-9]+")) {
                            System.out.println("Invalid title...");
                            continue;
                        }
                        break;
                    }

                    int totalPages;
                    while (true) {
                        System.out.print("[+]Enter total pages: ");
                        totalPages = Integer.parseInt(scanner.nextLine());
                        if (totalPages < 1000 && totalPages > 0) break;
                        else {
                            System.out.println("invalid page size...");
                        }
                    }

                    int totalCopies;
                    while (true) {
                        System.out.print("[+]Enter total copies: ");
                        totalCopies = Integer.parseInt(scanner.nextLine());
                        if (totalCopies > 0) break;
                        else {
                            System.out.println("Invalid amount...");
                        }
                    }

                    int availableCopies = totalCopies; //Depends on loan
                    boolean isAvailable;
                    if (availableCopies > 0) isAvailable = true;
                    Book newBook = new Book(UUID.randomUUID().toString(), bookTitle, author, parsedDate, category, totalPages, totalCopies, availableCopies, true);
                    bookService.addBook(newBook);
                    continue;
                }
                case "2": {
                    Book book;
                    String bookId;

                    /* ============ FIND BOOK ============ */
                    while (true) {
                        System.out.print("[+] Enter Book ID to delete: ");
                        bookId = scanner.nextLine().trim();

                        if (bookId.isEmpty()) {
                            System.out.println("Book ID cannot be empty.");
                            continue;
                        }

                        book = bookService.findBookById(bookId);
                        if (book == null) {
                            System.out.println("Book not found. Try again.");
                            continue;
                        }
                        break;
                    }

                    /* ============ SHOW DETAILS ============ */
                    System.out.println("\nYou are about to delete:");
                    System.out.println("Title   : " + book.getTitle());
                    System.out.println("Author  : " + book.getAuthor());
                    System.out.println("Category: " + book.getCategory());
                    System.out.println("Copies  : " + book.getTotalCopies());

                    /* ============ CONFIRMATION ============ */
                    while (true) {
                        System.out.print("\nType 'YES' to confirm deletion or 'NO' to cancel: ");
                        String confirm = scanner.nextLine().trim();

                        if (confirm.equalsIgnoreCase("YES")) {
                            bookService.deleteBook(bookId);
                            System.out.println("Book deleted successfully.");
                            break;
                        }

                        if (confirm.equalsIgnoreCase("NO")) {
                            System.out.println("Deletion cancelled.");
                            break;
                        }

                        System.out.println("Invalid input. Please type YES or NO.");
                    }
                    break;
                }
                case "3": {
                    String bookId;
                    Book book;
                    while (true) {
                        System.out.print("[+] Enter Book ID: ");
                        bookId = scanner.nextLine();

                        book = bookService.findBookById(bookId);
                        if (book == null) {
                            System.out.println("Book not found. Try again.");
                            continue;
                        }
                        break;
                    }

                    /* ================== TITLE ================== */
                    System.out.print("[+] Enter new title (leave blank to keep '" + book.getTitle() + "'): ");
                    String input = scanner.nextLine();
                    if (!input.isBlank() && !input.matches("[0-9]+")) {
                        book.setTitle(input);
                    }

                    /* ================== AUTHOR ================== */
                    System.out.print("[+] Enter new author (leave blank to keep '" + book.getAuthor() + "'): ");
                    input = scanner.nextLine();
                    if (!input.isBlank() && !input.matches("[0-9]+")) {
                        book.setAuthor(input);
                    }

                    /* ================== DATE ================== */
                    while (true) {
                        System.out.print("[+] Enter new publish date (yyyy-MM-dd) or press Enter to keep "
                                + book.getPublicationDate() + ": ");
                        input = scanner.nextLine();

                        if (input.isBlank()) break;

                        try {
                            LocalDate date = LocalDate.parse(input);
                            if (date.isAfter(LocalDate.now())) {
                                System.out.println("Date cannot be in the future.");
                                continue;
                            }
                            book.setPublicationDate(date);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format.");
                        }
                    }

                    /* ================== CATEGORY ================== */
                    System.out.print("[+] Enter new category (leave blank to keep '" + book.getCategory() + "'): ");
                    input = scanner.nextLine();
                    if (!input.isBlank() && !input.matches("[0-9]+")) {
                        book.setCategory(input);
                    }

                    /* ================== TOTAL PAGES ================== */
                    while (true) {
                        System.out.print("[+] Enter total pages (leave blank to keep " + book.getTotalPages() + "): ");
                        input = scanner.nextLine();

                        if (input.isBlank()) break;

                        try {
                            int pages = Integer.parseInt(input);
                            if (pages > 0 && pages < 1000) {
                                book.setTotalPages(pages);
                                break;
                            }
                            System.out.println("Invalid page count.");
                        } catch (NumberFormatException e) {
                            System.out.println("Numbers only.");
                        }
                    }

                    /* ================== TOTAL COPIES ================== */
                    while (true) {
                        System.out.print("[+] Enter total copies (leave blank to keep " + book.getTotalCopies() + "): ");
                        input = scanner.nextLine();

                        if (input.isBlank()) break;

                        try {
                            int copies = Integer.parseInt(input);
                            if (copies > 0) {
                                int diff = copies - book.getTotalCopies();
                                book.setTotalCopies(copies);
                                book.setAvailableCopies(book.getAvailableCopies() + diff);
                                break;
                            }
                            System.out.println("Invalid amount.");
                        } catch (NumberFormatException e) {
                            System.out.println("Numbers only.");
                        }
                    }

                    /* ================== AVAILABILITY ================== */
                    book.setIsAvailable(book.getAvailableCopies() > 0);

                    bookService.updateBook(book);
                    System.out.println("Book updated successfully.");
                }
                case "4": {
                    List<Book> books = bookService.listAllBooks();
                    for (int i = 0; i < books.size(); i++) {
                        Book b = books.get(i);
                        System.out.println("Book " + (i + 1));
                        System.out.println("Book ID: " + b.getId());
                        System.out.println("Book Title: " + b.getTitle());
                        System.out.println("Author: " + b.getAuthor());
                        System.out.println("Publish date: " + b.getPublicationDate());
                        System.out.println("Category: " + b.getCategory());
                        System.out.println("Total copies: " + b.getTotalCopies());
                        System.out.println("Total pages: " + b.getTotalPages());
                        System.out.println("Available copies: " + b.getAvailableCopies());
                        System.out.println("Is Available: " + b.getIsAvailable());
                        System.out.println();
                    }
                    continue;
                }
                case "5": {
                    while (true) {
                        searchMenu();
                        String choice = scanner.nextLine().trim();

                        List<Book> results = new ArrayList<>();

                        switch (choice) {

                            case "1": {
                                System.out.print("[+] Enter book title keyword: ");
                                String keyword = scanner.nextLine().trim().toLowerCase();

                                if (keyword.isEmpty()) {
                                    System.out.println("Keyword cannot be empty.");
                                    continue;
                                }

                                results = bookService.searchBooksByTitle(keyword);
                                break;
                            }

                            case "2": {
                                System.out.print("[+] Enter author name keyword: ");
                                String keyword = scanner.nextLine().trim().toLowerCase();

                                if (keyword.isEmpty()) {
                                    System.out.println("Keyword cannot be empty.");
                                    continue;
                                }

                                results = bookService.searchBooksByAuthor(keyword);
                                break;
                            }

                            case "3": {
                                System.out.print("[+] Enter category keyword: ");
                                String keyword = scanner.nextLine().trim().toLowerCase();

                                if (keyword.isEmpty()) {
                                    System.out.println("Keyword cannot be empty.");
                                    continue;
                                }

                                results = bookService.searchBooksByCategory(keyword);
                                break;
                            }

                            case "4": {
                                System.out.print("[+] Enter book ID to search: ");
                                String id = scanner.nextLine().trim();

                                if (id.isEmpty()) {
                                    System.out.println("Book ID cannot be empty.");
                                    continue;
                                }

                                Book book = bookService.findBookById(id);
                                if (book != null) {
                                    results.add(book);
                                }
                                break;
                            }

                            case "5": {
                                bookServiceUtilLoop();
                            }

                            default:
                                System.out.println("Invalid option.");
                                continue;
                        }

                        /* ========= DISPLAY RESULTS ========= */

                        if (results.isEmpty()) {
                            System.out.println("No books found.");
                            continue;
                        }

                        System.out.println("\n=== SEARCH RESULTS ===");
                        for (Book book : results) {
                            System.out.println("----------------------------");
                            System.out.println("ID       : " + book.getId());
                            System.out.println("Title    : " + book.getTitle());
                            System.out.println("Author   : " + book.getAuthor());
                            System.out.println("Category : " + book.getCategory());
                            System.out.println("Available: " + book.getAvailableCopies());
                        }
                    }
                }
                case "6":
                    isRunning = false;
                default:
                    System.out.println("Invalid choice...");
            }
        }

    }
}
