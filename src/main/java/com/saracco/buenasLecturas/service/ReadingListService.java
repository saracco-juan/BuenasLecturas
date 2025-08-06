package com.saracco.buenasLecturas.service;

import com.saracco.buenasLecturas.model.Book;
import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.model.ReadingList;
import com.saracco.buenasLecturas.model.Review;
import com.saracco.buenasLecturas.repository.ReadingListRepository;
import com.saracco.buenasLecturas.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingListService {

    @Autowired
    private ReadingListRepository readingListRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReadingList> findByReaderAndListType(Reader reader, String listType) {
        return readingListRepository.findByReaderAndListType(reader, listType);
    }

    public void moveToRead(Reader reader, Book book) {

        ReadingList entry = readingListRepository.findByReaderAndBookAndListType(reader, book, "QUIERO_LEER");

        if (entry != null) {
            readingListRepository.delete(entry);
        }

        boolean isRead = readingListRepository.existsByReaderAndBookAndListType(reader, book, "LEIDOS");

        if (!isRead) {
            ReadingList newEntry = new ReadingList();
            newEntry.setReader(reader);
            newEntry.setBook(book);
            newEntry.setListType("LEIDOS");

            readingListRepository.save(newEntry);
        }
    }

    public void removeFromRead(Reader reader,Book book) {
        ReadingList entrada = readingListRepository.findByReaderAndBookAndListType(reader, book, "LEIDOS");
        if (entrada != null) {
            readingListRepository.delete(entrada);
        }

        Review review = reviewRepository.findByReaderAndBook(reader, book);
        if (review != null) {
            reviewRepository.delete(review);
        }
    }

}
