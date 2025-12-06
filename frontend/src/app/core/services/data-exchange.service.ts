// src/app/core/services/data-exchange.service.ts

import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TransactionRecord } from '../models/transaction-record.model';


@Injectable({
    providedIn: 'root'
})
export class DataExchangeService {
    private http = inject(HttpClient);

    // URL base de tu backend de Spring Boot, ajustada a /api/archivos
    private urlApi = 'http://localhost:8080/api/archivos';

    /**
     * 📤 Envía el archivo Excel al backend para su procesamiento.
     * Mapea al endpoint POST /api/archivos/subir
     */
    subirArchivo(archivo: File): Observable<any> {
        const formData = new FormData();
        // CRÍTICO: La clave 'file' debe coincidir con @RequestParam("file") de Spring
        formData.append('file', archivo, archivo.name);

        return this.http.post(`${this.urlApi}/subir`, formData);
    }

    /**
     * 📜 Obtiene el historial de todas las transacciones de carga.
     * Mapea al endpoint GET /api/archivos/historial
     */
    obtenerHistorial(): Observable<TransactionRecord[]> {
        return this.http.get<TransactionRecord[]>(`${this.urlApi}/historial`);
    }

    /**
     * ❌ Descarga el archivo Excel con los registros que contuvieron errores.
     * Mapea al endpoint GET /api/archivos/errores/{idRegistro}
     */
    descargarErrores(idRegistro: number): Observable<Blob> {
        return this.http.get(`${this.urlApi}/errores/${idRegistro}`, {
            responseType: 'blob'
        });
    }

    /**
     * ⬇️ Exporta el historial de transacciones (el contenido de la tabla del log).
     * Mapea al endpoint GET /api/archivos/exportar/log
     */
    exportarLogHistorial(): Observable<Blob> {
        return this.http.get(`${this.urlApi}/exportar/log`, {
            responseType: 'blob'
        });
    }

    /**
     * ⬇️ Exporta TODOS los registros clínicos de la base de datos (Data).
     * Mapea al endpoint GET /api/archivos/exportar/datos
     */
    exportarDatosClinicos(): Observable<Blob> {
        return this.http.get(`${this.urlApi}/exportar/datos`, {
            responseType: 'blob'
        });
    }
}