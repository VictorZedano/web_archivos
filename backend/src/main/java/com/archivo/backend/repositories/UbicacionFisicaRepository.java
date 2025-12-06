package com.archivo.backend.repositories;

import com.archivo.backend.entities.UbicacionFisica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbicacionFisicaRepository extends JpaRepository<UbicacionFisica, Integer> {

    /** Busca una ubicación por su código único (Ej: Estante A1-Nivel 3). */
    Optional<UbicacionFisica> findByCodigoUbicacion(String codigoUbicacion);
}
