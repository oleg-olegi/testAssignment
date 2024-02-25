package com.example.testassignment.service;

import com.example.testassignment.model.Book;
import com.example.testassignment.repository.BookPseudoRepository;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.*;
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
public class BookService implements IBookService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    @Value("${dataset.path}")
    private String datasetPath;
    @Value("${path.to.csv.folder}")
    private String pathToCsvDir;

    private final BookPseudoRepository bookRepository;
    private final CheckParamsForProcessRequest checkParamsForProcessRequest;
    private final ParsingStringsFromCSV parsingStringsFromCSV;

    public BookService(BookPseudoRepository bookRepository,
                       CheckParamsForProcessRequest checkParamsForProcessRequest,
                       ParsingStringsFromCSV parsingStringsFromCSV) {
        this.bookRepository = bookRepository;
        this.checkParamsForProcessRequest = checkParamsForProcessRequest;
        this.parsingStringsFromCSV = parsingStringsFromCSV;
        try {
            load();
        } catch (URISyntaxException | IOException | CsvException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> processRequest(int year, String column, Sort.Direction sortBy) {
        checkParamsForProcessRequest.checkColumnParam(column);
        Comparator<Book> comparator = (b1, b2) -> switch (sortBy) {
            case ASC -> compareByColumn(b1, b2, column);
            case DESC -> compareByColumn(b2, b1, column);
        };
        return bookRepository.getBookList().stream()
                .filter(book -> {
                    if (MANDATORY_FIELDS.contains(column)) {
                        return getFieldValue(book, column) != null;
                    }
                    return true;
                })
                .sorted(comparator)
                .limit(10)
                .collect(Collectors.toList());
    }

    private int compareByColumn(Book bookOne, Book bookOther, String column) {
        return getFieldValue(bookOne, column).compareToIgnoreCase(getFieldValue(bookOther, column));
    }

    private String getFieldValue(Book book, String column) {
        try {
            return String.valueOf(Book.class.getDeclaredField(column).get(book));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalArgumentException("Invalid value for parameter column");
        }
    }

    private void load() throws URISyntaxException, IOException, CsvException {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();
        Path path = Paths.get(
                ClassLoader.getSystemResource(pathToCsvDir).toURI());
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()) {
                csvReader.readAll()
                        .forEach(lineArr -> {
                            Book book = new Book();
                            setBookFields(lineArr, book);
                            bookRepository.add(book);
                        });
            }
        }
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
