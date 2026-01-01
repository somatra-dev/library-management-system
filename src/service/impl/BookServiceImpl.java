package service.impl;

import domain.Book;
import service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private final List<Book> books = new ArrayList<>();
    private Integer nextId = 1;

    @Override
    public void addBook(Book book) {
        book.setId(nextId++);
        book.setIsAvailable(book.getAvailableCopies() > 0);
        books.add(book);
    }

    @Override
    public void updateBook(Integer id, Book book) {
        Book existing = findBookById(id);
        if (existing != null) {
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            existing.setPublicationDate(book.getPublicationDate());
            existing.setCategory(book.getCategory());
            existing.setTotalPages(book.getTotalPages());
            existing.setTotalCopies(book.getTotalCopies());
            existing.setAvailableCopies(book.getAvailableCopies());
            existing.setIsAvailable(book.getAvailableCopies() > 0);
        }
    }

    @Override
    public void deleteBook(Integer id) {
        books.removeIf(b -> b.getId().equals(id));
    }

    @Override
    public Book findBookById(Integer id) {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Book> findAllBooks() {
        return new ArrayList<>(books);
    }

    @Override
    public Book searchBooksByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        return books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBooksByCategory(String category) {
        return books.stream()
                .filter(b -> b.getCategory().toLowerCase().contains(category.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAvailableBooks() {
        return books.stream()
                .filter(Book::getIsAvailable)
                .collect(Collectors.toList());
    }

}
