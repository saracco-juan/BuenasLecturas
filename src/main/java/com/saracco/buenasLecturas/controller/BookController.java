package com.saracco.buenasLecturas.controller;

import com.saracco.buenasLecturas.model.Book;
import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/api")
public class BookController {

    private BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public String getBooks(@RequestParam("title") String title, Model model, HttpSession session) {
        List<Book> books = bookService.getBookByTitle(title);
        session.setAttribute("books", books);

        return "redirect:/home?title=" + URLEncoder.encode(title, StandardCharsets.UTF_8);

    }
}
