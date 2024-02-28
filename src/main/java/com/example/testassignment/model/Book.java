package com.example.testassignment.model;

import java.util.List;
import java.util.Objects;

public class Book {

    private String id;
    private String ISBN;
    private String title;
    private String seriesTitle;
    private String seriesReleaseNumber;
    private String authors;
    private String publisher;
    private String language;
    private String description;
    private String numPages;
    private String format;
    private List<String> genres;
    private String publicationDate;
    private String ratingScore;
    private String numRatings;
    private String numReviews;
    private String currentReaders;
    private String wantToRead;
    private String price;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
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
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getNumPages() {
        return numPages;
    }

    public void setNumPages(String numPages) {
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
        this.genres = genres;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(String ratingScore) {
        this.ratingScore = ratingScore;
    }

    public String getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(String numRatings) {
        this.numRatings = numRatings;
    }

    public String getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(String numReviews) {
        this.numReviews = numReviews;
    }

    public String getCurrentReaders() {
        return currentReaders;
    }

    public void setCurrentReaders(String currentReaders) {
        this.currentReaders = currentReaders;
    }

    public String getWantToRead() {
        return wantToRead;
    }

    public void setWantToRead(String wantToRead) {
        this.wantToRead = wantToRead;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && ISBN.equals(book.ISBN) && title.equals(book.title)
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
        return Objects.hash(id, ISBN, title, seriesTitle, seriesReleaseNumber, authors,
                publisher, language, description, numPages, format, genres, publicationDate,
                ratingScore, numRatings, numReviews, currentReaders, wantToRead, price, url);
    }
}
