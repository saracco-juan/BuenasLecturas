package com.saracco.buenasLecturas.API;

import com.saracco.buenasLecturas.model.ApiBook;
import com.saracco.buenasLecturas.model.ApiResponse;
import com.saracco.buenasLecturas.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookApiClient {

    @Autowired
    private RestTemplate restTemplate;

    public List<Book> searchBookByTitle(String title) {
        String url = "https://openlibrary.org/search.json?q=" + title;

        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

        List<Book> books = new ArrayList<>();
        List<ApiBook> docs = response.getDocs();
        int limit = Math.min(5, docs.size());

        if (response.getDocs() != null) {
            for (int i = 0; i < limit; i++) {
                ApiBook apiBook = docs.get(i);
                String author = (apiBook.getAuthorName() != null && apiBook.getAuthorName().length > 0)
                        ? apiBook.getAuthorName()[0]
                        : "Desconocido";

                Book book = new Book(
                        apiBook.getTitle(),
                        author,
                        apiBook.getFirstPublishYear(),
                        apiBook.getOpenLibraryId()
                );
                books.add(book);
            }
        }
        return books;
    }
}
