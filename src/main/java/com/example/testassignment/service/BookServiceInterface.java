package com.example.testassignment.service;

import com.example.testassignment.model.Book;

import java.util.List;

public interface BookServiceInterface {

    List<Book> processRequest(Integer year, String column, String sortBy);
}
