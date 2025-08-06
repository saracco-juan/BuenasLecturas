package com.saracco.buenasLecturas.controller;

import com.saracco.buenasLecturas.model.Book;
import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.model.ReadingList;
import com.saracco.buenasLecturas.service.ReaderService;
import com.saracco.buenasLecturas.service.ReadingListService;
import com.saracco.buenasLecturas.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ReaderController {

    @Autowired
    private ReaderService readerService;
    @Autowired
    private ReadingListService readingListService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("reader", new Reader());
        return "register";
    }

    @PostMapping("/register")
    public String registerReader(@Valid @ModelAttribute("reader") Reader reader, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register"; // Vuelve al form con errores
        }
        try {
            readerService.register(reader);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("reader") Reader reader, Model model, HttpSession session) {
        Reader loggedReader = readerService.login(reader.getEmail(), reader.getPassword());

        if (loggedReader != null) {
            session.setAttribute("reader", loggedReader); // 👈 solo si pasa el login
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Email o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        Reader loggedReader = (Reader) session.getAttribute("reader");
        model.addAttribute("reader", loggedReader);
        model.addAttribute("book", new Book());

        List<Book> books = (List<Book>) session.getAttribute("books");
        if (books != null) {
            model.addAttribute("books", books);
            session.removeAttribute("books");
        }

        return "home";
    }

    @GetMapping("/profile")
    public String mostrarPerfil(HttpSession session, Model model) {

        Reader reader = (Reader) session.getAttribute("reader");

        List<ReadingList> quieroLeer = readingListService.findByReaderAndListType(reader, "QUIERO_LEER");

        List<ReadingList> leidos = readingListService.findByReaderAndListType(reader, "LEIDOS");

        Map<Long, Boolean> reviewsMap = new HashMap<>();
        for (ReadingList item : leidos) {
            Book book = item.getBook();
            boolean tiene = reviewService.hasReview(reader, book);
            reviewsMap.put(book.getId(), tiene);
        }

        model.addAttribute("reviewsMap", reviewsMap);
        model.addAttribute("quiero_leer", quieroLeer);
        model.addAttribute("leidos", leidos);

        return "/profile";
    }


}
