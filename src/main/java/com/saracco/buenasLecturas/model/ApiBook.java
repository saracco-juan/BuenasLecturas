package com.saracco.buenasLecturas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiBook {

    private String title;

    @JsonProperty("cover_edition_key")
    private String openLibraryId;

    @JsonProperty("author_name")
    private String[] authorName;

    @JsonProperty("first_publish_year")
    private int firstPublishYear;

    public String getTitle() {
        return title;
    }

    public String[] getAuthorName() {
        return authorName;
    }

    public int getFirstPublishYear() {
        return firstPublishYear;
    }

    public String getOpenLibraryId() {
        return openLibraryId;
    }
}
