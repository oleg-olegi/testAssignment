package com.example.testassignment.service;

import com.example.testassignment.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService implements BookServiceInterface {
    @Value("${dataset.path}")
    private String datasetPath;
    @Value("${path.to.csv.folder}")
    private String pathToCsvDir;
    private final static Logger logger = LoggerFactory.getLogger(BookService.class);
    private List<Book> books;

    @PostConstruct
    public void init() {
        books = loadDataset();
    }

    @Override
    public List<Book> processRequest(int year, String column, String sortBy) {
        checkColumnParam(column);
        checkSortParam(sortBy);
        books.stream()
                .filter()
                .sorted();
        books.sort((b1, b2) -> {
            if (sortBy.equalsIgnoreCase("ASC")) {
                return compareByColumn(b1, b2, column);
            } else {
                return compareByColumn(b1, b2, column);
            }
        });
        return books.subList(0, Math.min(10, books.size()));
    }

    private int compareByColumn(Book b1, Book b2, String column) {
        return switch (column.toLowerCase()) {
            case "book" -> b1.getTitle().compareToIgnoreCase(b2.getTitle());
            case "author" -> b1.getAuthors().compareToIgnoreCase(b2.getAuthors());
            case "numpages" -> Integer.compare(b1.getNumPages(), b2.getNumPages());
            case "publicationdate" -> b1.getPublicationDate().compareToIgnoreCase(b2.getPublicationDate());
            case "rating" -> Double.compare(b1.getRatingScore(), b2.getRatingScore());
            case "numberofvoters" -> Integer.compare(b1.getNumReviews(), b2.getNumReviews());
            default -> throw new IllegalArgumentException("Invalid value for parameter column");
        };
    }

    private void checkSortParam(String sort) {
        // Проверка корректности параметра sort
        if (!sort.equalsIgnoreCase("ASC") && !sort.equalsIgnoreCase("DESC")) {
            throw new IllegalArgumentException("Invalid value for parameter sort");
        }
    }

    private void checkColumnParam(String column) {
        // Проверка корректности параметра column
        String[] validColumns = {"book", "author", "numPages", "publicationDate", "rating", "numberOfVoters"};
        boolean isValidColumn = false;
        for (String validColumn : validColumns) {
            if (column.equalsIgnoreCase(validColumn)) {
                isValidColumn = true;
                break;
            }
        }
        if (!isValidColumn) {
            throw new IllegalArgumentException("Invalid value for parameter column");
        }
    }

    private List<Book> loadDataset() {
        List<Book> books = new ArrayList<>();
        try {
            File csvFile = new File(pathToCsvDir);
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
//            URL url = new URL(datasetPath);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            logger.info("Stream opened");
            boolean isFirstLine = true; // Флаг для определения первой строки
            String line;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;// Устанавливаем флаг в false после пропуска первой строки
                    continue; // Пропускаем первую строку
                }
                // Обработка строки из CSV файла и создание объекта Book
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (values.length < 20) {
                    // Пропускаем строку, если количество элементов меньше ожидаемого
                    continue;
                }
                logger.info("Array = {}", (Object) values);
                // Создание объекта Book
                Book book = new Book();
                book.setId(values[0]);
                logger.info("Поле {}", book.getId());
                book.setIsbn(values[1]);
                book.setTitle(values[2]);
                book.setSeriesTitle(values[3]);
                book.setSeriesReleaseNumber(values[4]);
                book.setAuthors(values[5]);
                book.setPublisher(values[6]);
                book.setLanguage(values[7]);
                book.setDescription(values[8]);
                book.setNumPages(parseInteger(values[9]));
                book.setFormat(values[10]);
                book.setGenres(Arrays.asList(values[11].split(",")));
                book.setPublicationDate(values[12]);
                book.setRatingScore(parseDouble(values[13]));
                book.setNumRatings(parseInteger(values[14]));
                book.setNumReviews(parseInteger(values[15]));
                book.setCurrentReaders(parseInteger(values[16]));
                book.setWantToRead(parseInteger(values[17]));
                book.setPrice(parseDouble(values[18]));
                book.setUrl(values[19]);
                books.add(book);
                logger.info("Добавлена книга {}", book);
            }
            reader.close();
        } catch (IOException e) {
            logger.info("Error reading line from CSV file: {}", e.getMessage());
            e.printStackTrace();
        }
        logger.info("размер коллекции {}", books.size());
        return books;
    }

    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
