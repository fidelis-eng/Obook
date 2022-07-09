package com.Obook.Model;

import java.util.Arrays;
import java.util.List;

public class Books {
    private int _id;
    private String title;
    private String isbn;
    private int pageCount;
    private String publishedDate;
    private String shortDescription;
    private String longDescription;
    private String status;
    private List<String> authors;
    private List<String> categories;
    private double price;

    public Books(int _id, String title, String isbn, int pageCount, String publishedDate, String shortDescription, String longDescription, String status, List<String> authors, List<String> categories, double price) {
        this._id = _id;
        this.title = title;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.publishedDate = publishedDate;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.status = status;
        this.authors = authors;
        this.categories = categories;
        this.price = price;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int get_id() {
        return _id;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getCategories() {
        return categories;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

}
