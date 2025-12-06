package com.archivo.backend.services;

import com.archivo.backend.dtos.RespuestaCargaDto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface CargaMasivaService {

    // 🔑 MODIFICACIÓN: Ya no requiere digitalizadorId, solo el archivo
    RespuestaCargaDto cargarRegistrosDesdeExcel(MultipartFile file) throws IOException;
}
