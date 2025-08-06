package com.saracco.buenasLecturas.repository;

import com.saracco.buenasLecturas.model.Book;
import com.saracco.buenasLecturas.model.Reader;
import com.saracco.buenasLecturas.model.ReadingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {

    boolean existsByReaderAndBookAndListType(Reader reader, Book book, String listType);

    List<ReadingList> findByReaderAndListType(Reader reader, String listType);

    ReadingList findByReaderAndBookAndListType(Reader reader,Book book, String listType);



}
