package com.saracco.buenasLecturas.repository;

import com.saracco.buenasLecturas.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Book findByOpenLibraryId(String openLibraryId);

}
