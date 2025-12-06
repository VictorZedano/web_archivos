package com.archivo.backend.controllers;

import com.archivo.backend.dtos.RespuestaCargaDto;
import com.archivo.backend.services.CargaMasivaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/carga")
public class CargaMasivaController {

    private final CargaMasivaService cargaMasivaService;

    public CargaMasivaController(CargaMasivaService cargaMasivaService) {
        this.cargaMasivaService = cargaMasivaService;
    }

    /**
     * Endpoint para cargar registros desde un archivo Excel.
     * URL: POST /api/carga/excel
     */
    @PostMapping("/excel")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo no puede estar vacío.");
        }
        if (!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return ResponseEntity.badRequest().body("Tipo de archivo no soportado. Debe ser un archivo .xlsx");
        }

        try {
            RespuestaCargaDto respuesta = cargaMasivaService.cargarRegistrosDesdeExcel(file);
            
            if (respuesta.getRegistrosConErrores() > 0) {
                 return ResponseEntity.status(HttpStatus.OK).body(respuesta); 
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de I/O al procesar el archivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la carga masiva: " + e.getMessage());
        }
    }
}