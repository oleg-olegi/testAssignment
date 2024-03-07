package com.example.testassignment.service;

import com.example.testassignment.model.Book;
import com.example.testassignment.repository.BookPseudoRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.testassignment.model.Constants.MANDATORY_FIELDS;

@Service
public class BookService implements BookServiceInterface {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    @Value("${dataset.path}")
    private String datasetPath;
    @Value("${path.to.csv.folder}")
    private String pathToCsvDir;

    private final BookPseudoRepository bookRepository;

    private final CheckParamsForProcessRequest checkParamsForProcessRequest;


    public BookService(BookPseudoRepository bookRepository,
                       CheckParamsForProcessRequest checkParamsForProcessRequest
    ) {
        this.bookRepository = bookRepository;
        this.checkParamsForProcessRequest = checkParamsForProcessRequest;
    }

    @PostConstruct
    void init() {
        try {
            loadDataset();
        } catch (URISyntaxException | IOException | CsvException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException();
        }
    }


    @Override
    public List<Book> processRequest(Integer year, String column, String sortBy) {
        checkParamsForProcessRequest.checkColumnParam(column);
        Comparator<Book> comparator = (bookOne, bookOther) ->
                switch (sortBy) {
                    case "ASC" -> compareByColumn(bookOne, bookOther, column);
                    case "DESC" -> compareByColumn(bookOther, bookOne, column);
                    default -> throw new IllegalArgumentException("Invalid value for sortBy: " + sortBy);
                };
        return bookRepository.getBookList().stream()
                .filter(book -> MANDATORY_FIELDS.contains(column) || getFieldValue(book, column) != null)
                .filter(book -> year == null || book.getPublicationDate() != null &&
                        book.getPublicationDate().contains(year.toString()))
                .sorted(comparator)
                .limit(10)
                .collect(Collectors.toList());
    }


    private int compareByColumn(Book bookOne, Book bookOther, String column) {
        return getFieldValue(bookOne, column).compareToIgnoreCase(getFieldValue(bookOther, column));
    }

    private String getFieldValue(Book book, String column) {
        try {
            Class<? extends Book> bookClass = book.getClass();
            Field field = bookClass.getDeclaredField(column);
            field.setAccessible(true);
            return String.valueOf(field.get(book));
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    /**
     * tutorial
     * <a href="https://www.baeldung.com/opencsv">...</a>
     */

    private void loadDataset() throws URISyntaxException, IOException, CsvException {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                .build();
        Path path = Paths.get(pathToCsvDir);
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build()) {
                csvReader.readAll()
                        .forEach(line -> {
                            Book book = new Book();
                            setBookFields(line, book);
                            bookRepository.add(book);
                        });
                LOGGER.info("Size of collection {}", bookRepository.size());
            }
        }
    }

    private void setBookFields(String[] values, Book book) {
        for (int i = 0; i < values.length; i++) {
            switch (i) {
                case 0 -> book.setId(values[i]);
                case 1 -> book.setISBN(values[i]);
                case 2 -> book.setTitle(values[i]);
                case 3 -> book.setSeriesTitle(values[i]);
                case 4 -> book.setSeriesReleaseNumber(values[i]);
                case 5 -> book.setAuthors(values[i]);
                case 6 -> book.setPublisher(values[i]);
                case 7 -> book.setLanguage(values[i]);
                case 8 -> book.setDescription(values[i]);
                case 9 -> book.setNumPages(values[i]);
                case 10 -> book.setFormat(values[i]);
                case 11 -> book.setGenres(Arrays.asList(values[i]));
                case 12 -> book.setPublicationDate(values[i]);
                case 13 -> book.setRatingScore(String.valueOf(values[i]));
                case 14 -> book.setNumRatings(values[i]);
                case 15 -> book.setNumReviews(values[i]);
                case 16 -> book.setCurrentReaders(values[i]);
                case 17 -> book.setWantToRead(values[i]);
                case 18 -> book.setPrice(values[i]);
                case 19 -> book.setUrl(values[i]);
            }
        }
    }
}
