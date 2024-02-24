package com.example.testassignment.model;

import java.util.List;
import java.util.Objects;

public class Book {

    private Integer id;
    private Integer isbn;
    private String title;
    private String seriesTitle;
    private String seriesReleaseNumber;
    private String authors;
    private String publisher;
    private String language;
    private String description;
    private Integer numPages;
    private String format;
    private List<String> genres;
    private String publicationDate;
    private Double ratingScore;
    private Integer numRatings;
    private Integer numReviews;
    private Integer currentReaders;
    private Integer wantToRead;
    private Double price;
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.length() < 2) {
            this.title = null;
        } else {
            this.title = title.trim();
        }
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        if (seriesTitle.length() < 2) {
            this.seriesTitle = null;
        } else {
            this.seriesTitle = seriesTitle.trim();
        }
    }

    public String getSeriesReleaseNumber() {
        return seriesReleaseNumber;
    }

    public void setSeriesReleaseNumber(String seriesReleaseNumber) {
        this.seriesReleaseNumber = seriesReleaseNumber;
    }


    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        if (authors.length() < 2) {
            this.authors = null;
        } else {
            this.authors = authors.trim();
        }
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if (publisher.length() < 2) {
            this.publisher = null;
        } else {
            this.publisher = publisher.trim();
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        if (language.length() < 2) {
            this.language = null;
        } else {
            this.language = language.trim();
        }

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.isEmpty()) {
            this.description = null;
        } else {
            this.description = description;
        }
    }

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        if (genres.size() == 0) {
            this.genres = null;
        } else
            this.genres = genres;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Double getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(Double ratingScore) {
        this.ratingScore = ratingScore;
    }

    public Integer getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(Integer numRatings) {
        this.numRatings = numRatings;
    }

    public Integer getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(Integer numReviews) {
        this.numReviews = numReviews;
    }

    public Integer getCurrentReaders() {
        return currentReaders;
    }

    public void setCurrentReaders(Integer currentReaders) {
        this.currentReaders = currentReaders;
    }

    public Integer getWantToRead() {
        return wantToRead;
    }

    public void setWantToRead(Integer wantToRead) {
        this.wantToRead = wantToRead;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url.isEmpty()) {
            this.url = null;
        } else
            this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && isbn.equals(book.isbn) && title.equals(book.title)
                && seriesTitle.equals(book.seriesTitle) && seriesReleaseNumber.equals(book.seriesReleaseNumber)
                && authors.equals(book.authors) && publisher.equals(book.publisher)
                && language.equals(book.language) && description.equals(book.description)
                && numPages.equals(book.numPages) && format.equals(book.format) && genres.equals(book.genres)
                && publicationDate.equals(book.publicationDate) && ratingScore.equals(book.ratingScore)
                && numRatings.equals(book.numRatings) && numReviews.equals(book.numReviews)
                && currentReaders.equals(book.currentReaders) && wantToRead.equals(book.wantToRead)
                && price.equals(book.price) && url.equals(book.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, seriesTitle, seriesReleaseNumber, authors,
                publisher, language, description, numPages, format, genres, publicationDate,
                ratingScore, numRatings, numReviews, currentReaders, wantToRead, price, url);
    }
}
