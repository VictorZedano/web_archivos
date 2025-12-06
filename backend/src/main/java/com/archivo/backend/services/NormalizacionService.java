package com.archivo.backend.services;

public interface NormalizacionService {

    /**
     * Procesa todos los registros nuevos y los normaliza (convierte RAW a IDs).
     * 
     * @return El número de registros normalizados con éxito.
     */
    int normalizarRegistrosPendientes();
}
