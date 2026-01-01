import init.DataInitializer;
import service.BookService;
import service.LibrarianService;
import service.LoanService;
import service.MemberService;
import service.impl.BookServiceImpl;
import service.impl.LibrarianServiceImpl;
import service.impl.LoanServiceImpl;
import service.impl.MemberServiceImpl;
import ui.MainMenu;

public class Main {
    public static void main(String[] args) {

        // Initialize services
        BookService bookService = new BookServiceImpl();
        LoanService loanService = new LoanServiceImpl();
        MemberService memberService = new MemberServiceImpl(bookService, loanService);
        LibrarianService librarianService = new LibrarianServiceImpl(bookService, memberService, loanService);

        // Initialize default data
        DataInitializer dataInitializer = new DataInitializer(memberService, librarianService, bookService, loanService);
        dataInitializer.initializeDefaultData();

        // Start the application
        MainMenu mainMenu = new MainMenu(memberService, librarianService, bookService, loanService);
        mainMenu.start();

    }
}
