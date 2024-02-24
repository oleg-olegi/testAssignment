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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        Comparator<Book> comparator = (b1, b2) -> {
            if (sortBy.equalsIgnoreCase("ASC")) {
                return compareByColumn(b1, b2, column);
            } else {
                return compareByColumn(b2, b1, column); // Обратная сортировка для DESC
            }
        };
        return books.stream()
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
               // .filter(y -> Integer.parseInt(y.getPublicationDate()) == year)
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
            logger.info("Stream opened");
            boolean isFirstLine = true; // Флаг для определения первой строки
            String line;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;// Устанавливаем флаг в false после пропуска первой строки
                    continue; // Пропускаем первую строку с наименованиями  колонок CSV файла
                }
                // Обработка строки из CSV файла и создание объекта Book

                String[] values = line.split(",", -1); // Разбиваем строку на массив значений

                logger.info("Array = {}", Arrays.toString(values));

                // Создание объекта Book
                Book book = new Book();

                // Устанавливаем значения из массива в объект Book, преобразуя их при необходимости
                for (int i = 0; i < values.length; i++) {
                    switch (i) {
                        case 0:
                            book.setId(parseInteger(values[i]));
                            break;
                        case 1:
                            book.setIsbn(parseInteger(values[i]));
                            break;
                        case 2:
                            book.setTitle(values[i]);
                            break;
                        case 3:
                            book.setSeriesTitle(values[i]);
                            break;
                        case 4:
                            book.setAuthors(values[i]);
                            break;
                        case 5:
                            book.setPublisher(values[i]);
                            break;
                        case 6:
                            book.setLanguage(values[i]);
                            break;
                        case 7:
                            book.setDescription(values[i]);
                            break;
                        case 8:
                            book.setNumPages(parseInteger(values[i]));
                            break;
                        case 9:
                            book.setFormat(values[i]);
                            break;
                        case 10:
                            book.setGenres(Arrays.asList(values[i]));
                            break;
                        case 11:
                            book.setPublicationDate(values[i]);
                            break;
                        case 12:
                            book.setRatingScore(parseDouble(values[i]));
                            break;
                        case 13:
                            book.setNumRatings(parseInteger(values[i]));
                            break;
                        case 14:
                            book.setNumReviews(parseInteger(values[i]));
                            break;
                        case 15:
                            book.setCurrentReaders(parseInteger(values[i]));
                            break;
                        case 16:
                            book.setWantToRead(parseInteger(values[i]));
                            break;
                        case 17:
                            book.setPrice(parseDouble(values[i]));
                            break;
                        case 18:
                            book.setUrl(values[i]);
                            break;

                        default:
                            // Если индекс больше 19, значит в строке больше элементов, чем требуется для объекта Book
                            // В этом случае мы просто игнорируем лишние элементы
                            break;
                    }
                }

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
