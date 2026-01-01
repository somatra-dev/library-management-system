package service;

import domain.Book;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    void updateBook(Book updatedBook);

    boolean deleteBook(String id);

    Book findBookById(String id);

    List<Book> listAllBooks();

    List<Book> searchBooksByTitle(String title);

    List<Book> searchBooksByAuthor(String author);

    List<Book> searchBooksByCategory(String category);

    List<Book> findAvailableBooks();

}
