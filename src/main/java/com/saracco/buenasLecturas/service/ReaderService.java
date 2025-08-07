package com.saracco.buenasLecturas.service;

import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.repository.ReaderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public void register(Reader reader) {
        if (readerRepository.findByEmail(reader.getEmail()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }

        String hashedPassword = passwordEncoder.encode(reader.getPassword());
        reader.setPassword(hashedPassword);
        readerRepository.save(reader);
    }

    public Reader login(String email, String rawPassword) {
        Reader reader = readerRepository.findByEmail(email);

        if (reader != null && passwordEncoder.matches(rawPassword, reader.getPassword())) {
            return reader;
        }

        return null;
    }



}
