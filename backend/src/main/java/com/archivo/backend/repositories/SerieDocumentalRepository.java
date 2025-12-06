package com.archivo.backend.repositories;

import com.archivo.backend.entities.SerieDocumental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SerieDocumentalRepository extends JpaRepository<SerieDocumental, Integer> {

    /** Busca la Serie Documental por el nombre que viene en el RAW. */
    Optional<SerieDocumental> findByNombre(String nombre);
}
