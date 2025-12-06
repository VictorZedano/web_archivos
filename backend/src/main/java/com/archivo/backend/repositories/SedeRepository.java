package com.archivo.backend.repositories;

import com.archivo.backend.entities.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SedeRepository extends JpaRepository<Sede, Integer> {

    /** Busca la Sede por el nombre que viene en la columna RAW del Excel. */
    Optional<Sede> findByNombre(String nombre);
}
