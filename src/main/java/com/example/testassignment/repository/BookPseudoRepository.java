package com.example.testassignment.repository;

import com.example.testassignment.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookPseudoRepository {
    private List<Book> bookList;
}
