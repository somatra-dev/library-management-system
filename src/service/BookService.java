package service;

import domain.Book;

import java.util.List;

public interface BookService {

    void addBook(Book book);

    void updateBook(Integer id, Book book);

    void deleteBook(Integer id);

    Book findBookById(Integer id);

    List<Book> findAllBooks();

    List<Book> searchBooksByTitle(String title);

    List<Book> searchBooksByAuthor(String author);

    List<Book> searchBooksByCategory(String category);

    List<Book> findAvailableBooks();

}
