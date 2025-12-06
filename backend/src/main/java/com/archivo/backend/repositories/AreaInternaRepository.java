package com.archivo.backend.repositories;

import com.archivo.backend.entities.AreaInterna;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaInternaRepository extends JpaRepository<AreaInterna, Integer> {

    /** Busca el Área Interna por el nombre que viene en el RAW. */
    Optional<AreaInterna> findByNombre(String nombre);
}
