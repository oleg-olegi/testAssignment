package com.example.testassignment.service;

import com.example.testassignment.model.Book;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IBookService {
    List<Book> processRequest(int year, String column, Sort.Direction sortBy);
}
