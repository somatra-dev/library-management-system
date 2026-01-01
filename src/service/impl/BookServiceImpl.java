package service.impl;

import domain.Book;
import service.BookService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookServiceImpl implements BookService {

    private final List<Book> books = new ArrayList<>();

public BookServiceImpl(){
    books.add(new Book (UUID.randomUUID().toString(), "Habit", "Chanchhay", LocalDate.of(2021,12,12), "improve", 192, 100, 100, true));
    books.add(new Book (UUID.randomUUID().toString(), "Habit2", "Chanchhay2", LocalDate.of(2021,12,12), "improve", 192, 100, 100, true));
}
    @Override
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book Added successfully");
    }

    @Override
    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equalsIgnoreCase(updatedBook.getId())) {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @Override
    public boolean deleteBook(String id) {
        return books.removeIf(book -> book.getId().equalsIgnoreCase(id.trim()));
    }

    @Override
    public Book findBookById(String id) {
        for (Book book : books) {
            if (book.getId().equalsIgnoreCase(id.trim())) {
                return book;
            }
        }
    return null;
    }

    @Override
    public List<Book> listAllBooks() {
        return books;
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        return books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .toList();
    }

    @Override
    public List<Book> searchBooksByCategory(String category) {
        return books.stream()
                .filter(b -> b.getCategory().toLowerCase().contains(category.toLowerCase()))
                .toList();
    }

    @Override
    public List<Book> findAvailableBooks() {
        return List.of();
    }
}
