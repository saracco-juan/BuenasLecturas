package com.saracco.buenasLecturas.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String title;
    private String authorName;
    private int firstPublishYear;
    private String openLibraryId;


    @OneToMany(mappedBy = "book")
    private List<ReadingList> readingList;

    public Book() {}

    public Book(String title, String authorName, int firstPublishYear,  String openLibraryId) {
        this.title = title;
        this.authorName = authorName;
        this.firstPublishYear = firstPublishYear;
        this.readingList = new ArrayList<>();
        this.openLibraryId = openLibraryId;
    }

    public long getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getFirstPublishYear() {
        return firstPublishYear;
    }

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setFirstPublishYear(int firstPublishYear) {
        this.firstPublishYear = firstPublishYear;
    }

    public void setOpenLibraryId(String openLibraryId) {
        this.openLibraryId = openLibraryId;
    }

    public void setReadingList(List<ReadingList> readingList) {
        this.readingList = readingList;
    }
}
