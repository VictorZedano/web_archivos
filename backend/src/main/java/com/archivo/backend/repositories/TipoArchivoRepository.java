package com.archivo.backend.repositories;

import com.archivo.backend.entities.TipoArchivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TipoArchivoRepository extends JpaRepository<TipoArchivo, Integer> {

    /** Busca el Tipo de Archivo por el nombre que viene en el RAW. */
    Optional<TipoArchivo> findByNombre(String nombre);
}