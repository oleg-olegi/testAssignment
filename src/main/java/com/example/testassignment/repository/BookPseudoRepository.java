package com.example.testassignment.repository;

import com.example.testassignment.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookPseudoRepository {
    private final List<Book> bookList;

    public BookPseudoRepository(List<Book> books) {
        this.bookList = books;
    }

    public void add(Book book) {
        bookList.add(book);
    }

    public int size() {
        return bookList.size();
    }

    public List<Book> getBookList() {
        return bookList;
    }
}
