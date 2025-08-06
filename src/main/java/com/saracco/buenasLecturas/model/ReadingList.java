package com.saracco.buenasLecturas.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ReadingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Reader reader;
    @ManyToOne
    private Book book;
    private String listType;

    public ReadingList() {}

    public ReadingList(Reader reader, Book book, String listType) {
        this.reader = reader;
        this.book = book;
        this.listType = listType;
    }

    public long getId() {
        return id;
    }


    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }
}
