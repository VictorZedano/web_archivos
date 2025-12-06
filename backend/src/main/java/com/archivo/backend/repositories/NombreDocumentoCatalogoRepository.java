package com.archivo.backend.repositories;

import com.archivo.backend.entities.NombreDocumentoCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NombreDocumentoCatalogoRepository extends JpaRepository<NombreDocumentoCatalogo, Integer> {

    /** Busca el Nombre del Documento por el nombre que viene en el RAW. */
    Optional<NombreDocumentoCatalogo> findByNombre(String nombre);
}