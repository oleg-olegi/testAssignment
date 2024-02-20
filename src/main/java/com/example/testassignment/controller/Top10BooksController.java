package com.example.testassignment.controller;

import com.example.testassignment.model.Book;
import com.example.testassignment.service.BookService;
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

    @GetMapping("/top10")
    @ResponseBody
    public List<Book> tpo10(@RequestParam(required = false) int year,
                            @RequestParam String column,
                            @RequestParam String sort) {
        return bookService.processRequest(year, column, sort);
    }


//    private List<Book> loadDataset() {
//        List<Book> books = new ArrayList<>();
//        try {
//            URL url = new URL("https://www.kaggle.com/datasets/cristaliss/ultimate-book-collection-top-100-books-up-to-2023?resource=download");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                 Обработка строки из CSV файла и создание объекта Book
//                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
//                 Создание объекта Book
//                Book book = new Book();
//                book.setId(Integer.parseInt(values[0]));
//                book.setBook(values[1]);
//                book.setSeries(values[2]);
//                book.setReleaseNumber(values[3]);
//                book.setAuthor(values[4]);
//                book.setDescription(values[5]);
//                book.setNumPages(Integer.parseInt(values[6]));
//                book.setFormat(values[7]);
//                book.setGenres(Arrays.asList(values[8].split("\\s*,\\s*")));
//                book.setPublicationDate(values[9]);
//                book.setRating(Double.parseDouble(values[10]));
//                book.setNumberOfVoters(Integer.parseInt(values[11]));
//                books.add(book);
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return books;
//    }
}
