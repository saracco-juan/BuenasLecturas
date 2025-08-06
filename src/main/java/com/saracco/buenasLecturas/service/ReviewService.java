package com.saracco.buenasLecturas.service;

import com.saracco.buenasLecturas.model.Book;
import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.model.Review;
import com.saracco.buenasLecturas.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void saveReview(Reader reader, Book book, Integer rating, String comment) {
        Review review = new Review();
        review.setReader(reader);
        review.setBook(book);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }

    public boolean hasReview(Reader reader, Book book) {
        return reviewRepository.existsByReaderAndBook(reader, book);
    }

    public Review findByReaderAndBook(Reader reader, Book book) {
        return reviewRepository.findByReaderAndBook(reader, book);
    }

    public void update(Reader reader, Book book, Integer rating, String comment) {
        Review review = reviewRepository.findByReaderAndBook(reader, book);
        if (review != null) {
            review.setRating(rating);
            review.setComment(comment);
            review.setCreatedAt(LocalDateTime.now());
            reviewRepository.save(review);
        }
    }

    public void delete(Reader reader, Book book) {
        Review review = reviewRepository.findByReaderAndBook(reader, book);
        if (review != null) {
            reviewRepository.delete(review);
        }
    }
}