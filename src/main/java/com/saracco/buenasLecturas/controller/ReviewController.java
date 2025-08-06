package com.saracco.buenasLecturas.controller;

import com.saracco.buenasLecturas.model.Book;
import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.model.Review;
import com.saracco.buenasLecturas.repository.BookRepository;
import com.saracco.buenasLecturas.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public String addReview(@RequestParam Long bookId, @RequestParam Integer rating, @RequestParam String comment, HttpSession session) {

        Reader reader = (Reader) session.getAttribute("reader");
        if (reader == null) {
            return "redirect:/login";
        }

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            return "redirect:/profile?error";
        }

        reviewService.saveReview(reader, optionalBook.get(), rating, comment);
        return "redirect:/profile?reviewAdded";
    }

    @GetMapping("/add")
    public String mostrarFormularioReseña(@RequestParam Long bookId, Model model) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            return "redirect:/profile?error";
        }

        model.addAttribute("book", optionalBook.get());
        model.addAttribute("review", new Review());

        return "add-review";
    }

    @GetMapping("/edit")
    public String mostrarFormularioEdicion(@RequestParam Long bookId, HttpSession session, Model model) {
        Reader reader = (Reader) session.getAttribute("reader");
        if (reader == null) return "redirect:/login";

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) return "redirect:/profile?error";

        Review review = reviewService.findByReaderAndBook(reader, optionalBook.get());
        if (review == null) return "redirect:/profile?error";

        model.addAttribute("book", optionalBook.get());
        model.addAttribute("review", review);

        return "edit-review";
    }

    @PostMapping("/edit")
    public String guardarCambiosReseña(@RequestParam Long bookId,
                                       @RequestParam Integer rating,
                                       @RequestParam String comment,
                                       HttpSession session) {
        Reader reader = (Reader) session.getAttribute("reader");
        if (reader == null) return "redirect:/login";

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) return "redirect:/profile?error";

        reviewService.update(reader, optionalBook.get(), rating, comment);
        return "redirect:/profile?reviewUpdated";
    }

    @PostMapping("/delete")
    public String eliminarReseña(@RequestParam Long bookId, HttpSession session) {
        Reader reader = (Reader) session.getAttribute("reader");
        if (reader == null) return "redirect:/login";

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) return "redirect:/profile?error";

        reviewService.delete(reader, optionalBook.get());
        return "redirect:/profile?reviewDeleted";
    }

}
