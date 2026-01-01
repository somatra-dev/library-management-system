package init;

import domain.Book;
import domain.Librarian;
import domain.Loan;
import domain.Member;
import service.BookService;
import service.LibrarianService;
import service.LoanService;
import service.MemberService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataInitializer {

    private final MemberService memberService;
    private final LibrarianService librarianService;
    private final BookService bookService;
    private final LoanService loanService;

    public DataInitializer(MemberService memberService, LibrarianService librarianService,
                           BookService bookService, LoanService loanService) {
        this.memberService = memberService;
        this.librarianService = librarianService;
        this.bookService = bookService;
        this.loanService = loanService;
    }

    public void initializeDefaultData() {
        initializeDefaultBooks();
        initializeDefaultMember();
        initializeDefaultLibrarian();
        initializeDefaultLoan();
    }

    private void initializeDefaultBooks() {
        Book book1 = new Book();
        book1.setTitle("Java Programming");
        book1.setAuthor("James Gosling");
        book1.setPublicationDate(LocalDate.of(2020, 1, 15));
        book1.setCategory("Programming");
        book1.setTotalPages(450);
        book1.setTotalCopies(5);
        book1.setAvailableCopies(5);
        bookService.addBook(book1);

        Book book2 = new Book();
        book2.setTitle("Clean Code");
        book2.setAuthor("Robert C. Martin");
        book2.setPublicationDate(LocalDate.of(2008, 8, 1));
        book2.setCategory("Software Engineering");
        book2.setTotalPages(464);
        book2.setTotalCopies(3);
        book2.setAvailableCopies(3);
        bookService.addBook(book2);

        Book book3 = new Book();
        book3.setTitle("Design Patterns");
        book3.setAuthor("Gang of Four");
        book3.setPublicationDate(LocalDate.of(1994, 10, 21));
        book3.setCategory("Software Engineering");
        book3.setTotalPages(395);
        book3.setTotalCopies(4);
        book3.setAvailableCopies(4);
        bookService.addBook(book3);

        Book book4 = new Book();
        book4.setTitle("The Pragmatic Programmer");
        book4.setAuthor("David Thomas");
        book4.setPublicationDate(LocalDate.of(2019, 9, 13));
        book4.setCategory("Programming");
        book4.setTotalPages(352);
        book4.setTotalCopies(2);
        book4.setAvailableCopies(2);
        bookService.addBook(book4);

        Book book5 = new Book();
        book5.setTitle("Introduction to Algorithms");
        book5.setAuthor("Thomas H. Cormen");
        book5.setPublicationDate(LocalDate.of(2009, 7, 31));
        book5.setCategory("Algorithms");
        book5.setTotalPages(1312);
        book5.setTotalCopies(3);
        book5.setAvailableCopies(3);
        bookService.addBook(book5);

        System.out.println("Default books initialized: 5 books added");
    }

    private void initializeDefaultMember() {
        Member defaultMember = new Member();
        defaultMember.setName("Member");
        defaultMember.setEmail("member@library.com");
        defaultMember.setPassword("member123");
        defaultMember.setPhoneNumber("012345678");
        defaultMember.setAddress("Phnom Penh, Cambodia");
        defaultMember.setMembershipDate(LocalDate.now());
        defaultMember.setIsActive(true);
        defaultMember.setIsMember(true);
        defaultMember.setLoans(new ArrayList<>());

        memberService.addMember(defaultMember);
        System.out.println("Default member initialized: " + defaultMember.getEmail());
    }

    private void initializeDefaultLibrarian() {
        Librarian defaultLibrarian = new Librarian();
        defaultLibrarian.setName("Admin");
        defaultLibrarian.setEmail("admin@library.com");
        defaultLibrarian.setPassword("admin123");
        defaultLibrarian.setPhoneNumber("098765432");
        defaultLibrarian.setIsLibrarian(true);
        defaultLibrarian.setManagedBooks(new ArrayList<>());

        librarianService.addLibrarian(defaultLibrarian);
        System.out.println("Default librarian initialized: " + defaultLibrarian.getEmail());
    }

    private void initializeDefaultLoan() {
        Member member = memberService.findMemberById(1);
        List<Book> allBooks = bookService.findAllBooks();

        if (member != null && !allBooks.isEmpty()) {
            // Borrow first two books for the default member
            List<Book> booksToBorrow = Arrays.asList(allBooks.get(0), allBooks.get(1));
            Loan loan = loanService.borrowBook(member, booksToBorrow);

            if (loan != null) {
                System.out.println("Default loan initialized: Member borrowed " + booksToBorrow.size() + " books");
            }
        }
    }

}
