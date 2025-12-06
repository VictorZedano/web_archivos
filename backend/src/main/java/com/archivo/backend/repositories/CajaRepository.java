package com.archivo.backend.repositories;

import com.archivo.backend.entities.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CajaRepository extends JpaRepository<Caja, Integer> {

    /**
     * Busca una caja por su campo 'codigo'.
     * Necesario para obtener el caja_id a partir del código de caja del Excel.
     */
    Optional<Caja> findByCodigo(String codigo);
}