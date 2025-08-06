package com.saracco.buenasLecturas.service;

import com.saracco.buenasLecturas.model.Book;
import com.saracco.buenasLecturas.API.BookApiClient;
import com.saracco.buenasLecturas.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;

@Component
public class BookService {


    @Autowired
    private BookApiClient bookApiClient;
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBookByTitle(String title) {
        return bookApiClient.searchBookByTitle(title);
    }

    public Book findBookInDB(String libraryId) {
        return bookRepository.findByOpenLibraryId(libraryId);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

}
