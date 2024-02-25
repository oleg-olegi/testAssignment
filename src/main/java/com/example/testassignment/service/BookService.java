package com.example.testassignment.service;

import com.example.testassignment.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements BookServiceInterface {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    @Value("${dataset.path}")
    private String datasetPath;
    @Value("${path.to.csv.folder}")
    private String pathToCsvDir;

    @Autowired
    private List<Book> bookRepository;
    @Autowired
    private CheckParamsForProcessRequest checkParamsForProcessRequest;
    @Autowired
    private ParsingStringsFromCSV parsingStringsFromCSV;

    @PostConstruct
    public void init() {
        bookRepository = loadDataset();
    }

    @Override
    public List<Book> processRequest(int year, String column, String sortBy) {
        checkParamsForProcessRequest.checkColumnParam(column);
        checkParamsForProcessRequest.checkSortParam(sortBy);
        Comparator<Book> comparator = (b1, b2) -> {
            if (("ASC").equalsIgnoreCase(sortBy)) { //здесь вопросы
                return compareByColumn(b1, b2, column);
            } else {
                return compareByColumn(b2, b1, column); // Обратная сортировка для DESC
            }
        };
        return bookRepository.stream()
                .filter(b -> {
                    switch (column.toLowerCase()) {
                        case "book" -> {
                            return b.getTitle() != null;
                        }
                        case "author" -> {
                            return b.getAuthors() != null;
                        }
                        case "numpages" -> {
                            return b.getNumPages() != null;
                        }
                        case "publicationdate" -> {
                            return b.getPublicationDate() != null;
                        }
                        case "rating" -> {
                            return b.getRatingScore() != null;
                        }
                        case "numberofvoters" -> {
                            return b.getNumReviews() != null;
                        }
                        default -> throw new IllegalArgumentException("Invalid value for parameter column");
                    }
                })
                .sorted(comparator)
                .limit(10)
                .collect(Collectors.toList());
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

    private List<Book> loadDataset() {
        try {
            File csvFile = new File(pathToCsvDir);
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            LOGGER.info("Stream opened");
            boolean isFirstLine = true; // Флаг для определения первой строки
            String line;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;// Устанавливаем флаг в false после пропуска первой строки
                    continue; // Пропускаем первую строку с наименованиями  колонок CSV файла
                }
                // Обработка строки из CSV файла и создание объекта Book
                String[] values = line.split(",", -1); // Разбиваем строку на массив значений
                LOGGER.info("Array = {}", Arrays.toString(values));
                // Создание объекта Book
                Book book = new Book();
                // Устанавливаем значения из массива в объект Book, преобразуя их при необходимости
                setBookFields(values, book);
                bookRepository.add(book);
                LOGGER.info("Добавлена книга {}", book);
            }
            reader.close();
        } catch (IOException e) {
            LOGGER.info("Error reading line from CSV file: {}", e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("размер коллекции {}", bookRepository.size());
        return bookRepository;
    }

    private void setBookFields(String[] values, Book book) {
        for (int i = 0; i < values.length; i++) {
            switch (i) {
                case 0 -> book.setId(parsingStringsFromCSV.parseInteger(values[i]));
                case 1 -> book.setIsbn(parsingStringsFromCSV.parseInteger(values[i]));
                case 2 -> book.setTitle(values[i]);
                case 3 -> book.setSeriesTitle(values[i]);
                case 4 -> book.setAuthors(values[i]);
                case 5 -> book.setPublisher(values[i]);
                case 6 -> book.setLanguage(values[i]);
                case 7 -> book.setDescription(values[i]);
                case 8 -> book.setNumPages(parsingStringsFromCSV.parseInteger(values[i]));
                case 9 -> book.setFormat(values[i]);
                case 10 -> book.setGenres(Arrays.asList(values[i]));
                case 11 -> book.setPublicationDate(values[i]);
                case 12 -> book.setRatingScore(parsingStringsFromCSV.parseDouble(values[i]));
                case 13 -> book.setNumRatings(parsingStringsFromCSV.parseInteger(values[i]));
                case 14 -> book.setNumReviews(parsingStringsFromCSV.parseInteger(values[i]));
                case 15 -> book.setCurrentReaders(parsingStringsFromCSV.parseInteger(values[i]));
                case 16 -> book.setWantToRead(parsingStringsFromCSV.parseInteger(values[i]));
                case 17 -> book.setPrice(parsingStringsFromCSV.parseDouble(values[i]));
                case 18 -> book.setUrl(values[i]);
            }
        }
    }
}
