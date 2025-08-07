package com.saracco.buenasLecturas.repository;

import com.saracco.buenasLecturas.model.Book;
import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    boolean existsByReaderAndBook(Reader reader, Book book);
    Review findByReaderAndBook(Reader reader, Book book);

}
