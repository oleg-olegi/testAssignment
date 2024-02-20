package com.example.testassignment.service;

import com.example.testassignment.model.Book;

import java.util.List;

public interface BookServiceInterface {
    public abstract List<Book> processRequest(int year, String column, String sort);
}
