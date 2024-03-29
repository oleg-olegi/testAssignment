package com.example.testassignment.controller;

import com.example.testassignment.model.Book;
import com.example.testassignment.service.BookService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api")
public class Top10BooksController {

    private final BookService bookService;

    public Top10BooksController(BookService bookService) {
        this.bookService = bookService;
    }
    @ApiResponses({
            @ApiResponse(
                    description = "Возвращает 10 книг, отсортированных по году и колонке"
            )
    })
    @GetMapping("/top10")
    @ResponseBody
    public List<Book> tpo10(@RequestParam(required = false) Integer year,
                            @RequestParam String column,
                            @RequestParam String sort) {
        return bookService.processRequest(year, column, sort);
    }
}
