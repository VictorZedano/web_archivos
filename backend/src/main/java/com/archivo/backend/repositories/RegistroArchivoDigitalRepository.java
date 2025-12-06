package com.archivo.backend.repositories;

import com.archivo.backend.entities.RegistroArchivoDigital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

import java.util.List;

@Repository
public interface RegistroArchivoDigitalRepository extends JpaRepository<RegistroArchivoDigital, Integer> {

    // Para el proceso de Normalización: Busca registros que aún no tienen las FKs
    // llenas.
    @NonNull
    List<RegistroArchivoDigital> findBySerieDocumentalIdIsNullAndTipoArchivoIdIsNull();

    // Para el Control de Calidad: Permite buscar errores.

    List<RegistroArchivoDigital> findByStatus(String status);
}