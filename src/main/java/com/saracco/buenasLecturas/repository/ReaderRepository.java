package com.saracco.buenasLecturas.repository;

import com.saracco.buenasLecturas.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {

    Reader findByEmail(String email);

}
