package com.example.library.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.library.entity.Book;
import com.example.library.repos.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BookService
 */
@Service
public class BookService {

    private static final int booksPerPage = 5;

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooks(int numOfPages) {
        if (numOfPages < 1) {
            return bookRepository.findAll().subList(0, booksPerPage);
        } else if (bookRepository.count() < numOfPages * booksPerPage) {
            return bookRepository.findAll();
        } else {
            return bookRepository.findAll().subList(0, numOfPages * booksPerPage);
        }
    }

    public Optional<Book> findBook(String isn) {
        return bookRepository.findByIsn(isn);
    }

    public String addBook(Book book) {
        if (this.contains(book.getIsn())) {
            return "Error";
        } else {
            book.setOwner(null);
            bookRepository.save(book);
            return "Success";
        }
    }

    public String addBook(Map<String, String> bookAttr) {
        if (bookAttr.get("isn") == null || bookAttr.get("author") == null || bookAttr.get("name") == null
                || bookAttr.get("isn").isEmpty() || bookAttr.get("author").isEmpty() || bookAttr.get("name").isEmpty()
                || this.contains(bookAttr.get("isn"))) {
            return "Error";
        } else {
            Book book = new Book(bookAttr.get("isn"), bookAttr.get("author"), bookAttr.get("name"), null);
            bookRepository.save(book);
            return "Success";
        }
    }

    public String deleteBook(String isn) {
        if (bookRepository.deleteByIsn(isn) != 0) {
            return "Success";
        } else {
            return "Error";
        }
    }

    public String takeBook(String isn, String userName) {
        Optional<Book> book = bookRepository.findByIsn(isn);

        if (!book.isPresent()) {
            return "Error";
        } else {
            book.get().setOwner(userName);
            bookRepository.update(book.get());
            return "Success";
        }
    }

    public String returnBook(String isn, String userName) {
        Optional<Book> book = bookRepository.findByIsn(isn);

        if (!book.isPresent() || !book.get().getOwner().equals(userName)) {
            return "Error";
        } else {
            book.get().setOwner(null);
            bookRepository.update(book.get());
            return "Success";
        }
    }

    public boolean contains(String isn) {
        if (bookRepository.findByIsn(isn).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

}