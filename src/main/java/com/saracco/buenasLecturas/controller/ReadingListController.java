package com.saracco.buenasLecturas.controller;

import com.saracco.buenasLecturas.model.Book;
import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.model.ReadingList;
import com.saracco.buenasLecturas.repository.BookRepository;
import com.saracco.buenasLecturas.repository.ReadingListRepository;
import com.saracco.buenasLecturas.service.ReadingListService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/reading-list")
public class ReadingListController {

    @Autowired
    private ReadingListRepository readingListRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReadingListService readingListService;


    @PostMapping("/add")
    public String addToReadingList(@RequestParam String title,
                                   @RequestParam String authorName,
                                   @RequestParam int firstPublishYear,
                                   @RequestParam String openLibraryId,
                                   @RequestParam String listType,
                                   HttpSession session) {

        if (session.getAttribute("reader") == null) {
            return "redirect:/login";
        }


        Reader reader = (Reader) session.getAttribute("reader");

        if (reader == null) {
            return "redirect:/login";
        }

        Book book = bookRepository.findByOpenLibraryId(openLibraryId);

        if (book == null) {
            book = new Book(title, authorName, firstPublishYear, openLibraryId);
            bookRepository.save(book);
        }


        if (readingListRepository.existsByReaderAndBookAndListType(reader, book, listType)) {
            return "redirect:/home?duplicate";
        }

        ReadingList entry = new ReadingList(reader, book, listType);
        readingListRepository.save(entry);

        return "redirect:/home?added";
    }

    @PostMapping("/move-to-read")
    public String moveToReadList(@RequestParam long bookId, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("reader");
        if (reader == null) {
            return "redirect:/login";
        }

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            return "redirect:/profile?error";
        }

        Book book = optionalBook.get();
        readingListService.moveToRead(reader, book);

        return "redirect:/profile?updated";
    }

    @PostMapping("delete-from-read")
    public String removeFromRead(@RequestParam Long bookId, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("reader");
        if (reader == null) {
            return "redirect:/login";
        }

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            return "redirect:/profile?error";
        }

        readingListService.removeFromRead(reader, optionalBook.get());

        return "redirect:/profile?deleted";
    }

}
